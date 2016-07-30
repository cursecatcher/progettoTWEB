package beans;

import java.sql.Time;
import java.sql.Date;
import java.util.Collection;
import com.google.gson.internal.Pair;


public class Prenotazione {
    private int id; 
    
    private Date dataConsegna; 
    private Time orarioConsegna; 
    private float prezzo; 
    private Collection<Pair<String, Integer>> ordine; //coppia <nome pizza, quantita>
    private boolean consegnato; 
    
    
    public Prenotazione() {
        this.id = -1;
        this.dataConsegna = null; 
        this.orarioConsegna = null; 
        this.prezzo = 0; 
        this.ordine = null; 
    }

    public int getId() {
        return this.id;
    }
    
    public Collection<Pair<String, Integer>> getOrdine() {
        return this.ordine;
    }

    public Date getDataConsegna() {
        return this.dataConsegna;
    }

    public Time getOrarioConsegna() {
        return this.orarioConsegna;
    }
    
    public float getPrezzo() {
        return this.prezzo;
    }
    
    public boolean isConsegnato() {
        return consegnato;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setDataConsegna(Date dataConsegna) {
        this.dataConsegna = dataConsegna;
    }

    public void setOrarioConsegna(Time orarioConsegna) {
        this.orarioConsegna = orarioConsegna;
    }

    public void setOrdine(Collection<Pair<String, Integer>> ordine) {
        this.ordine = ordine;
    }

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    public void setConsegnato(boolean consegnato) {
        this.consegnato = consegnato;
    }
    
    
    
}
