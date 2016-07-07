package beans;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import web.Query;

public class Utente {
    private int idUtente; 
    private String email; 
    private String password; 
    private String role; 
    
    
    public Utente() {
        idUtente = -1; 
        email = "unknown"; 
        password = ""; 
        role = "not logged"; 
    }
    
    public void setIdUtente(int id) {
        this.idUtente = id; 
    }
    
    public void setEmail(String email) {
        this.email = email; 
    }
    
    public void setPassword(String password) {
        this.password = password; 
    }
    
    public void setRole(String role) {
        this.role = role; 
    }
    
    public String getPrenotazioni() {
        String html = ""; 
        
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
           Connection conn = Query.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Prenotazione" );  //where...
        }
        catch (SQLException ex) {
            Logger.getLogger(Utente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return html; 
    }
}
