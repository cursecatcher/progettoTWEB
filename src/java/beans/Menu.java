/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import web.Query;

/**
 *
 * @author nico
 */
public class Menu {
    private Collection<Pizza> pizze = new ArrayList<Pizza>();
    
    public Menu() {
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = Query.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Pizza");
            
            System.out.println("CREAZIONE BEAN");
            
            while (rs.next()) {
                Pizza p = new Pizza(); 
                
                p.setId(rs.getInt("id_pizza"));
                p.setNome(rs.getString("nome"));
                p.setPrezzo(rs.getFloat("prezzo"));
                
                pizze.add(p); 
            }
            
            rs.close();
            st.close();
            conn.close();
        }
        catch (SQLException ex) {
            ;
        }
    }
    
    public Collection<Pizza> getPizze() {
        return this.pizze;
    }
    
}
