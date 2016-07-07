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

/* https://blogs.oracle.com/WebLogicServer/entry/using_try_with_resources_with */
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

                String nome = request.getParameter("nome");
                String id_ingredienti = request.getParameter("listaIngredienti");
                float prezzo = Float.parseFloat(request.getParameter("prezzo"));

                if (id_ingredienti.equalsIgnoreCase("")) {
                    System.out.println("L'utente e' un coglione -> pizza senza ingredienti");
                }

                try (Connection conn = Query.getConnection();
                        Statement st = conn.createStatement();
                        ResultSet rs = Query.getPizzaByName(st, nome)) {

                    if (!rs.next()) {
                        Query.insertPizza(st, nome, prezzo, id_ingredienti);
                        System.out.println("OK");
                        out.println("OK");
                    } else {
                        System.out.println("Pizza gia' presente");
                        out.println("ERR");
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(ServletGnam.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Eccezione in ServletGnam->pizza-create");
                }

            } else if (action.equalsIgnoreCase("pizza-remove")) {
                int id = Integer.parseInt(request.getParameter("id_pizza"));
                
                try (Connection conn = Query.getConnection();
                        Statement st = conn.createStatement()) {
                    
                    if (Query.deletePizza(st, id)) {
                        System.out.println("OK"); 
                        out.println("OK"); 
                    }
                    else {
                        System.out.println("ERR"); 
                        out.println("ERR"); 
                    }
                }
                catch (SQLException ex) {
                    Logger.getLogger(ServletGnam.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Eccezione in ServletGnam->pizza-remove");
                }
                
            } else if (action.equalsIgnoreCase("pizza-modify")) {
                int id = Integer.parseInt(request.getParameter("id_pizza"));
                String nome = request.getParameter("nome");
                float prezzo = Float.parseFloat(request.getParameter("prezzo"));
                String listaIngredienti = request.getParameter("lista_ingredienti");
                
                try (Connection conn = Query.getConnection();
                        Statement st = conn.createStatement()) {
                    
                    if (Query.updatePizza(st, id, nome, prezzo, listaIngredienti)) {
                        out.println("OK");
                        System.out.println("OK");
                    }
                    else {
                        out.println("ERR");
                        System.out.println("ERR");
                    }
                }
                catch (SQLException ex) {
                    Logger.getLogger(ServletGnam.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Eccezione in ServletGnam->pizza-modify");
                }

            } else if (action.equalsIgnoreCase("ingrediente-add")) {
                String nome = request.getParameter("nome");
                float prezzo = Float.valueOf(request.getParameter("prezzo"));

                try (Connection conn = Query.getConnection();
                        Statement st = conn.createStatement();
                        ResultSet rs = Query.getIngredientByName(st, nome)) {
                    
                    if (!rs.next()) {
                        Query.insertIngrediente(st, nome, prezzo);
                        out.println("OK");
                    } else {
                        System.out.println("Ingrediente gia' presente :v");
                        out.println("ERR");
                    }
                    
                } catch (SQLException ex) {
                    Logger.getLogger(ServletGnam.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Eccezione in ServletGnam->ingrediente-add");
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
