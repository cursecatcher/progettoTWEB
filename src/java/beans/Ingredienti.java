/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import web.Query;

/**
 *
 * @author nicol
 */
public class Ingredienti {
    private ArrayList<Ingrediente> listaIngredienti;

    public Ingredienti() {
        this.listaIngredienti = new ArrayList<Ingrediente>();
        try {
            System.out.println("DriverManager.registerDriver Ingredienti Bean");
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = Query.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Ingrediente"); 
            
            while (rs.next()) {
                Ingrediente i = new Ingrediente(); 
                
                i.setId(rs.getInt("id_ingrediente"));
                i.setNome(rs.getString("nome"));
                i.setPrezzo(rs.getFloat("prezzo"));
                
                this.listaIngredienti.add(i);
            }
            
            rs.close();
            st.close();
            conn.close(); 

        } catch (SQLException ex) {
            System.out.println("Ingredienti Bean - qualcosa e' andato storto");
            Logger.getLogger(Ingredienti.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Collection<Ingrediente> getListaIngredienti() {
        return this.listaIngredienti;
    }
    
}
