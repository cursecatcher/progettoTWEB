package beans;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import web.Query;

public class Utente {

    private int id;
    private String email = null;
    private String password = null;
    private String ruolo = null;

    public Utente() {
        id = -1;
        email = null;
        password = null;
        ruolo = "null";
    }

    public Utente(int id) {
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Utente temp = Query.utenteGetById(id);

            if (temp != null) {
                this.id = temp.getId();
                this.email = temp.getEmail();
                this.password = temp.getPassword();
                this.ruolo = temp.getRuolo();
            }
            /*
            Connection conn = Query.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = Query.getUserById(st, id);

            if (rs.next()) {
                this.id = id;
                this.email = rs.getString("email");
                this.ruolo = rs.getString("ruolo");
                this.password = rs.getString("password");
            }

            rs.close();
            st.close();
            conn.close();*/
        } catch (SQLException ex) {
            System.out.println("SMERDO  init utente- " + ex.getMessage());
        }
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRuolo() {
        return ruolo;
    }

    public boolean isAdmin() {
        return this.ruolo.equalsIgnoreCase("admin");
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRuolo(String role) {
        this.ruolo = role;
    }

    public ArrayList<Prenotazione> getPrenotazioni() {
        ArrayList<Prenotazione> prenotazioniUtente = new ArrayList<>();

        try (Connection conn = Query.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = Query.prenotazioneGetByUserID(st, this.id)) {

            while (rs.next()) {
                prenotazioniUtente.add(Query.prenotazioneGetByID(rs.getInt("id_prenotazione")));
            }
        } catch (SQLException ex) {
            System.out.println("SMERDO - getPrenotazioni" + ex.getMessage());
        }

        return prenotazioniUtente;
    }

    
    public ArrayList<Prenotazione> getAllPrenotazioni() {
        ArrayList<Prenotazione> ret = null; 
        
        if (this.isAdmin()) {
            ret = Query.prenotazioniGetAll(); 
        }
        
        return ret; 
    } 

}
