package beans;

import java.util.ArrayList;
import java.util.Collection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author nico
 */
public class Carrello {

    private ArrayList<ElementoOrdine> ordine = null;

    public Carrello() {
        this.ordine = new ArrayList<>();
    }

    public ArrayList<ElementoOrdine> getOrdine() {
        return ordine;
    }

    public void setOrdine(ArrayList<ElementoOrdine> ordine) {
        this.ordine = ordine;
    }

    public int getLength() {
        int res = 0;

        for (ElementoOrdine e : this.ordine) {
            res += e.getQuantity();
        }

        return res;
    }

    public JSONObject getJSON() {
        JSONObject res = new JSONObject();        
        JSONArray array = new JSONArray();

        try {
            for (ElementoOrdine e: this.ordine) {
                JSONObject je = new JSONObject();
                
                je.accumulate("id_pizza", e.getId()); 
                je.accumulate("nome_pizza", e.getNome()); 
                je.accumulate("quantity", e.getQuantity());
                
                array.put(je); 
            }
            
            res.accumulate("ordine", array); 
            res.accumulate("tot_pizze", this.getLength()); 
            
        } catch (JSONException jex) {
            System.out.println(jex.getMessage());
        }

        return res;
    }
}
