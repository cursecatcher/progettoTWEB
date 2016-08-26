package beans;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;
import web.Query;

public class Utente {

    private int id;
    private String email = null;
    private String password = null;
    private String nome = null; 
    private String cognome = null; 
    private String ruolo = null;

    public Utente() {
        id = -1;
        email = null;
        password = null;
        ruolo = "nd";
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
                this.nome = temp.getNome();
                this.cognome = temp.getCognome();
            }
            
        } catch (SQLException ex) {
            System.out.println("Eccezione in init utente- " + ex.getMessage());
        }
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
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

    public ArrayList<Prenotazione> getPrenotazioniConsegnate() {
        return this.getPrenotazioni()
                .stream()
                .filter((Prenotazione p) -> p.isConsegnato())
                .collect(Collectors.toCollection(ArrayList::new));
    }
    
    public ArrayList<Prenotazione> getPrenotazioniNonConsegnate() {
        return this.getPrenotazioni()
                .stream()
                .filter((Prenotazione p) -> !p.isConsegnato())
                .collect(Collectors.toCollection(ArrayList::new));
    }
    
    
    
    
    public ArrayList<Prenotazione> getAllPrenotazioni() {
        ArrayList<Prenotazione> ret = null; 
        
        if (this.isAdmin()) {
            ret = Query.prenotazioniGetAll(); 
        }
        
        return ret; 
    } 

}
