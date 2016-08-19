/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import beans.Carrello;
import beans.ElementoOrdine;
import beans.Prenotazione;
import com.google.gson.internal.Pair;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author nico
 */
public class GestorePrenotazioni extends HttpServlet {

    public void init(ServletConfig config) throws ServletException {
        System.out.println("Init GestorePrenotazioni");
        super.init(config);

        try {
            System.out.println("Init DriverManager");
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
        } catch (SQLException ex) {
            System.out.println("DriverManger.registerDriver failed");
            Logger.getLogger(GestoreCucina.class.getName()).log(Level.SEVERE, null, ex);
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
        ServletContext ctx = getServletContext();
        RequestDispatcher rd = null;
        String action = request.getParameter("action");

        try (PrintWriter out = response.getWriter()) {
            if (action == null) {
                rd = ctx.getRequestDispatcher("/index.jsp");
            } else if (action.equalsIgnoreCase("get-prenotazione")) {
                JSONObject json = new JSONObject();
                int id_prenotazione = Integer.parseInt(request.getParameter("id"));

                try {
                    Prenotazione p = Query.prenotazioneGetByID(id_prenotazione);

                    json.accumulate("data_consegna", p.getDataConsegna());
                    json.accumulate("orario_consegna", p.getOrarioConsegna());
                    json.accumulate("prezzo_totale", p.getPrezzo()); //???
                    json.accumulate("consegnato", p.isConsegnato());
                    /* JSON ARRAY con Pizza, quantità */
                    JSONArray pizze = new JSONArray();

//                    for (Pair<String, Integer> current : p.getOrdine()) {
                    for (ElementoOrdine current : p.getOrdine()) {
                        JSONObject temp = new JSONObject();

                        temp.accumulate("nome_pizza", current.getNome());
                        temp.accumulate("quantita", current.getQuantity());

                        pizze.put(temp);
                    }

                    json.accumulate("pizze", pizze);

                } catch (JSONException ex) {
                    System.out.println("SBRAAAAAAAAAAAA!!!" + ex.getMessage());
                }

                response.setContentType("application/json");
                out.write(json.toString());
            } else if (action.equalsIgnoreCase("get-all-prenotazioni")) {
                JSONArray json_prenotazioni = new JSONArray();

                response.setContentType("application/json");
                out.write(json_prenotazioni.toString());

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

        try (PrintWriter out = response.getWriter()) {
            String action = request.getParameter("action");
            System.out.println("GestorePrenotazioni --  action: " + action);

            if (action == null) {
                rd = ctx.getRequestDispatcher("/index.jsp");

            } else if (action.equalsIgnoreCase("add-prenotazione")) {
                java.sql.Date date = null;
                java.sql.Time time = null;
                boolean ok = false;

                // parsing data e orario
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm");
                    String dataConsegna = request.getParameter("dataConsegna");
                    String oraConsegna = request.getParameter("oraConsegna");
                    java.util.Date temp = sdf.parse(dataConsegna + " " + oraConsegna);
                    out.println("Data parsata: " + temp);
                    date = new java.sql.Date(temp.getTime());
                    time = new java.sql.Time(temp.getTime());

                    ok = true;
                } catch (ParseException ex) {
                    Logger.getLogger(GestorePrenotazioni.class.getName()).log(Level.SEVERE, null, ex);
                    out.println(ex.getMessage());
                }

                if (ok) {
                    String nomeCompleto = request.getParameter("nominativo"); 
                    String indirizzo = request.getParameter("indirizzo"); 
                    
                    
                    HttpSession session = request.getSession();
                    int id_utente = (int) session.getAttribute("idUtente");
                    Carrello cart = (Carrello) session.getAttribute("carrello");
                    ArrayList<ElementoOrdine> ordine = new ArrayList<>(cart.getOrdine());

                    if (Query.prenotazioneInsert(id_utente, nomeCompleto, indirizzo, date, time, ordine)) {
                        session.setAttribute("carrello", new Carrello());
                        rd = ctx.getRequestDispatcher("/mie-prenotazioni.jsp");
                    }
                } else {
                    //errrore parse date / time
                }
                
            } else if (action.equalsIgnoreCase("confirm-deliver")) {
                int id_prenotazione = Integer.parseInt(request.getParameter("idp"));
                String resp = Query.prenotazioneConfermaConsegna(id_prenotazione) ? "OK" : "ERR";

                System.out.println("Conferma ordine n. " + id_prenotazione + ": " + resp);

                out.write(resp);

            } else if (action.equalsIgnoreCase("delete-deliver")) {
                int id_prenotazione = Integer.parseInt(request.getParameter("idp"));
                String resp = Query.prenotazioneDelete(id_prenotazione) ? "OK" : "ERR";

                System.out.println("Cancellazione ordine n. " + id_prenotazione + ": " + resp);

                out.write(resp);

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
