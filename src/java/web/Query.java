package web;

import beans.ElementoOrdine;
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

public class Query {

    private static final String DB_URL = "jdbc:derby://localhost:1527/pizzeriaDB";
    private static final String DB_USER = "nicola";
    private static final String DB_PASSWORD = "asd123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    /**
     * Pizze
     */
    /**
     * Restituisce una collection contenente tutte le pizze presenti nel DB
     *
     * @return ArrayList di oggetti di tipo Pizza
     */
    public static Collection<Pizza> pizzaGetAll() {
        Collection<Pizza> res = new ArrayList<>();
        String query = "SELECT * FROM Pizza ORDER BY nome";
//        String iquery = "SELECT * FROM Ingrediente";
        System.out.println("Eseguo query: " + query);

        try (Connection conn = getConnection();
                Statement st = conn.createStatement()) {
            HashMap<Integer, Ingrediente> ingredientiMap = Query.ingredienteGethashMap();

            /* faccio le pizze */
            try (ResultSet rs = st.executeQuery(query)) {
                while (rs.next()) {
                    String[] ingredienti = rs.getString("ingredienti").split(",");
                    ArrayList<Ingrediente> ingredientiPizza = new ArrayList<>();
                    String flatlist = "";

                    /* creo stringa ingredienti pizza */
                    for (String key : ingredienti) {
                        int ikey = Integer.parseInt(key);
                        Ingrediente curr = ingredientiMap.get(ikey);

                        flatlist += curr.getNome() + ", ";
                        ingredientiPizza.add(curr);

                        //        flatlist += ingredientiMap.get(key) + ", ";
                    }
                    flatlist = flatlist.substring(0, flatlist.length() - 2);

                    Pizza p = new Pizza();

                    p.setId(rs.getInt("id_pizza"));
                    p.setNome(rs.getString("nome"));
                    p.setPrezzo(rs.getFloat("prezzo"));
                    p.setListIngredienti(flatlist);
                    p.setIngredienti(ingredientiPizza);

                    res.add(p);
                }

                //     rs.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            res = null;
        }

        return res;
    }

    /*
    public static boolean pizzaInsert(String nome, float prezzo, String ingredienti) {
        String query = "INSERT INTO Pizza(nome, prezzo, ingredienti) VALUES (?,?,?)";
        boolean res = false;

        try (Connection conn = getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = pizzaGetByName(st, nome)) {
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
    }*/
    public static boolean pizzaInsert(String nome, float prezzo, String[] ingredienti) {
        int key = pizzaInsertRecord(nome, prezzo, String.join(",", ingredienti)); 
        boolean ret = false; 
        
        if (key != -1) {
            ret = pizzaInsertIngredienti(key, ingredienti); 
        }

        return ret;
    }

    private static int pizzaInsertRecord(String nome, float prezzo, String ingredientiFlat) {
        String query = "INSERT INTO Pizza(nome, prezzo, ingredienti) VALUES(?,?, ?)";
        int idPizza = -1; 
        
        try (Connection conn = getConnection();
                PreparedStatement pst
                = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, nome);
            pst.setFloat(2, prezzo);
            pst.setString(3, ingredientiFlat);

            if (pst.executeUpdate() != 0) {
                // ottengo ID del record appena inserito 
                try (ResultSet generatedKey = pst.getGeneratedKeys()) {
                    if (generatedKey.next()) {
                        idPizza = (int) generatedKey.getLong(1);
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Eccezione in pizzaInsertRecord: " + ex.getMessage());
        }
        
        return idPizza;
    }
    
    private static boolean pizzaInsertIngredienti(int idPizza, String[] ingredienti) {
        String query = "INSERT INTO IngredientiPizza(fk_pizza, fk_ingrediente) VALUES (?, ?)";
        boolean ret = false;

        try (Connection conn = getConnection(); 
                PreparedStatement pst = conn.prepareStatement(query)) {

            for (String ingrediente : ingredienti) {
                pst.setInt(1, idPizza);
                pst.setInt(2, Integer.parseInt(ingrediente));
                pst.addBatch();
            }

            pst.executeBatch();
            ret = true;

        } catch (SQLException ex) {
            System.out.println("Eccezione in pizzaInsertIngredienti: " + ex.getMessage());
        }

        return ret;
    }

    public static Pizza pizzaGetById(int id) {
        Pizza p = null;
        String query = "SELECT * FROM Pizza WHERE id_pizza=?";

        try (Connection conn = Query.getConnection();
                PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                HashMap<Integer, Ingrediente> hi = ingredienteGethashMap();

                ///////schifo
                String[] ingredienti = rs.getString("ingredienti").split(",");
                ArrayList<Ingrediente> ingredientiPizza = new ArrayList<>();
                String flatlist = "";

                /* creo stringa ingredienti pizza */
                for (String key : ingredienti) {
                    int ikey = Integer.parseInt(key);
                    Ingrediente curr = hi.get(ikey);

                    flatlist += curr.getNome() + ", ";
                    ingredientiPizza.add(curr);
                }
                flatlist = flatlist.substring(0, flatlist.length() - 2);

                p = new Pizza();
                p.setId(rs.getInt("id_pizza"));
                p.setNome(rs.getString("nome"));
                p.setPrezzo(rs.getFloat("prezzo"));
                p.setListIngredienti(flatlist);
                p.setIngredienti(ingredientiPizza);
                ///////schifo

            }
        } catch (SQLException ex) {
        }

        return p;
    }

    /**
     *
     * @param st
     * @param nome
     * @return
     * @throws SQLException
     */
    private static ResultSet pizzaGetByName(Statement st, String nome)
            throws SQLException {
        String query = "SELECT * FROM Pizza WHERE nome='" + nome.toLowerCase() + "'";
        System.out.println("Eseguo query: " + query);
        return st.executeQuery(query);
    }

    public static String pizzaUpdate(int id, String nome, float prezzo, String ingredienti) {
        String query = "UPDATE Pizza SET nome=?, prezzo=?, ingredienti=? WHERE id_pizza=?";
        String ret = "ERR_UNAVAIABLE_NAME";

        try (Connection conn = getConnection();
                PreparedStatement pst = conn.prepareStatement(query);
                Statement st = conn.createStatement();
                ResultSet pizza = pizzaGetByName(st, nome)) {
            /* Verifica l'univocità del nome della pizza che stiamo andando a inserire: 
            il record viene modificato se il nome non è presente nel db, o se è presente 
            sul record che sta per essere aggiornato */
            if (!pizza.next() || pizza.getInt("id_pizza") == id) {
                pst.setString(1, nome.toLowerCase());
                pst.setFloat(2, prezzo);
                pst.setString(3, ingredienti);
                pst.setInt(4, id);

                System.out.println("Eseguo update pizza!");
                ret = pst.executeUpdate() == 1 ? "OK" : "ERR_BOH";
            }
        } catch (SQLException ex) {
            ret = "ERR_SQL_EXCEPTION";
        }

        return ret;
    }

    public static String pizzaDelete(int idPizza) {
        String query = "DELETE FROM Pizza WHERE id_pizza=?";
        String res = "";

        try (Connection conn = Query.getConnection();
                PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, idPizza);
            res = pst.executeUpdate() == 1 ? "OK" : "ERR_NO_RECORD";

        } catch (SQLIntegrityConstraintViolationException ex) {
            res = "ERR_SQL_ICV";
        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
            res = "ERR_SQL_EXCEPTION";
        }

        return res;
    }

    /**
     * Ingredienti
     */
    /**
     * Resituisce una collection contenente tutti gli ingredienti presenti nel
     * DB
     *
     * @return ArrayList di oggetti di tipo Ingrediente
     */
    public static ArrayList<Ingrediente> ingredienteGetAll() {
        String query = "SELECT * FROM Ingrediente ORDER BY nome";
        ArrayList<Ingrediente> ingredienti = new ArrayList<>();
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
     * Inserisce un nuovo ingrediente nel DB
     *
     * @param nome Nome dell'ingrediente da inserire
     * @param prezzo Prezzo dell'ingrediente
     * @return true se l'inserimento è andato a buon fine, false altrimenti
     */
    public static boolean ingredienteInsert(String nome, float prezzo) {
        String query = "INSERT INTO Ingrediente(nome, prezzo) VALUES (?, ?)";
        boolean res = false;

        try (Connection conn = getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = ingredienteGetByName(st, nome)) {
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

    /**
     * Restituisce la tupla associata all'ingrediente identificato dal nome
     *
     * @param st
     * @param nome Nome dell'ingrediente da cercare
     * @return ResultSet associato all'ingrediente
     * @throws SQLException
     */
    private static ResultSet ingredienteGetByName(Statement st, String nome)
            throws SQLException {
        String query = "SELECT * FROM Ingrediente WHERE nome='" + nome.toLowerCase() + "'";
        System.out.println("Eseguo query: " + query);
        return st.executeQuery(query);
    }

    /**
     * Utenti
     */
    private static ArrayList<Utente> utenteGetAll() {
        ArrayList<Utente> ret = new ArrayList<>();
        String query = "SELECT * FROM Utente";

        try (Connection conn = getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                Utente u = new Utente();

                u.setId(rs.getInt("id_utente"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setRuolo(rs.getString("ruolo"));

                ret.add(u);
            }
        } catch (SQLException ex) {
            System.out.println("Eccezione in UtenteGetAll - " + ex.getMessage());
        }

        return ret;
    }

    /**
     * Inserisce un nuovo utente di tipo cliente nel DB
     *
     * @param email email dell'utente da inserire nel DB
     * @param password password dell'utente. Vale null se si effettua il login
     * social
     * @return true se l'inserimento va a buon fine, false altrimenti
     */
    public static boolean insertNewClient(String email, String password) {
        String query = "INSERT INTO Utente(email, password, ruolo) VALUES (?,?,'cliente')";
        boolean res = false;

        try (Connection conn = getConnection()) {
            /* Controllo se esiste un utente registrato con la stessa mail */
            if (utenteGetByEmail(conn, email) == null) {
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

    /**
     *
     * @param conn Oggetto Connection utilizzato per eseguire la query
     * @param email email dell'utente da trovare
     * @return Oggetto di tipo Utente valorizzato sull'utente trovato, o null se
     * l'email <code>email</code> non è presente nel db
     * @throws SQLException
     */
    public static Utente utenteGetByEmail(Connection conn, String email) throws SQLException {
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

    public static Utente utenteGetById(int id) {
        String query = "SELECT * FROM Utente WHERE id_utente=?";
        Utente u = null;

        try (Connection conn = Query.getConnection();
                PreparedStatement pst = conn.prepareStatement(query)) {
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
        } catch (SQLException ex) {
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
     *//*
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
    }*/

    /**
     * Prenotazioni
     */
    /**
     *
     * @param id_utente
     * @param data
     * @param orario
     * @param ordine
     * @return
     */
    public static boolean prenotazioneInsert(int id_utente, String nominativo, String indirizzo, java.sql.Date data, java.sql.Time orario, ArrayList<ElementoOrdine> ordine) {
        long idp = prenotazioneCreateRecord(id_utente, nominativo, indirizzo, data, orario);
        boolean ret = false;

        if (idp != -1) {
            ret = prenotazioneInsertPizze(idp, ordine);
        }

        return ret;
    }

    private static long prenotazioneCreateRecord(int id_utente, String nominativo, String indirizzo, java.sql.Date data, java.sql.Time orario) {
        String query = "INSERT INTO Prenotazione(fk_utente, nominativo, indirizzo, data_consegna, ora_consegna) "
                + "VALUES (?, ?, ?, ?, ?)";
        long idPrenotazione = -1;

        try (Connection conn = getConnection();
                PreparedStatement pst = conn.prepareStatement(
                        query, Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, id_utente);
            pst.setString(2, nominativo);
            pst.setString(3, indirizzo);
            pst.setDate(4, data);
            pst.setTime(5, orario);

            if (pst.executeUpdate() != 0) {
                // ottengo ID del record appena inserito 
                try (ResultSet generatedKey = pst.getGeneratedKeys()) {
                    if (generatedKey.next()) {
                        idPrenotazione = generatedKey.getLong(1);
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Eccezione in prenotazioneCreateRecord: " + ex.getMessage());
        }

        return idPrenotazione;
    }

    private static boolean prenotazioneInsertPizze(long id_prenotazione, ArrayList<ElementoOrdine> ordine) {
        String query = "INSERT INTO PrenotazionePizza (fk_prenotazione, fk_pizza, quantita) "
                + "VALUES(?, ?, ?)";
        boolean ret = false;

        try (Connection conn = getConnection();
                PreparedStatement pst = conn.prepareStatement(query)) {
            for (ElementoOrdine e : ordine) {
                pst.setInt(1, (int) id_prenotazione);
                pst.setInt(2, e.getId());
                pst.setInt(3, e.getQuantity());
                pst.addBatch();
            }

            pst.executeBatch();
            ret = true;
        } catch (SQLException ex) {
            System.out.println("Eccezione in prenotazioneInsertPizze: " + ex.getMessage());
        }

        return ret;
    }

    public static ResultSet prenotazioneGetByUserID(Statement st, int userId)
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
    public static Prenotazione prenotazioneGetByID(int id) {
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

                HashMap<Integer, Pizza> hashPizza = pizzaGetHashMap();
                ArrayList<ElementoOrdine> ordine = new ArrayList<>();

                //           ArrayList<Pair<String, Integer>> ordine = new ArrayList();
                float tot = 0;

                while (rs.next()) {
                    Pizza currentPizza = hashPizza.get(rs.getInt("fk_pizza"));
                    ElementoOrdine temp = new ElementoOrdine();
                    int quantity = rs.getInt("quantita");

                    temp.setId(currentPizza.getId());
                    temp.setNome(currentPizza.getNome().toUpperCase());
                    temp.setPrezzo(currentPizza.getPrezzo());
                    temp.setQuantity(quantity);

                    ordine.add(temp);
                    tot += quantity * currentPizza.getPrezzo();

                }

                pr.setPrezzo(tot);
                pr.setOrdine(ordine);
            }

        } catch (SQLException ex) {
            System.out.println("Eccezione in getPrenotazione: " + ex.getMessage());
        }

        return pr;
    }

    public static ArrayList<Prenotazione> prenotazioniGetAll() {
        String query = "SELECT * FROM Prenotazione ORDER BY data_consegna, ora_consegna";
        ArrayList<Prenotazione> pr = new ArrayList<>();

        /*
        - prendo tutte le singole prenotazioni dalla tabella Prenotazione
        - per ogni prenotazione p, recupero le pizze che compongono l'ordine
        dalla tabella PrenotazionePizza
         */
        try (Connection conn = getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query)) {

            HashMap<Integer, Pizza> hashPizze = pizzaGetHashMap();
            HashMap<Integer, Utente> hashUtenti = utenteGetHashMap();

            while (rs.next()) {
                Prenotazione p = new Prenotazione();
                int idPrenotazione = rs.getInt("id_prenotazione");

                p.setId(idPrenotazione);
                p.setDataConsegna(rs.getDate("data_consegna"));
                p.setOrarioConsegna(rs.getTime("ora_consegna"));
                p.setConsegnato(rs.getBoolean("consegnato"));
                p.setProprietario(hashUtenti.get(rs.getInt("fk_utente")));

                ArrayList<ElementoOrdine> temp = prenotazioneGetOrdine(idPrenotazione, hashPizze);

                //questa roba è null
                System.out.println((temp == null ? "null" : "not null"));
                for (ElementoOrdine e : temp) {
                    System.out.println(e.getId());
                }

                p.setOrdine(temp);

                pr.add(p);

            }

        } catch (SQLException ex) {
            System.out.println("PrenotazioniGetAll: " + ex.getMessage());
        }

        return pr;
    }

    /**
     * Restituisce il contenuto dell'ordine associata a una prenotazione
     * specificata tramite ID
     *
     * @param id_prenotazione
     * @param hashPizze
     * @return
     */
    private static ArrayList<ElementoOrdine> prenotazioneGetOrdine(int id_prenotazione, HashMap<Integer, Pizza> hashPizze) {
        String query = "SELECT * FROM PrenotazionePizza WHERE fk_prenotazione=?";
        ArrayList<ElementoOrdine> ordine = new ArrayList<>();

        try (Connection conn = getConnection();
                PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, id_prenotazione);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    ElementoOrdine e = new ElementoOrdine();
                    Pizza current = hashPizze.get(rs.getInt("fk_pizza"));

                    e.setId(current.getId());//cos'è?
                    e.setNome(current.getNome()); //serve hashmap pizze
                    e.setPrezzo(current.getPrezzo()); //idem
                    e.setQuantity(rs.getInt("quantita"));

                    ordine.add(e);
                }
            }
        } catch (SQLException ex) {
            ordine = null;
            System.out.println("eccezione in prenotazioneGetOrdine: " + ex.getMessage());
        }

        return ordine;
    }

    public static boolean prenotazioneDelete(int id_prenotazione) {
        String query1 = "DELETE FROM Prenotazione WHERE id_prenotazione=?";
        String query2 = "DELETE FROM PrenotazionePizza WHERE fk_prenotazione=?";
        boolean res = false;

        try (Connection conn = Query.getConnection();
                PreparedStatement st1 = conn.prepareStatement(query1);
                PreparedStatement st2 = conn.prepareStatement(query2)) {
            //transazione???
            st1.setInt(1, id_prenotazione);
            st2.setInt(1, id_prenotazione);

            st2.executeUpdate();
            res = st1.executeUpdate() == 1;
        } catch (SQLException ex) {
            System.out.println("deleteReservation exception: " + ex.getMessage());
        }

        return res;
    }

    public static boolean prenotazioneConfermaConsegna(int id_prenotazione) {
        boolean res = false;
        String query = "UPDATE Prenotazione SET consegnato=true WHERE id_prenotazione=?";

        try (Connection conn = Query.getConnection();
                PreparedStatement st = conn.prepareStatement(query)) {
            st.setInt(1, id_prenotazione);
            res = st.executeUpdate() == 1;

        } catch (SQLException ex) {
            System.out.println("confirmDeliver exception: " + ex.getMessage());
        }

        return res;
    }

    /**
     * ********************************************************************
     */
    private static HashMap<Integer, Pizza> pizzaGetHashMap() {
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

    private static HashMap<Integer, Ingrediente> ingredienteGethashMap() {
        HashMap<Integer, Ingrediente> ret = new HashMap<>();

        Query.ingredienteGetAll().stream().forEach((Ingrediente x) -> {
            ret.put(x.getId(), x);
        });

        return ret;
    }

    private static HashMap<Integer, Utente> utenteGetHashMap() {
        HashMap<Integer, Utente> ret = new HashMap<>();

        Query.utenteGetAll().stream().forEach((Utente x) -> {
            ret.put(x.getId(), x);
        });

        return ret;
    }
}
