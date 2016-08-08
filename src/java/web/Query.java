package web;

import beans.Ingrediente;
import beans.Pizza;
import beans.Prenotazione;
import beans.Utente;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.digest.DigestUtils;
import com.google.gson.internal.Pair;
//import org.apache.commons.lang3.StringUtils;

/* Fare in modo che i metodi restituiscano non ResultSet, ma direttamente oggetti
 -> incapsulare tutti i metodi del db in questa classe ??*/
public class Query {

    private static final String DB_URL = "jdbc:derby://localhost:1527/pizzeriaDB";
    private static final String DB_USER = "nicola";
    private static final String DB_PASSWORD = "asd123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    /**
     *
     * @return
     */
    public static Collection<Pizza> getPizze() {
        Collection<Pizza> res = new ArrayList<>();
        String query = "SELECT * FROM Pizza ORDER BY nome";
        String iquery = "SELECT * FROM Ingrediente";
        System.out.println("Eseguo query: " + query);

        try (Connection conn = getConnection();
                Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(iquery);
            // carico hashmap <id ingrediente , nome ingrediente>
            HashMap<String, String> ingredientiMap = new HashMap<>();
            while (rs.next()) {
                String key = String.valueOf(rs.getInt("id_ingrediente"));
                ingredientiMap.put(key, rs.getString("nome"));
            }
            rs.close();

            /* faccio le pizze */
            rs = st.executeQuery(query);

            while (rs.next()) {
                String[] ingredienti = rs.getString("ingredienti").split(",");
                String flatlist = "";

                /* creo stringa ingredienti pizza */
                for (String key : ingredienti) {
                    flatlist += ingredientiMap.get(key) + ", ";
                }
                flatlist = flatlist.substring(0, flatlist.length() - 2);

                Pizza p = new Pizza();

                p.setId(rs.getInt("id_pizza"));
                p.setNome(rs.getString("nome"));
                p.setPrezzo(rs.getFloat("prezzo"));
                p.setListIngredienti(flatlist);

                res.add(p);
            }

            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            res = null;
        }

        return res;
    }

