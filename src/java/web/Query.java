/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.sql.*;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author nico
 */
public class Query {

    /**
     * Inserisce un nuovo utente di tipo cliente nel DB
     * 
     * @param st Statement
     * @param email email dell'utente da inserire nel DB
     * @param password password dell'utente. Vale null se si effettua il login
     * social
     * @return true se l'inserimento va a buon fine, false altrimenti
     * @throws SQLException
     */
    public static boolean insertNewClient(Statement st, String email, String password)
            throws SQLException {
        password = password == null
                ? "null" : quote(DigestUtils.sha1Hex(password));

        String query = "INSERT INTO UTENTE (email, password, ruolo) VALUES"
                + "(" + quote(email) + ", " + password + ", 'cliente')";

        return st.executeUpdate(query) == 1;
    }

    /**
     * Restituisce il record dell'utente con una determinata email 
     * 
     * @param st Statement
     * @param email email dell'utente da cercare
     * @return ResultSet contenente il record cercato, se presente
     * @throws SQLException
     */
    public static ResultSet getUserByEmail(Statement st, String email)
            throws SQLException {
        return st.executeQuery("SELECT * FROM Utente WHERE email = " + quote(email));
    }

    /**
     * Modifica la password di un certo utente di tipo cliente, dato il suo id
     * 
     * @param st
     * @param id_cliente primary key utente interessato dal cambio password
     * @param password nuova password dell'utente
     * @return true se l'operazione viene effettuata correttamente, false altrimenti
     * @throws SQLException 
     */
    public static boolean changeClientPassword(Statement st, int id_cliente, String password)
            throws SQLException {
        String query = "UPDATE Utente SET password=" + quote(DigestUtils.sha1Hex(password)
                + " WHERE id_utente=" + id_cliente);
        return st.executeUpdate(query) == 1;
    }

    private static String quote(String s) {
        return "'" + s + "'";
    }
}
