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

/**
 *
 * @author nicol
 */
public class ServletGnam extends HttpServlet {

    public void init(ServletConfig config) throws ServletException {
        System.out.println("Init ServletGnam");
        super.init(config);

        try {
            System.out.println("Init DriverManager");
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
        } catch (SQLException ex) {
            System.out.println("DriverManger.registerDriver failed");
            Logger.getLogger(ServletGnam.class.getName()).log(Level.SEVERE, null, ex);
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

        try (PrintWriter out = response.getWriter()) {
            String action = request.getParameter("action");

            if (action == null) {
                rd = ctx.getRequestDispatcher("/index.jsp");

            } else if (action.equalsIgnoreCase("pizza-create")) {
                System.out.println("It's time");

            } else if (action.equalsIgnoreCase("pizza-remove")) {
                ;

            } else if (action.equalsIgnoreCase("pizza-modify")) {
                ;

            } else if (action.equalsIgnoreCase("ingrediente-add")) {
                String nome = request.getParameter("nome");
                float prezzo = Float.valueOf(request.getParameter("prezzo"));

                /* server side validation plz */
                String query = "INSERT INTO Ingrediente(nome, prezzo) VALUES "
                        + "('" + nome + "', " + prezzo + ")";

                try {
                    System.out.println("Eseguo query: " + query);

                    Connection conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
                    Statement st = conn.createStatement();
                    boolean res;

                    try {
                        res = st.executeUpdate(query) == 1;
                    } catch (SQLIntegrityConstraintViolationException ex) {
                        res = false;
                        Logger.getLogger(ServletGnam.class.getName()).log(Level.INFO, null, ex);
                    } finally {
                        st.close();
                        conn.close();
                    }

                    if (res) {
                        System.out.println("OK");
                        out.println("OK");
                    } else {
                        System.out.println("ERRORE");
                        out.println("ERR");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ServletGnam.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                rd = ctx.getRequestDispatcher("/error.jsp");
            }

            if (rd != null) {
                rd.forward(request, response);
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
