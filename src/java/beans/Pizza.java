package beans;

import java.sql.*;
import java.util.logging.Logger;
import java.util.logging.Level;

import web.Query;

public class Pizza {

    public String getMenu() {
        String html = "";

        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = Query.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Pizza");

            while (rs.next()) {
                String id = "'pizza-" + rs.getInt("id_pizza") + "' ";
                
                html += "<div>"
                        + "<a href='#0' id=" + id + "class='choose-pizza'><span class='glyphicon glyphicon-plus'></span></a>"
                        + rs.getString("nome") + " -- " + rs.getString("ingredienti")
                        + "</div>";
            }

            rs.close();
            st.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Pizza.class.getName()).log(Level.SEVERE, null, ex);
        }

        return html;
    }
}
