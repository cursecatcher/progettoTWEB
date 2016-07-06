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
                    ResultSet rs = Query.getUserByEmail(st, email);

                    if (rs.next()) {
                        String user_pwd = rs.getString("password");

                        if (user_pwd != null) {
                            if (user_pwd.equals(DigestUtils.sha1Hex(password))) {
                                createSession(request.getSession(), rs.getInt("id_utente"), email);
                                out.println("OK");
                            } else {
                                out.println("WRONG_PASSWORD");
                            }
                        } else {
                            System.out.println("SOCIAL_LOGIN_REQUIRED");
                        }
                    } else {
                        out.println("EMAIL_NOT_FOUND");
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
                    System.out.println("Autenticazione G+ OK");

                    try {
                        Connection conn = getDBConnection();
                        Statement st = conn.createStatement();
                        ResultSet rs = Query.getUserByEmail(st, verify[1]);

                        /* Email utente non presente: inserisco nuovo utente con password null */
                        if (!rs.next()) {
                            System.out.println("Inserimento nuovo utente g+");
                            Query.insertNewClient(st, verify[1], null);
                            rs = Query.getUserByEmail(st, verify[1]);
                        }

                        createSession(request.getSession(), rs.getInt("id_utente"), verify[1]);

                        rs.close();
                        st.close();
                        conn.close();
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
                    ResultSet rs = Query.getUserByEmail(st, email);

                    if (!rs.next()) {
                        Query.insertNewClient(st, email, password);
                        out.println("OK");

                    } else {
                        System.out.println("Utente gia' registrato");

                        if (rs.getString("password") == null) {
                            Query.changeClientPassword(st, rs.getInt("id_utente"), password);
                            out.println("OK");
                        } else {
                            System.out.println("Password != null");
                            out.println("ERR");
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
        } catch (SQLException ex) {
            Logger.getLogger(ServletUtente.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Connessione al DB fallita");
        }

        return conn;
    }

    private void createSession(HttpSession session, int id, String email) {
        session.setAttribute("idUtente", id);
        session.setAttribute("emailUtente", email);
        session.setAttribute("ruoloUtente", "cliente");
    }

    @Override
    public String getServletInfo() {
        return "NOPE";
    }

}
