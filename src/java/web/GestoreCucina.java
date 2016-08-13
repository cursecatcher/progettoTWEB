package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.util.regex.Pattern.compile;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GestoreCucina extends HttpServlet {

    public void init(ServletConfig config) throws ServletException {
        System.out.println("Init GestoreCucina");
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
                String nome = request.getParameter("nome").trim();
                String id_ingredienti = request.getParameter("listaIngredienti").trim();
                float prezzo = 0;  
                String err = "";

                // controllo input
                if (nome.equalsIgnoreCase("")) {
                    err = "ERR_MISSING_NAME";
                } else if (id_ingredienti.equalsIgnoreCase("")) {
                    err = "ERR_NO_INGREDIENTI";
                } else {
                    try {
                        prezzo = Float.parseFloat(request.getParameter("prezzo").trim());
                        if (prezzo <= 0) {
                            err = "ERR_PARSE_FLOAT"; 
                        }
                    } catch (NumberFormatException ex) {
                        err = "ERR_PARSE_FLOAT";
                        System.out.println("Eccezione -" + ex.getMessage()); 
                    }
                }
                
                if (err.equalsIgnoreCase("")) {
                    err = Query.insertPizza(nome, prezzo, id_ingredienti) 
                            ? "OK" : "ERR_DUPLICATE"; 
                }

                System.out.println("pizza-create - err: " + err);
                out.print(err);

            } else if (action.equalsIgnoreCase("pizza-remove")) {
                int id = Integer.parseInt(request.getParameter("id_pizza"));

                try (Connection conn = Query.getConnection();
                        Statement st = conn.createStatement()) {

                    if (Query.deletePizza(id)) {
                        System.out.println("OK");
                        out.println("OK");
                    } else {
                        System.out.println("ERR");
                        out.println("ERR");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(GestoreCucina.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Eccezione in ServletGnam->pizza-remove");
                }

            } else if (action.equalsIgnoreCase("pizza-edit")) {
                int id = Integer.parseInt(request.getParameter("id_pizza"));
                String nome = request.getParameter("nome");
                float prezzo = Float.parseFloat(request.getParameter("prezzo"));
                String listaIngredienti = request.getParameter("lista_ingredienti");

                try (Connection conn = Query.getConnection();
                        Statement st = conn.createStatement()) {
                    /*
                    if (Query.updatePizza(st, id, nome, prezzo, listaIngredienti)) {
                        out.println("OK");
                        System.out.print("OK");
                    } else {
                        out.print("ERR_DUPLICATE");
                        System.out.println("ERR_DUPLICATE");
                    } */
                } catch (SQLException ex) {
                    Logger.getLogger(GestoreCucina.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Eccezione in ServletGnam->pizza-edit");
                    out.print("ERR_SQL_EXCEPTION");
                }

            } else if (action.equalsIgnoreCase("ingrediente-add")) {
                String nome = request.getParameter("nome");
                float prezzo = Float.valueOf(request.getParameter("prezzo"));

                if (Query.insertIngrediente(nome, prezzo)) {
                    out.print("OK");
                } else {
                    System.out.println("Ingrediente gia' presente :v");
                    out.print("ERR_DUPLICATE");
                }
                /*
                try (Connection conn = Query.getConnection();
                        Statement st = conn.createStatement();
                        ResultSet rs = Query.getIngredientByName(st, nome)) {

                    if (!rs.next()) {
                        Query.insertIngrediente(nome, prezzo);
                        out.print("OK");
                    } else {
                        System.out.println("Ingrediente gia' presente :v");
                        out.print("ERR_DUPLICATE");
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(GestoreCucina.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Eccezione in ServletGnam->ingrediente-add");
                    out.print("ERR_SQL_EXCEPTION");
                }*/
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
