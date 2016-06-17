/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import org.apache.commons.codec.digest.DigestUtils;
import verifytoken.Verify;


@WebServlet(name = "ServletUtente", urlPatterns = {"/ServletUtente"})
public class ServletUtente extends HttpServlet {

    public void init(ServletConfig config) throws ServletException {
        System.out.println("Init ServletUtente");
        super.init(config);

        try {
            System.out.println("Init DriverManager");
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
        } catch (SQLException ex) {
            System.out.println("DriverManger.registerDriver failed");
            Logger.getLogger(ServletUtente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        ServletContext ctx = getServletContext();
        RequestDispatcher rd = null;

        try (PrintWriter out = response.getWriter()) {
            String action = request.getParameter("action");

            if (action == null) {
                rd = ctx.getRequestDispatcher("/index.jsp");
            } else {
                rd = ctx.getRequestDispatcher("/error.jsp");
            }

            if (rd != null) {
                rd.forward(request, response);
            }
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        ServletContext ctx = getServletContext();
        RequestDispatcher rd = null;
        String action = request.getParameter("action");

        System.out.println("ServletUtente - action: " + action);

        try (PrintWriter out = response.getWriter()) {
            if (action == null) {
                rd = ctx.getRequestDispatcher("/index.jsp");

            } else if (action.equalsIgnoreCase("user-login")) {
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                try {
                    Connection conn = getDBConnection();
                    Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery("SELECT * FROM Utente WHERE email = '" + email + "'");

                    if (rs.next()) {
                        String user_pwd = rs.getString("password");

                        if (user_pwd != null) {
                            if (user_pwd.equals(DigestUtils.sha1Hex(password))) {
                                out.println("OK");
                                /* sessione */
                            } else {
                                out.println("WRONG PASSWORD");
                            }
                        } else {
                            System.out.println("Account social");
                        }
                    } else {
                        out.println("EMAIL NOT FOUND");
                    }

                    rs.close();
                    st.close();
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ServletUtente.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else if (action.equalsIgnoreCase("login-googleplus")) {
                System.out.println("ServletUtente login g+");

                String[] verify = Verify.getUserCredentials(
                        request.getParameter("id_token"),
                        request.getParameter("access_token"));

                if (verify != null) {
                    System.out.println("Andato tutto ok");

                    try {
                        Connection conn = getDBConnection();
                        Statement st = conn.createStatement();
                        String query = "SELECT * FROM Utente WHERE email = '" + verify[1] + "'";
                        ResultSet rs = st.executeQuery(query);

                        /* Email utente non presente: inserisco nuovo utente con password null */
                        if (!rs.next()) {
                            query = "INSERT INTO Utente (email, password, ruolo) VALUES "
                                    + "('" + verify[1] + "', null, 'cliente')";

                            System.out.println("Inserimento nuovo utente g+");
                            if (st.executeUpdate(query) == 1) {
                                System.out.println("OK");
                            } else {
                                System.out.println("FAIL");
                            }

                        }
                        
                        rs.close();
                        st.close();
                        conn.close();
                        
                        /* creazione sessione */

                    } catch (SQLException ex) {
                        Logger.getLogger(ServletUtente.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    System.out.println("EH, VOLEVI");
                }

            } else if (action.equalsIgnoreCase("user-registrazione")) {
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                //controllo email e password != null? 
                try {
                    Connection conn = getDBConnection();
                    Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery("SELECT * FROM Utente WHERE email = '" + email + "'");
                    String query;

                    if (!rs.next()) {
                        query = "INSERT INTO Utente(email, password, ruolo) VALUES " +
                                "('" + email + "', " + 
                                "'" + DigestUtils.sha1Hex(password) + "', 'cliente')"; 

                        if (st.executeUpdate(query) == 1) {
                            out.println("OK");
                        } else {
                            out.println("BOH");
                        }

                    } else {
                        System.out.println("Utente gia' registrato");
                        /* Utente gia' registrato: verifica se l'utente ha gia' una 
                        password associata*/
                        if (rs.getString("password") == null) {
                            query = "UPDATE Utente SET password='" + DigestUtils.sha1Hex(password) + "'"
                                    + " WHERE id_utente=" + rs.getInt("id_utente");
                            st.executeUpdate(query); 
                            
                        } else {
                            System.out.println("Password != null");
                        }
                    }

                    rs.close();
                    st.close();
                    conn.close();

                } catch (SQLException ex) {
                    Logger.getLogger(ServletUtente.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                rd = ctx.getRequestDispatcher("/error.jsp");
            }

            if (rd != null) {
                rd.forward(request, response);
            }
        }
    }
    
    private Connection getDBConnection() {
        Connection conn = null; 
        
        try {
            conn = DriverManager.getConnection(
                            Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
        }
        catch (SQLException ex) {
            Logger.getLogger(ServletUtente.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Connessione al DB fallita"); 
        }
        
        return conn; 
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
