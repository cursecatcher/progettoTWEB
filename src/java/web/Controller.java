/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import beans.Carrello;
import beans.Pizza;
import beans.Utente;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author nico
 */
public class Controller extends HttpServlet {

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
        RequestDispatcher rd = ctx.getRequestDispatcher("/error.jsp");

        String action = request.getParameter("action");
        System.out.println("Controller. Action: " + action + ".");

        if (action == null) {
            rd = ctx.getRequestDispatcher("/index.jsp");

        } else if (action.equalsIgnoreCase("get-prenotazione")) {
            rd = ctx.getNamedDispatcher("GestorePrenotazioni");

        } else if (action.equalsIgnoreCase("newpizza")) {
            rd = ctx.getRequestDispatcher("/newpizza.jsp");

        } else if (action.equalsIgnoreCase("profilo")) {
            HttpSession session = request.getSession();
            String state = (String) session.getAttribute("usertoken");

            if (state != null && state.equalsIgnoreCase("authenticated")) {
                int id = (int) session.getAttribute("idUtente");
                Utente u = new Utente(id);
                request.setAttribute("user", u);
                rd = ctx.getRequestDispatcher("/profilo.jsp");
            } else {
                request.setAttribute("message", "Login richiesto per accedere alla pagina");
                rd = ctx.getRequestDispatcher("/login.jsp");
                System.out.println("LOGIN REQUIRED");
            }

        } else if (action.equalsIgnoreCase("proceed-to-order")) {
            HttpSession session = request.getSession();
            Carrello cart = (Carrello) session.getAttribute("carrello");

            if (cart.isEmpty()) {
                request.setAttribute("message", "FAIL");
                rd = ctx.getRequestDispatcher("/newordine.jsp");
            } else {
                rd = ctx.getRequestDispatcher("/confirm-order.jsp");
            }

        } else if (action.equalsIgnoreCase("edit-req")) {
            int idp = Integer.parseInt(request.getParameter("id"));
            Pizza p = Query.pizzaGetById(idp);

            if (idp < 0) {
                rd = ctx.getRequestDispatcher("/error.jsp");
                System.out.println("idp < 0");
            } else if (p == null) {
                rd = ctx.getRequestDispatcher("/error.jsp");
                System.out.println("p == null");
            } else {
                request.setAttribute("pizza", p);
                rd = ctx.getRequestDispatcher("/editpizza.jsp");
            }
        } else if (action.equalsIgnoreCase("logout")) {
            HttpSession session = request.getSession();
            session.invalidate();
            rd = ctx.getRequestDispatcher("/index.jsp");

        } else if (action.equalsIgnoreCase("ajax-update-admin-pizzas")) {
            rd = ctx.getNamedDispatcher("GestoreCucina");

        } else if (action.equalsIgnoreCase("ajax-html-cart")) {
            rd = ctx.getNamedDispatcher("GestoreCliente");

        } else if (action.equalsIgnoreCase("ajax-html-ingr")) {
            rd = ctx.getNamedDispatcher("GestoreCucina");

        }

        rd.forward(request, response);
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

        try (PrintWriter out = response.getWriter()) {
            String action = request.getParameter("action");
            System.out.println("Controller. Action: " + action + ".");

            if (action == null) {
                rd = ctx.getRequestDispatcher("/index.jsp");

            } else if (action.equalsIgnoreCase("user-login")) {
                rd = ctx.getNamedDispatcher("GestoreCliente");

            } else if (action.equalsIgnoreCase("user-registrazione")) {
                rd = ctx.getNamedDispatcher("GestoreCliente");

            } else if (action.equalsIgnoreCase("user-change-pwd")) {
                rd = ctx.getNamedDispatcher("GestoreCliente");

            } else if (action.equalsIgnoreCase("ingrediente-add")) {
                rd = ctx.getNamedDispatcher("GestoreCucina");

            } else if (action.equalsIgnoreCase("pizza-create")) {
                rd = ctx.getNamedDispatcher("GestoreCucina");

            } else if (action.equalsIgnoreCase("pizza-edit")) {
                rd = ctx.getNamedDispatcher("GestoreCucina");

            } else if (action.equalsIgnoreCase("add-prenotazione")) {
                rd = ctx.getNamedDispatcher("GestorePrenotazioni");

            } else if (action.equalsIgnoreCase("add-to-cart")) {
                rd = ctx.getNamedDispatcher("GestoreCliente");

            } else if (action.equalsIgnoreCase("remove-to-cart")) {
                rd = ctx.getNamedDispatcher("GestoreCliente");

            } else if (action.equalsIgnoreCase("confirm-deliver")) {
                rd = ctx.getNamedDispatcher("GestorePrenotazioni");

            } else if (action.equalsIgnoreCase("delete-deliver")) {
                rd = ctx.getNamedDispatcher("GestorePrenotazioni");

            } else if (action.equalsIgnoreCase("pizza-remove")) {
                rd = ctx.getNamedDispatcher("GestoreCucina");

            } else if (action.equalsIgnoreCase("delete-order")) {
                HttpSession session = request.getSession(false);
                rd = null; 

                if (session == null) {
                    out.write("ERR");
                } else {
                    out.write("OK");
                    session.setAttribute("carrello", new Carrello());
                }

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
    }// </editor-fold>

}
