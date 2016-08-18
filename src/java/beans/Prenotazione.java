package beans;

import java.sql.Time;
import java.sql.Date;
import java.util.Collection;
import java.util.ArrayList;


public class Prenotazione {
    private int id; 
    
    private int fk_utente; 
    private Date dataConsegna; 
    private Time orarioConsegna; 
    private float prezzo; 
//    private Collection<Pair<String, Integer>> ordine; //coppia <nome pizza, quantita>
    private ArrayList<ElementoOrdine> ordine; 
    private boolean consegnato; 
    private Utente proprietario; 
    
    
    public Prenotazione() {
        this.id = -1;
        this.fk_utente = -1; 
        this.dataConsegna = null; 
        this.orarioConsegna = null; 
        this.prezzo = 0; 
        this.ordine = null; 
    }

    public int getId() {
        return this.id;
    }
    
    public int getFkUtente() {
        return this.fk_utente; 
    }
    
    public ArrayList<ElementoOrdine> getOrdine() {
        return this.ordine;
    }
    
    public void setOrdine(ArrayList<ElementoOrdine> ordine) {
        this.ordine = ordine; 
    }
    /*
    public Collection<Pair<String, Integer>> getOrdine() {
        return this.ordine;
    }*/

    public Date getDataConsegna() {
        return this.dataConsegna;
    }

    public Time getOrarioConsegna() {
        return this.orarioConsegna;
    }
    
    public Utente getProprietario() {
        return this.proprietario; 
    }
    
    public float getPrezzo() {
 //       return this.prezzo;
       float tot = 0; 
       
       for (ElementoOrdine e: this.ordine) {
           tot += e.getPrezzo() * e.getQuantity();
       }
       
       return tot; 
    }
    
    public boolean isConsegnato() {
        return consegnato;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setFkUtente(int fk_utente) {
        this.fk_utente = fk_utente; 
    }
    
    public void setDataConsegna(Date dataConsegna) {
        this.dataConsegna = dataConsegna;
    }

    public void setOrarioConsegna(Time orarioConsegna) {
        this.orarioConsegna = orarioConsegna;
    }
/*
    public void setOrdine(Collection<Pair<String, Integer>> ordine) {
        this.ordine = ordine;
    }*/

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    public void setConsegnato(boolean consegnato) {
        this.consegnato = consegnato;
    }
    
    public void setProprietario(Utente u) {
        this.proprietario = u; 
    }
}
