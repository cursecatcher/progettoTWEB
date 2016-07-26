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
        RequestDispatcher rd = null;//ctx.getRequestDispatcher("/error.jsp");
        String action = request.getParameter("action");
        System.out.println("ServletUtente - action: " + action);

        try (PrintWriter out = response.getWriter()) {
            if (action == null) {
                rd = ctx.getRequestDispatcher("/index.jsp");

            } else if (action.equalsIgnoreCase("user-login")) {
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                try (Connection conn = Query.getConnection()) {
                    Utente u = Query.getUserByEmail(conn, email);

                    if (u != null) {
                        if (u.getPassword().equals(DigestUtils.sha1Hex(password))) {
                            HttpSession session = request.getSession();
                            session.setAttribute("idUtente", u.getId());
                            session.setAttribute("usertoken", "authenticated");

                            request.setAttribute("user", u);

                            rd = ctx.getRequestDispatcher("/profilo.jsp");
                        } else {
                            request.setAttribute("message", "WRONG_PASSWORD");
                            request.setAttribute("previous_email", email);
                            rd = ctx.getRequestDispatcher("/login.jsp");
                        }
                    } else {
                        request.setAttribute("message", "EMAIL_NOT_FOUND");
                        rd = ctx.getRequestDispatcher("/login.jsp");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(GestoreCliente.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Eccezione ???");
                    //redirect a error.jsp
                }

            } else if (action.equalsIgnoreCase("user-registrazione")) {
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                if (password.equals(request.getParameter("password2"))) {
                    if (Query.insertNewClient(email, password)) {
                        out.print("OK");
                    } else {
                        out.print("USER_ALREADY_EXISTS");
                    }
                } else {
                    out.print("PASSWORD_MISMATCH");
                }

            } else {
                rd = ctx.getRequestDispatcher("/error.jsp");
            }

            if (rd != null) {
                rd.forward(request, response);
            }
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
