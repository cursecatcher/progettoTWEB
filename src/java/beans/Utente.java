package beans;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.digest.DigestUtils;

import verifytoken.Verify;

import web.Query;

public class Utente {

    private int idUtente;
    private String email;
    private String password;
    private String role;

    public Utente() {
        idUtente = -1;
        email = "unknown";
        password = null;
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
    
    public String getEmail() {
        return this.email;
    }
    
    public String getRuolo() {
        return this.role;
    }

    /**
     *
     * @param email
     * @param password
     * @return
     */
    public boolean login(String email, String password) {
        boolean res = false;
        email = email.toLowerCase();

        try (Connection conn = Query.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = Query.getUserByEmail(st, email)) {

            if (rs.next()) {
                String usrPassword = rs.getString("password");
                password = DigestUtils.sha1Hex(password);

                if (usrPassword != null && usrPassword.equals(password)) {
                    this.setEmail(email);
                    this.setPassword(null);
                    this.setRole(rs.getString("ruolo"));
                    this.setIdUtente(rs.getInt("id_utente"));
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(Utente.class.getName()).log(Level.SEVERE, null, ex);
        }

        return res;
    }

    /**
     *
     * @param id_token
     * @param access_code
     * @return
     */
    public boolean loginSocial(String id_token, String access_code) {
        String email = Verify.getUserCredentials(id_token, access_code);

        try (Connection conn = Query.getConnection();
                Statement st = conn.createStatement()) {
            /* Verifica la presenza dell'utente registrato; 
             * se il record richiesto non c'è, viene inserito nel DB e recuperato */
            ResultSet rs = Query.getUserByEmail(st, email);

            if (!rs.next()) {
                Query.insertNewClient(st, email, null);
                rs = Query.getUserByEmail(st, email);
                rs.next();
            }

            /* valorizza Utente */
            this.setIdUtente(rs.getInt("id_utente"));
            this.setEmail(email.toLowerCase());
            this.setPassword(null);
            this.setRole(rs.getString("ruolo"));
            
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Utente.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }

    /**
     *
     * @param email
     * @param password
     * @return
     */
    public boolean registrazione(String email, String password) {
        boolean res = false;
        email = email.toLowerCase();
        
        try (Connection conn = Query.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = Query.getUserByEmail(st, email)) {

            /* Utente non presente */ 
            if (!rs.next()) {
                Query.insertNewClient(st, email, password);
                res = true;
            }
            /* utente presente con password null: login social; 
             * associo una password */
            else if (rs.getString("password") == null) {
                Query.updateUserPassword(st, rs.getInt("id_utente"), password);
                res = true; 
            }
        } catch (SQLException ex) {
            Logger.getLogger(Utente.class.getName()).log(Level.SEVERE, null, ex);
        }

        return res;
    }

    public String getPrenotazioniCliente() {
        String html = "";

        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = Query.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Prenotazione");  //where...
        } catch (SQLException ex) {
            Logger.getLogger(Utente.class.getName()).log(Level.SEVERE, null, ex);
        }

        return html;
    }
}
