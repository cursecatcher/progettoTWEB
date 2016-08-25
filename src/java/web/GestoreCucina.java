package web;

import beans.Ingrediente;
import beans.Pizza;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
            } else if (action.equalsIgnoreCase("ajax-update-admin-pizzas")) {
                ArrayList<Pizza> pizze = Query.pizzaGetAll();

                for (Pizza p : pizze) {
                    out.write(
                            "<div id='pizza-" + p.getId() + "' class='row-fluid'>\n"
                            + "<div class='row'>\n"
                            + "<div class='col-md-8'>\n"
                            + "<span class='pizza-nome text-uppercase'>\n"
                            + "<h4>\n"
                            + p.getNome()
                            + "</h4>\n"
                            + "</span>\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<div class='row'>\n"
                            + "<div class='col-md-8 text-lowercase'>\n"
                            + p.getListaIngredienti()
                            + "</div>\n"
                            + "<div class='col-md-2 text-muted'>\n"
                            + "<strong>\n"
                            + String.format("%.2f", p.getPrezzo())
                            //   + "<fmt:formatNumber type='number' value='${pizza.prezzo}' minFractionDigits='2'/>\n"
                            + "&nbsp;&euro;\n"
                            + "</strong>\n"
                            + "</div>\n"
                            + "<div class='col-md-2'>\n"
                            + "<a href='#0' class='edit-link btn btn-primary btn-xs' data-id='" + p.getId() + "'>\n"
                            + "<span class='glyphicon glyphicon-pencil'></span>\n"
                            + "</a>\n"
                            + "<a href='#0' class='delete-link btn btn-danger btn-xs' data-id='" + p.getId() + "'>\n"
                            + "<span class='glyphicon glyphicon-remove'></span>\n"
                            + "</a>\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<hr>"
                            + "</div>\n");

                    /*
                    out.write("<div id=\"pizza-" + p.getId() + "\" class=\"row-fluid\">");
                    out.write("<div class=\"row-fluid\">\n"
                            + "<div class=\"col-md-8 text-uppercase\">\n"
                            + "<h4>" + p.getNome() + "</h4>\n"
                            + "</div>\n"
                            + "<div class=\"col-md-2\">\n"
                            + "<h4><small>" + p.getPrezzo() + "&nbsp;&euro;</small></h4>\n"
                            + "</div>\n"
                            + "<div class=\"col-md-2\">\n"
                            + "</div>\n"
                            + "</div>");

                    out.write("<div class=\"row-fluid\">\n"
                            + "<div class=\"col-md-9\">\n"
                            + p.getListaIngredienti()
                            + "</div>\n"
                            + "<div class=\"col-md-1\"></div>\n"
                            + "<div class=\"col-md-2 pull-right\">");

                    out.write("<a href=\"#0\" class=\"edit-link btn btn-primary btn-xs\" data-id=\"" + p.getId() + "\">\n"
                            + "<span class=\"glyphicon glyphicon-pencil\"></span>\n"
                            + "</a>\n"
                            + "<a href=\"#0\" class=\"delete-link btn btn-danger btn-xs\" data-id=\"" + p.getId() + "\">\n"
                            + "<span class=\"glyphicon glyphicon-remove\"></span>\n"
                            + "</a>");

                    out.write("</div></div></div></hr>");*/
                }

            } else if (action.equalsIgnoreCase("ajax-html-ingr")) {
                ArrayList<Ingrediente> ingredienti = Query.ingredienteGetAll();

                for (Ingrediente i : ingredienti) {
                    out.println("<div class='row'>"
                            + "<div class='col-md-8 text-uppercase'>"
                            + "<h4>"
                            + i.getNome()
                            //                  + "<c:out value='${ingrediente.nome}'/>"
                            + "</h4>"
                            + "</div>"
                            + "<div class='col-md-4'>"
                            + "<h4>"
                            + "<small>"
                            + String.format("%.2f", i.getPrezzo())
                            //                            + "<fmt:formatNumber type='number' value='${ingrediente.prezzo}' minFractionDigits='2'/>"
                            + "&nbsp;&euro;"
                            + "</small>"
                            + "</h4>"
                            + "</div>"
                            + "</div>");
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
        RequestDispatcher rd = null;

        try (PrintWriter out = response.getWriter()) {
            String action = request.getParameter("action");

            if (action == null) {
                rd = ctx.getRequestDispatcher("/index.jsp");

            } else if (action.equalsIgnoreCase("pizza-create")) {
                String nome = request.getParameter("nome").trim();
                float prezzo = 0;
                String[] ingredienti = request.getParameterValues("ingredientiPizza");
                String err = "";

                if (nome.equalsIgnoreCase("")) {
                    err = "ERR_MISSING_NAME";
                } else if (ingredienti.length == 0) {
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
                    err = Query.pizzaInsert(nome, prezzo, ingredienti)
                            ? "OK" : "ERR_DUPLICATE";
                }

                out.print(err);

            } else if (action.equalsIgnoreCase("pizza-remove")) {
                int id = Integer.parseInt(request.getParameter("id_pizza"));
                String res = Query.pizzaDelete(id);
                out.print(res);

            } else if (action.equalsIgnoreCase("pizza-edit")) {
                int id = Integer.parseInt(request.getParameter("id"));
                String nome = request.getParameter("nome").trim();
                float prezzo = 0;
                String[] ingredienti = request.getParameterValues("ingredientiPizza");
                String err = "";

                if (nome.equalsIgnoreCase("")) {
                    err = "ERR_MISSING_NAME";
                } else if (ingredienti.length == 0) {
                    err = "ERR_NO_INGREDIENTI";
                } else {
                    try {
                        prezzo = Float.parseFloat(request.getParameter("prezzo"));

                        if (prezzo < 0) {
                            err = "ERR_NEGATIVE_PRICE";
                        }
                    } catch (NumberFormatException ex) {
                        err = "ERR_PARSE_FLOAT";
                    }
                }

                if (err.equalsIgnoreCase("")) {
                    //                 String ingrString = String.join(",", ingredienti);
                    err = Query.pizzaUpdate(id, nome, prezzo, ingredienti);

                }

                out.print(err);
                System.out.println("Esito update: " + err);

            } else if (action.equalsIgnoreCase("ingrediente-add")) {
                String nome = request.getParameter("nome");
                String res = "";
                float prezzo = 0;

                try {
                    prezzo = Float.valueOf(request.getParameter("prezzo"));
                } catch (NumberFormatException ex) {
                    res = "ERR_PARSEFLOAT";
                }

                if (res.equalsIgnoreCase("")) {
                    if (Query.ingredienteInsert(nome, prezzo)) {
                        res = "OK";
                    } else {
                        System.out.println("Ingrediente gia' presente :v");
                        res = "ERR_DUPLICATE";
                    }
                }

                out.print(res);

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
