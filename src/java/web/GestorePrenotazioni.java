/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import com.google.gson.internal.Pair;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nico
 */
public class GestorePrenotazioni extends HttpServlet {

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
        RequestDispatcher rd = ctx.getRequestDispatcher("/error.jsp");

        try (PrintWriter out = response.getWriter()) {
            String action = request.getParameter("action");
            System.out.println("GestorePrenotazioni --  action: " + action);

            if (action == null) {
                rd = ctx.getRequestDispatcher("/index.jsp");
                
            } else if (action.equalsIgnoreCase("add-prenotazione")) {
                int num_pizze = Integer.parseInt(request.getParameter("num_pizze")); 
                List<Pair<Integer, Integer>> ordine = new ArrayList<>();
                
                out.println("Numero pizze:" + num_pizze); 
                
                /* inserisci record in Prenotazione */ 
                
                
                for (int i = 0; i < num_pizze; i++) {
                    String id = request.getParameter("id_pizza-" + i); 
                    String quantity = request.getParameter("quantity-" + i); 
                    
                    out.println("id:" + id); 
                    out.println("quantity: " + quantity); 
                    
                    ordine.add(new Pair<>(new Integer(id), new Integer(quantity)));
                    
                    
                    /* inserisci records in PrenotazionePizza */ 
  //                  ordine.add(new Pair<Integer, Integer>); 
                }
                
                out.println("Sintesi ordine: "); 
                for (Pair p : ordine) {
                    out.println("-" + p);
                }
                
            }

            //      rd.forward(request, response);
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
