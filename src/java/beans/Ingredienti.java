/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;

import org.json.JSONObject;
import web.Query;

/**
 *
 * @author nicol
 */
public class Ingredienti {

    /**
     * Rappresenta un singolo ingrediente (terna <id,nome,prezzo>) accessibile
     * tramite metodi get&set (solo get per ora)
     */
    class Ingrediente {

        private final int id;
        private final String nome;
        private final float prezzo;

        public Ingrediente(int id, String nome, float prezzo) {
            this.id = id;
            this.nome = nome;
            this.prezzo = prezzo;
        }

        public int getId() {
            return this.id;
        }

        public String getNome() {
            return this.nome;
        }

        public float getPrezzo() {
            return this.prezzo;
        }

        public JSONObject toJSON() {
            JSONObject json = new JSONObject();

            try {
                json.accumulate("id", this.id);
                json.accumulate("nome", this.nome);
                json.accumulate("prezzo", this.prezzo);
            } catch (JSONException ex) {
                Logger.getLogger(Ingredienti.class.getName()).log(Level.SEVERE, null, ex);
            }

            return json;
        }
        //   private static final Logger LOG = Logger.getLogger(Ingrediente.class.getName());
    }

    private ArrayList<Ingrediente> listaIngredienti;

    public Ingredienti() {
        this.listaIngredienti = new ArrayList<Ingrediente>();
        try {
            System.out.println("DriverManager.registerDriver Ingredienti Bean");
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = Query.getConnection();
            Statement st = conn.createStatement();
            String query = "SELECT * FROM Ingrediente"; 
            ResultSet rs = st.executeQuery(query); 
            
            while (rs.next()) {
                int id = rs.getInt("id_ingrediente");
                String nome = rs.getString("nome"); 
                float prezzo = rs.getFloat("prezzo"); 
                this.listaIngredienti.add(new Ingrediente(id, nome, prezzo));
            }
            
            rs.close();
            st.close();
            conn.close(); 

        } catch (SQLException ex) {
            System.out.println("Ingredienti Bean - qualcosa e' andato storto");
            Logger.getLogger(Ingredienti.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getListaIngredienti() {
        String html = "";

        if (listaIngredienti != null) {
            for (final Ingrediente s : listaIngredienti) {
                html += "<option value='" + s.getId() + "'>";
                html += s.getNome() + "</option>";
            }
        }

        return html;
    }
}
