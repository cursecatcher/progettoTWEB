/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

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
                HttpSession session = request.getSession(); 
                java.sql.Date date = null;
                java.sql.Time time = null;
                /* parsing data e orario */
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm");
                    String dataConsegna = request.getParameter("dataConsegna");
                    String oraConsegna = request.getParameter("oraConsegna");
                    java.util.Date temp = sdf.parse(dataConsegna + " " + oraConsegna);
                    out.println("Data parsata: " + temp);
                    date = new java.sql.Date(temp.getTime());
                    time = new java.sql.Time(temp.getTime());
                } catch (ParseException ex) {
                    Logger.getLogger(GestorePrenotazioni.class.getName()).log(Level.SEVERE, null, ex);
                    out.println(ex.getMessage());
                }
                /* parsing pizze da inserire nell'ordine */
                List<Pair<Integer, Integer>> ordine = new ArrayList<>();

                out.println("Numero pizze:" + num_pizze);

                /* inserisci record in Prenotazione */
                for (int i = 0; i < num_pizze; i++) {
                    String id = request.getParameter("id_pizza-" + i);
                    String quantity = request.getParameter("quantity-" + i);                    
                    ordine.add(new Pair<>(new Integer(id), new Integer(quantity)));
                }

                System.out.println("Sintesi ordine: ");
                for (Pair p : ordine) {
                    System.out.println("-" + p);
                }
                
                /* Inserisco record prenotazione nel db */
                String query = "INSERT INTO Prenotazione(fk_utente, data_consegna, ora_consegna) "  +
                        "VALUES (?, ?, ?)";
                try (Connection conn = Query.getConnection();
                        PreparedStatement st = conn.prepareStatement(
                                query, Statement.RETURN_GENERATED_KEYS);) {
                    st.setInt(1, (int) session.getAttribute("idUtente"));
                    st.setDate(2, date);
                    st.setTime(3, time);
                    
                    int affectedRows = st.executeUpdate();
                    if (affectedRows == 0) {
                        System.out.println("ERRORE BOOOH BOOH"); 
                    } else {
                        /* ottengo ID del record appena inserito */
                        try (ResultSet generatedKey = st.getGeneratedKeys()) {
                            if (generatedKey.next()) {
                                long idPrenotazione = generatedKey.getLong(1); 
                                System.out.println("ID generato: " + idPrenotazione);
                                /* inserisco nel db le varie componenti dell'ordine */
                                query = "INSERT INTO PrenotazionePizza (fk_prenotazione, fk_pizza, quantita) " + 
                                        "VALUES(?, ?, ?)";
                                try (PreparedStatement st2 = conn.prepareStatement(query)) {
                                    for (Pair p: ordine) {
                                        st2.setInt(1, (int) idPrenotazione);                                        
                                        st2.setInt(2, (int) p.first);
                                        st2.setInt(3, (int) p.second);
                                        st2.addBatch();
                                    }
                                    st2.executeBatch();
                                    
                                }
                            } else {
                                System.out.println("ERRORE  BOOH");
                            }
                        }
                        catch (SQLException ex2) {
                            System.out.println("E mo?");
                            System.out.println(ex2.getMessage());
                        }
                    }
                } catch (SQLException ex) {
                    out.println("SQL EGGEZZIONALE");
                    System.out.println(ex.getMessage());
                }

                
                /*
                try (Connection conn = Query.getConnection();
                        Statement st = conn.createStatement()) {
                    String query = "INSERT INTO Prenotazione(fk_utente, data_consegna, consegnato)";
                }
                catch (SQLException ex) {
                    ;
                }*/
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
