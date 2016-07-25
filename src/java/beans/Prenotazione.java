package beans;

import java.sql.Time;
import java.sql.Date;
import java.util.Collection;
import org.apache.commons.lang3.tuple.Pair;


public class Prenotazione {
    private int id; 
    private Date dataConsegna; 
    private Time orarioConsegna; 
    private float prezzo; 
    private Collection<Pair<Pizza, Integer>> ordine; 
    
    
    public Prenotazione() {
        this.dataConsegna = null; 
        this.orarioConsegna = null; 
        this.prezzo = 0; 
    }

    public int getId() {
        return this.id;
    }
    
    public Collection<Pair<Pizza, Integer>> getOrdine() {
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

    public void setId(int id) {
        this.id = id;
    }
    
    public void setDataConsegna(Date dataConsegna) {
        this.dataConsegna = dataConsegna;
    }

    public void setOrarioConsegna(Time orarioConsegna) {
        this.orarioConsegna = orarioConsegna;
    }

    public void setPrezzoTotale(float prezzo) {
        this.prezzo = prezzo; 
    }
}
