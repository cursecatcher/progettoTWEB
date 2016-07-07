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
            ResultSet rs = st.executeQuery("SELECT nome, ingredienti, prezzo FROM Pizza"); 
            
            while (rs.next()) {
                html += rs.getString("nome") + " -- " + rs.getString("ingredienti") + "</br>";
            } 
            
            rs.close(); 
            st.close();
            conn.close(); 
        }
        catch (SQLException ex) {
            Logger.getLogger(Pizza.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return html; 
    }
}
