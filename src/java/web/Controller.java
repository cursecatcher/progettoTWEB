/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

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
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        ServletContext ctx = getServletContext();
        RequestDispatcher rd = null;

        try (PrintWriter out = response.getWriter()) {
            String action = request.getParameter("action");

            if (action == null) {
                rd = ctx.getRequestDispatcher("/index.jsp");
                System.out.println("L'utente e' un coglione"); //da cambiare
            } else if (action.equalsIgnoreCase("user-login")) {
                rd = ctx.getNamedDispatcher("GestoreCliente");
                System.out.println("LOGIN");
            } else if (action.equalsIgnoreCase("login-googleplus")) {
                rd = ctx.getNamedDispatcher("GestoreCliente");
                System.out.println("LOGIN G+");
            } else if (action.equalsIgnoreCase("user-registrazione")) {
                rd = ctx.getNamedDispatcher("GestoreCliente");
                System.out.println("REGISTRAZIONE");
            } else if (action.equalsIgnoreCase("ingrediente-add")) {
                rd = ctx.getNamedDispatcher("GestoreCucina");
                System.out.println("INSERIMENTO INGREDIENTE");
            } else if (action.equalsIgnoreCase("pizza-create")) {
                rd = ctx.getNamedDispatcher("GestoreCucina");
                System.out.println("INSERIMENTO PIZZA");
            } else if (action.equalsIgnoreCase("pizza-edit")) {
                rd = ctx.getNamedDispatcher("GestoreCucina");
                System.out.println("MODIFICA PIZZA");
            } else {
                System.out.println("OPERAZIONE NON SUPPORTATA");
                rd = ctx.getRequestDispatcher("/error.jsp");
            }

            rd.forward(request, response);

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
        response.setContentType("text/html;charset=UTF-8");

        ServletContext ctx = getServletContext();
        RequestDispatcher rd = ctx.getRequestDispatcher("/error.jsp");

        String action = request.getParameter("action");
        System.out.println("Controller. Action: " + action + ".");

        if (action == null) {
            rd = ctx.getRequestDispatcher("/index.jsp");

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

            } else if (action.equalsIgnoreCase("login-googleplus")) {
                rd = ctx.getNamedDispatcher("GestoreCliente");

            } else if (action.equalsIgnoreCase("user-registrazione")) {
                rd = ctx.getNamedDispatcher("GestoreCliente");

            } else if (action.equalsIgnoreCase("ingrediente-add")) {
                rd = ctx.getNamedDispatcher("GestoreCucina");

            } else if (action.equalsIgnoreCase("pizza-create")) {
                rd = ctx.getNamedDispatcher("GestoreCucina");

            } else if (action.equalsIgnoreCase("pizza-edit")) {
                rd = ctx.getNamedDispatcher("GestoreCucina");

            } else if (action.equalsIgnoreCase("add-prenotazione")) {
                rd = ctx.getNamedDispatcher("GestorePrenotazioni");

            }

            rd.forward(request, response);
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
