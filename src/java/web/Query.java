package web;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author nico
 */
public class Query {

    private static final String DB_URL = "jdbc:derby://localhost:1527/pizzeriaDB";
    private static final String DB_USER = "nicola";
    private static final String DB_PASSWORD = "asd123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    /**
     *
     * @param st
     * @return
     * @throws SQLException
     */
    public static ResultSet getAllPizze(Statement st) throws SQLException {
        String query = "SELECT * FROM Pizza ORDER BY nome";
        System.out.println("Eseguo query: " + query);
        return st.executeQuery(query);
    }

    /**
     *
     * @param st
     * @return
     * @throws SQLException
     */
    public static ResultSet getAllIngredients(Statement st) throws SQLException {
        String query = "SELECT * FROM Ingrediente ORDER BY nome";
        System.out.println("Eseguo query: " + query);
        return st.executeQuery(query);
    }

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
                + parentize(quote(email.toLowerCase()), password, quote("cliente"));

        System.out.println("Eseguo query: " + query);
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
        String query = "SELECT * FROM Utente WHERE email = " + quote(email.toLowerCase());
        System.out.println("Eseguo query: " + query);
        return st.executeQuery(query);
    }

    public static ResultSet getUserById(Statement st, int id) throws SQLException {
        String query = "SELECT * FROM Utente WHERE id_utente=" + id;
        System.out.println("Eseguo query: " + query);
        return st.executeQuery(query);
    }

    /**
     * Modifica la password di un certo utente di tipo cliente, dato il suo id
     *
     * @param st
     * @param id_cliente primary key utente interessato dal cambio password
     * @param password nuova password dell'utente
     * @return true se l'operazione viene effettuata correttamente, false
     * altrimenti
     * @throws SQLException
     */
    public static boolean updateUserPassword(Statement st, int id_cliente, String password)
            throws SQLException {
        password = quote(DigestUtils.sha1Hex(password));
        String query = "UPDATE Utente SET password=" + password
                + " WHERE id_utente=" + id_cliente;
        System.out.println("Eseguo query: " + query);
        return st.executeUpdate(query) == 1;
    }

    /**
     * Restituisce la tupla associata all'ingrediente identificato dal nome
     *
     * @param st
     * @param nome Nome dell'ingrediente da cercare
     * @return ResultSet associato all'ingrediente
     * @throws SQLException
     */
    public static ResultSet getIngredientByName(Statement st, String nome)
            throws SQLException {
        nome = quote(nome.toLowerCase());
        String query = "SELECT * FROM Ingrediente WHERE nome=" + nome;
        System.out.println("Eseguo query: " + query);
        return st.executeQuery(query);
    }

    /**
     * Inserisce un nuovo ingrediente nel DB
     *
     * @param st Statement
     * @param nome Nome dell'ingrediente da inserire
     * @param prezzo Prezzo dell'ingrediente
     * @return true se l'inserimento è andato a buon fine, false altrimenti
     * @throws SQLException
     */
    public static boolean insertIngrediente(Statement st, String nome, float prezzo)
            throws SQLException {
        nome = quote(nome.toLowerCase());
        String query = "INSERT INTO Ingrediente(nome, prezzo) VALUES "
                + parentize(nome, Float.toString(prezzo));
        System.out.println("Eseguo query: " + query);
        return st.executeUpdate(query) == 1;
    }

    /**
     * Inserisce una nuova pizza nel DB
     *
     * @param st Statement
     * @param nome nome della pizza
     * @param prezzo prezzo
     * @param ingredienti lista ingredienti
     * @return true se l'inserimento va a buon fine, false altrimenti
     * @throws SQLException
     */
    public static boolean insertPizza(Statement st, String nome, float prezzo, String ingredienti)
            throws SQLException {
        nome = quote(nome.toLowerCase());
        String query = "INSERT INTO Pizza(nome, prezzo, ingredienti) VALUES "
                + parentize(nome, Float.toString(prezzo), quote(ingredienti));
        System.out.println("Eseguo query: " + query);
        return st.executeUpdate(query) == 1;
    }

    /**
     *
     * @param st
     * @param nome
     * @return
     * @throws SQLException
     */
    public static ResultSet getPizzaByName(Statement st, String nome)
            throws SQLException {
        nome = quote(nome.toLowerCase());
        String query = "SELECT * FROM Pizza WHERE nome=" + nome;
        System.out.println("Eseguo query: " + query);
        return st.executeQuery(query);
    }

    /**
     * Aggiorna il record specificato tramite ID della tabella Pizza con i dati
     * passati come parametri
     *
     * @param st Statement
     * @param id primary key della pizza da modificare
     * @param nome nome pizza
     * @param prezzo prezzo pizza
     * @param ingredienti lista ingrendienti pizza
     * @return esito operazione (true/false)
     * @throws SQLException
     */
    public static boolean updatePizza(Statement st, int id, String nome, float prezzo, String ingredienti)
            throws SQLException {
        String query = "UPDATE Pizza SET "
                + "nome=" + quote(nome) + ", "
                + "prezzo=" + prezzo + ", "
                + "ingredienti=" + quote(ingredienti) + " "
                + "WHERE id_pizza=" + id;
        System.out.println("Eseguo query: " + query);
        return st.executeUpdate(query) == 1;
    }

    /**
     * Rimuove la pizza con lo specifico ID dal DB
     *
     * @param st Statement
     * @param id primary key del record pizza da cancellare
     * @return true se la cancellazione va a buon fine, false altrimenti
     * @throws SQLException
     */
    public static boolean deletePizza(Statement st, int id)
            throws SQLException {
        String query = "DELETE FROM Pizza WHERE id=" + id;
        System.out.println("Eseguo query: " + query);
        return st.executeUpdate(query) == 1;
    }

    /**
     * Inserisce una nuova prenotazione nel db
     *
     * @param st Statement
     * @param id_utente Id dell'utente a cui appartiene la prenotazione
     * @param consegna
     * @return
     */
    public static int insertNewPrenotazione(Statement st, int id_utente, Date consegna) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        String query = "INSERT INTO Prenotazione(fk_utente, data_consegna) VALUES  "
                + parentize(Integer.toString(id_utente), quote(df.format(consegna)));

        System.out.println("Eseguo query: " + query);

        return 0;
    }


    /* ***************************************************************** */
    private static String quote(String s) {
        return "'" + s + "'";
    }

    private static String parentize(String... data) {
        String res = "";

        for (int i = 0; i < data.length; i++) {
            res += data[i];
            if (i != data.length - 1) {
                res += ", ";
            }
        }

        return "(" + res + ")";
    }
}
