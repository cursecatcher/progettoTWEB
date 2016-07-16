/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import beans.Utente;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import verifytoken.Verify;

/**
 *
 * @author nico
 */
public class GestoreCliente extends HttpServlet {

    public void init(ServletConfig config) throws ServletException {
        System.out.println("Init ServletUtente");
        super.init(config);

        try {
            System.out.println("Init DriverManager");
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
        } catch (SQLException ex) {
            System.out.println("DriverManger.registerDriver failed");
            Logger.getLogger(GestoreCliente.class.getName()).log(Level.SEVERE, null, ex);
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
        RequestDispatcher rd = ctx.getRequestDispatcher("/error.jsp");
        String action = request.getParameter("action");
        System.out.println("ServletUtente - action: " + action);

        try (PrintWriter out = response.getWriter()) {
            if (action == null) {
                rd = ctx.getRequestDispatcher("/index.jsp");

            } else if (action.equalsIgnoreCase("user-login")) {
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                try (Connection conn = Query.getConnection();
                        Statement st = conn.createStatement();
                        ResultSet rs = Query.getUserByEmail(st, email)) {

                    if (rs.next()) {
                        String user_pwd = rs.getString("password");

                        if (user_pwd.equals(DigestUtils.sha1Hex(password))) {
                            HttpSession session = request.getSession();
                            session.setAttribute("idUtente", rs.getInt("id_utente"));
                            session.setAttribute("usertoken", "authenticated");
                            /*
                                createSession(
                                        request.getSession(),
                                        rs.getInt("id_utente"),
                                        email,
                                        rs.getString("ruolo")
                                );*/

                            Utente u = new Utente();
                            u.setId(rs.getInt("id_utente"));
                            u.setEmail(email);
                            u.setRuolo(rs.getString("ruolo"));
                            request.setAttribute("user", u);

                            //   out.println("OK");
                            rd = ctx.getRequestDispatcher("/profilo.jsp");
                        } else {
                            out.println("WRONG_PASSWORD");
                        }
                    } else {
                        out.println("EMAIL_NOT_FOUND");
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(GestoreCliente.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else if (action.equalsIgnoreCase("user-registrazione")) {
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                if (password.equals(request.getParameter("password2"))) {
                    try (Connection conn = Query.getConnection();
                            Statement st = conn.createStatement();
                            ResultSet rs = Query.getUserByEmail(st, email)) {

                        if (!rs.next()) {
                            Query.insertNewClient(st, email, password);
                            out.println("OK");
                        } else {
                            out.println("USER_ALREADY_EXISTS");
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(GestoreCliente.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                // else..le due password non coincidono

            }

            rd.forward(request, response);
        }
    }

    private void createSession(HttpSession session, int id, String email, String ruolo) {
        session.setAttribute("usertoken", "authenticated");
        session.setAttribute("idUtente", id);
        session.setAttribute("emailUtente", email);
        session.setAttribute("ruoloUtente", ruolo);
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
