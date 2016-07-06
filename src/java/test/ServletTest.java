package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import web.Constants;
import web.ServletUtente;

public class ServletTest extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /*
            try {
                Connection conn = DriverManager.getConnection(
                        Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
                Statement st = conn.createStatement();

                String pwdDigest = DigestUtils.sha1Hex("asd");

                String query
                        = "INSERT INTO Utente(email, password, ruolo) VALUES"
                        + "('" + "nicola.licheri@gmail.com" + "', '" + pwdDigest + "', 'admin')";

                if (st.executeUpdate(query) == 1) {
                    out.println("OK");
                } else {
                    out.println("BOH");
                }

                st.close();
                conn.close();

            } catch (SQLException ex) {
                Logger.getLogger(ServletUtente.class.getName()).log(Level.SEVERE, null, ex);
            }*/

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServletTest</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServletTest at " + request.getContextPath() + "</h1>");
            out.println("<h1>Test Sessione</h1>");
            
            HttpSession session = request.getSession();
            Enumeration e = session.getAttributeNames();

            while (e.hasMoreElements()) {
                String name = (String) e.nextElement();
                out.println(name + ": " + session.getAttribute(name) + "<BR>");
            }

            out.println("</body>");
            out.println("</html>");
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