    /**
     *
     * @return
     */
    public static Collection<Ingrediente> getIngredienti() {
        String query = "SELECT * FROM Ingrediente ORDER BY nome";
        Collection<Ingrediente> ingredienti = new ArrayList<>();
        System.out.println("Eseguo query: " + query);

        try (Connection conn = getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                Ingrediente i = new Ingrediente();
                i.setId(rs.getInt("id_ingrediente"));
                i.setNome(rs.getString("nome"));
                i.setPrezzo(rs.getFloat("prezzo"));
                ingredienti.add(i);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ingredienti = null;
        }

        return ingredienti;
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
    public static boolean insertNewClient(String email, String password) /*  throws SQLException*/ {
        String query = "INSERT INTO Utente(email, password, ruolo) VALUES (?,?,'cliente')";
        boolean res = false;

        try (Connection conn = getConnection()) {
            /* Controllo se esiste un utente registrato con la stessa mail */
            if (getUserByEmail(conn, email) == null) {
                try (PreparedStatement pst = conn.prepareStatement(query);) {
                    pst.setString(1, email.toLowerCase());
                    pst.setString(2, DigestUtils.sha1Hex(password));
                    res = pst.executeUpdate() == 1;
                }
            }

        } catch (SQLException ex) {
            System.out.println("Eccezione in Query.insertNewClient: " + ex.getMessage());
        }

        return res;
    }

    /*
     * Restituisce il record dell'utente con una determinata email
     *
     * @param st Statement
     * @param email email dell'utente da cercare
     * @return ResultSet contenente il record cercato, se presente
     * @throws SQLException
     *//*
    public static ResultSet getUserByEmail(Statement st, String email)
            throws SQLException {
        String query = "SELECT * FROM Utente WHERE email = " + quote(email.toLowerCase());
        System.out.println("Eseguo query: " + query);
        return st.executeQuery(query);
    }-*/

    public static Utente getUserByEmail(Connection conn, String email) throws SQLException {
        String query = "SELECT * FROM Utente WHERE email = ?";
        Utente u = null;

        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, email);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    u = new Utente();
                    u.setId(rs.getInt("id_utente"));
                    u.setEmail(rs.getString("email"));
                    u.setPassword(rs.getString("password"));
                    u.setRuolo(rs.getString("ruolo"));
                }
            }
        }

        return u;
    }

    public static ResultSet getUserById(Statement st, int id) throws SQLException {
        String query = "SELECT * FROM Utente WHERE id_utente=" + id;
        System.out.println("Eseguo query: " + query);
        return st.executeQuery(query);
    }

    public static Utente getUserById(Connection conn, int id) throws SQLException {
        String query = "SELECT * FROM Utente WHERE id_utente = ?";
        Utente u = null;

        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, id);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    u = new Utente();
                    u.setId(rs.getInt("id_utente"));
                    u.setEmail(rs.getString("email"));
                    u.setPassword(rs.getString("password"));
                    u.setRuolo(rs.getString("ruolo"));
                }
            }
        }

        return u;
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
    private static boolean updateUserPassword(int id_cliente, String password) {
        String query = "UPDATE Utente SET password=? WHERE id_utente=?";
        boolean res = false;

        try (Connection conn = getConnection();
                PreparedStatement pst = conn.prepareStatement(query);) {
            pst.setString(1, DigestUtils.sha1Hex(password));
            pst.setInt(2, id_cliente);
            res = pst.executeUpdate() == 1;
        } catch (SQLException ex) {
            System.out.println("Eccezione in Query.updateUserPassword: " + ex.getMessage());
        }

        return res;
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
        String query = "SELECT * FROM Ingrediente WHERE nome='" + nome.toLowerCase() + "'";
        System.out.println("Eseguo query: " + query);
        return st.executeQuery(query);
    }

    /**
     * Inserisce un nuovo ingrediente nel DB
     *
     * @param nome Nome dell'ingrediente da inserire
     * @param prezzo Prezzo dell'ingrediente
     * @return true se l'inserimento è andato a buon fine, false altrimenti
     */
    public static boolean insertIngrediente(String nome, float prezzo) {
        String query = "INSERT INTO Ingrediente(nome, prezzo) VALUES (?, ?)";
        boolean res = false;

        try (Connection conn = getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = getIngredientByName(st, nome)) {
            if (!rs.next()) {
                try (PreparedStatement pst = conn.prepareStatement(query)) {
                    pst.setString(1, nome.toLowerCase());
                    pst.setFloat(2, prezzo);
                    res = pst.executeUpdate() == 1;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Eccezione in Query.insertIngrediente: " + ex.getMessage());
        }

        return res;
    }

    public static boolean insertPizza(String nome, float prezzo, String ingredienti) {
        String query = "INSERT INTO Pizza(nome, prezzo, ingredienti) VALUES (?,?,?)";
        boolean res = false;

        try (Connection conn = getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = getPizzaByName(st, nome)) {
            if (!rs.next()) {
                try (PreparedStatement pst = conn.prepareStatement(query)) {
                    pst.setString(1, nome.toLowerCase());
                    pst.setFloat(2, prezzo);
                    pst.setString(3, ingredienti);
                    res = pst.executeUpdate() == 1;
                }
            }
        } catch (SQLException ex) {
        }

        return res;
    }

    /**
     *
     * @param st
     * @param nome
     * @return
     * @throws SQLException
     */
    private static ResultSet getPizzaByName(Statement st, String nome)
            throws SQLException {
        String query = "SELECT * FROM Pizza WHERE nome='" + nome.toLowerCase() + "'";
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
    public static boolean updatePizza(int id, String nome, float prezzo, String ingredienti) {
        String query = "UPDATE Pizza SET nome=?, prezzo=?, ingredienti=? WHERE id_pizza=?";
        boolean res = false;

        try (Connection conn = getConnection();
                PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, nome);
            pst.setFloat(2, prezzo);
            pst.setString(3, ingredienti);
            pst.setInt(4, id);
            System.out.println("Eseguo update pizza!");
            res = pst.executeUpdate() == 1;
        } catch (SQLException ex) {
        }

        return res;
    }

    public static boolean deletePizza(int idPizza) {
        String query = "DELETE FROM Pizza WHERE id=?";
        boolean res = false;

        try (Connection conn = Query.getConnection();
                PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, idPizza);
            res = pst.executeUpdate() == 1;

        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }

        return res;
    }

    /**
     * Inserisce una nuova prenotazione nel db
     *
     * @param st Statement
     * @param id_utente Id dell'utente a cui appartiene la prenotazione
     * @param consegna
     * @return
     */
    /*
    public static int insertNewPrenotazione(Statement st, int id_utente, Date consegna) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        String query = "INSERT INTO Prenotazione(fk_utente, data_consegna) VALUES  "
                + parentize(Integer.toString(id_utente), quote(df.format(consegna)));

        System.out.println("Eseguo query: " + query);

        return 0;
    }*/
    public static ResultSet getPrenotazioniByUserId(Statement st, int userId)
            throws SQLException {
        String query = "SELECT * FROM Prenotazione WHERE fk_utente=" + userId;
        System.out.println("Eseguo query: " + query);
        return st.executeQuery(query);
    }

    /**
     * Restituisce un oggetto Prenotazione contenente tutti i dati relativi alla
     * prenotazione specificata tramite id
     *
     * @param id Id della prenotazione della quale recuperare i dati
     * @return
     */
    public static Prenotazione getPrenotazione(int id) {
        Prenotazione pr = null;
        String query = "SELECT * FROM Prenotazione WHERE id_prenotazione=?";
        String query_dettagli
                = "SELECT P.id_prenotazione, PP.fk_pizza, PP.quantita "
                + "FROM Prenotazione AS P, PrenotazionePizza AS PP "
                + "WHERE P.id_prenotazione = PP.fk_prenotazione AND P.id_prenotazione=?";

        try (Connection conn = getConnection()) {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                /* preleva dati generici della prenotazione */
                pr = new Prenotazione();
                pr.setDataConsegna(rs.getDate("data_consegna"));
                pr.setOrarioConsegna(rs.getTime("ora_consegna"));
                pr.setId(rs.getInt("id_prenotazione"));
                pr.setConsegnato(rs.getBoolean("consegnato"));

                rs.close();
                /* preleva dati delle pizze ordinate */
                pst = conn.prepareStatement(query_dettagli);
                pst.setInt(1, id);
                rs = pst.executeQuery();
                
                HashMap<Integer, Pizza> hashPizza = getHashPizza();
                ArrayList<Pair<String, Integer>> ordine = new ArrayList();
                float tot = 0; 

                while (rs.next()) {
                    Pizza current = hashPizza.get(rs.getInt("fk_pizza")); 
                    int quantity = rs.getInt("quantita"); 
                    
                    ordine.add(new Pair(current.getId(), quantity)); 
                    tot += quantity * current.getPrezzo();
/*
                    Pair<String, Integer> pcurrent
                            = new Pair(hashpizza.get(idp), quantity);*/

                }

                pr.setPrezzo(tot);
                pr.setOrdine(ordine);
            }

        } catch (SQLException ex) {
            System.out.println("Eccezione in getPrenotazione: " + ex.getMessage());
        }

        return pr;
    }
    
    public static ArrayList<Prenotazione> getPrenotazioni() {
        ArrayList<Prenotazione> pr = new ArrayList<>();
        String query = "SELECT * FROM ";
        
        
        return pr; 
    }
    

    /**
     * ********************************************************************
     */
    private static HashMap<Integer, Pizza> getHashPizza() {
        HashMap<Integer, Pizza> ret = null;
        String query = "SELECT * FROM Pizza";

        System.out.println("getHashPizza!");

        try (Connection conn = getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query)) {
            ret = new HashMap<>();

            while (rs.next()) {
                Pizza current = new Pizza();
                
                current.setId(rs.getInt("id_pizza"));
                current.setNome(rs.getString("nome"));
                current.setPrezzo(rs.getFloat("prezzo"));
                current.setListIngredienti(rs.getString("ingredienti"));
                
                ret.put(rs.getInt("id_pizza"), current);
            }
        } catch (SQLException ex) {
            System.out.println("... " + ex.getMessage()); 
        }

        return ret;
    }

    private static HashMap<Integer, String> getHashIngredienti() {
        HashMap<Integer, String> ret = new HashMap<>();
        String query = "SELECT id_ingrediente, nome FROM Ingrediente";

        System.out.println("getHashPIngredienti!");

        try (Connection conn = getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                ret.put(rs.getInt("id_ingrediente"), rs.getString("nome"));
            }
        } catch (SQLException ex) {
            System.out.println("... " + ex.getMessage()); 
        }

        return ret;
    }

    /* ***************************************************************** *//*
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
    }*/
}
