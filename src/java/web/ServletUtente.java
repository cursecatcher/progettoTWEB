/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import org.apache.commons.codec.digest.DigestUtils; 

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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
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
                    Connection conn = DriverManager.getConnection(
                            Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
                    Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery("SELECT * FROM Utente WHERE email = '" + email + "'");

                    if (rs.next()) {
                        String user_pwd = rs.getString("password"); 
                        
                        if (user_pwd.equals(DigestUtils.sha1Hex(password))) {
                            out.println("OK"); 
                            /* sessione */
                        }
                        else {
                            out.println("WRONG PASSWORD");
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

            } else if (action.equalsIgnoreCase("user-registrazione")) {
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                //controllo email e password != null? 
                
                try {
                    Connection conn = DriverManager.getConnection(
                            Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
                    Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery("SELECT id_utente FROM Utente WHERE email = '" + email + "'");
                    
                    if (!rs.next()) {
                        String pwdDigest = DigestUtils.sha1Hex(password); 
                        
                        String query
                                = "INSERT INTO Utente(email, password, ruolo) VALUES"
                                + "('" + email + "', '" + pwdDigest + "', 'cliente')";

                        if (st.executeUpdate(query) == 1) {
                            out.println("OK"); 
                        }
                        else {
                            out.println("BOH"); 
                        }

                    } else {
                        out.println("ERR");
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

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        processRequest(request, response);
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
