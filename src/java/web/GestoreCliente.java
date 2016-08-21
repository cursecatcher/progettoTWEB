package web;

import beans.Carrello;
import beans.ElementoOrdine;
import beans.Utente;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
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
import org.json.JSONObject;
import org.json.JSONException;

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
            } else if (action.equalsIgnoreCase("ajax-html-cart")) {
                HttpSession session = request.getSession();
                Carrello cart = (Carrello) session.getAttribute("carrello");

                if (cart.isEmpty()) {
                    out.println("Il tuo carrello &egrave; vuoto!<hr>");
                } else {
                    for (ElementoOrdine e : cart.getOrdine()) {
                        out.println("<div class='row' style='padding-left: 1em'>"
                                + "<a class='remove-pizza btn btn-danger btn-xs' href='#0' "
                                + "data-id-pizza='" + e.getId() + "'"
                                + "data-nome-pizza='" + e.getNome() + "'>"
                                + "<span class='glyphicon glyphicon-minus'></span></a>"
                                + e.getQuantity() + " x <strong>" + e.getNome() + "</strong></div>");
                    }
                    out.println("<hr><div class='page-header'>"
                            + "<h3>Totale: <small>"
                            + String.format("%.2f", cart.getPrezzoTotale())
                            + "&nbsp;&euro;</small></h3></div>");
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
                    Utente u = Query.utenteGetByEmail(conn, email);

                    if (u != null) {
                        if (u.getPassword().equals(DigestUtils.sha1Hex(password))) {
                            HttpSession session = request.getSession();
                            session.setAttribute("idUtente", u.getId());
                            session.setAttribute("user", u);
                            session.setAttribute("carrello", new Carrello());
                            session.setAttribute("usertoken", "authenticated");

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
                String nome = request.getParameter("nome");
                String cognome = request.getParameter("cognome");
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                if (password.equals(request.getParameter("password2"))) {
                    if (Query.insertNewClient(email, password, nome, cognome)) {
                        out.print("OK");
                    } else {
                        out.print("USER_ALREADY_EXISTS");
                    }
                } else {
                    out.print("PASSWORD_MISMATCH");
                }

            } else if (action.equalsIgnoreCase("user-change-pwd")) {
                HttpSession session = request.getSession();
                Utente user = (Utente) session.getAttribute("user");

                String old_p = request.getParameter("old-password");
                String new_p = request.getParameter("new-password");
                String confirm_p = request.getParameter("confirm-password");
                String res = "ERR_MISMATCH";

                if (confirm_p.equalsIgnoreCase(new_p)) {
                    if (DigestUtils.sha1Hex(old_p).equalsIgnoreCase(user.getPassword())) {
                        res = Query.utenteUpdatePassword(user.getId(), new_p)
                                ? "OK" : "ERR_UPDATE_FAILED";
                    } else {
                        res = "ERR_OLD_PWD";
                    }

                }

                out.print(res);

                //aggiornare utente in sessione !!
            } else if (action.equalsIgnoreCase("add-to-cart")) {
                HttpSession session = request.getSession();
                Carrello cart = (Carrello) session.getAttribute("carrello");
                ArrayList<ElementoOrdine> ord = (ArrayList) cart.getOrdine();
                int idp = Integer.parseInt(request.getParameter("id_pizza"));
                boolean found = false;

                /* verifica presenza dell'elemento idp all'interno dell'ordine, 
                 * e se è presente aggiunge un'unità del prodotto */
                for (int i = 0; i < ord.size(); i++) {
                    if (ord.get(i).getId() == idp) {
                        ord.get(i).incrementQuantity();
                        found = true;
                        break;
                    }
                }

                // inserisce il nuovo elemento nel carrello 
                if (!found) {
                    ElementoOrdine t = new ElementoOrdine();

                    t.setId(idp);
                    t.setNome(request.getParameter("nome").toUpperCase());
                    t.setPrezzo(Float.parseFloat(request.getParameter("prezzo")));

                    ord.add(t);
                }

                //aggiorna carrello 
                cart.setOrdine(ord);
                session.setAttribute("carrello", cart);

                System.out.println("CARRELLO! -> " + idp);

                response.setContentType("application/json");
                out.write(cart.getJSON().toString());

            } else if (action.equalsIgnoreCase("remove-to-cart")) {
                HttpSession session = request.getSession();
                Carrello cart = (Carrello) session.getAttribute("carrello");
                ArrayList<ElementoOrdine> ord = (ArrayList) cart.getOrdine();
                int idp = Integer.parseInt(request.getParameter("id_pizza"));
                boolean found = false;

                for (int i = 0; i < ord.size(); i++) {
                    ElementoOrdine e = ord.get(i);

                    if (e.getId() == idp) {
                        if (e.getQuantity() > 1) {
                            ord.get(i).decrementQuantity();
                        } else {
                            ord.remove(i);
                        }
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    System.out.println("WTF SITUATION");
                }
                //aggiorna carrello 
                cart.setOrdine(ord);
                session.setAttribute("carrello", cart);
                response.setContentType("application/json");
                out.write(cart.getJSON().toString());

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
    }// </editor-fold>

}
