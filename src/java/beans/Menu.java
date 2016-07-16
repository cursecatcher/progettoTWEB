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
import java.util.HashMap;
import web.Query;

/**
 *
 * @author nico
 */
public class Menu {

    private Collection<Pizza> pizze = new ArrayList<>();
    private HashMap<String, String> ingredientiMap;

    public Menu() {
        this.ingredientiMap = new HashMap<String, String>();
        System.out.println("CREAZIONE BEAN");

        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = Query.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT id_ingrediente, nome FROM Ingrediente");

            /* carico hashmap */
            while (rs.next()) {
                String key = String.valueOf(rs.getInt("id_ingrediente"));
                this.ingredientiMap.put(key, rs.getString("nome"));
            }

            rs = st.executeQuery("SELECT * FROM Pizza");
            /* faccio le pizze */
            while (rs.next()) {
                String[] ingredienti = rs.getString("ingredienti").split(",");
                String flatlist_ingredienti = "";

                /* creo stringa ingredienti pizza */
                for (String key : ingredienti) {
                    flatlist_ingredienti += this.ingredientiMap.get(key) + ", ";
                }

                flatlist_ingredienti
                        = flatlist_ingredienti.substring(0,
                                flatlist_ingredienti.length() - 2);

                Pizza p = new Pizza();
                p.setId(rs.getInt("id_pizza"));
                p.setNome(rs.getString("nome"));
                p.setPrezzo(rs.getFloat("prezzo"));
                p.setListIngredienti(flatlist_ingredienti);
                pizze.add(p);
            }

            rs.close();
            st.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Collection<Pizza> getPizze() {
        return this.pizze;
    }

}
