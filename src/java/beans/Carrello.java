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

    public Collection<ElementoOrdine> getOrdine() {
        return ordine;
    }

    public void setOrdine(ArrayList<ElementoOrdine> ordine) {
        this.ordine = ordine;
    }

    public int getLength() {
        return this.ordine.stream().
                map((e) -> e.getQuantity()).
                reduce(0, Integer::sum);
    }
    
    public boolean isEmpty() {
        return this.ordine.size() == 0; 
    }

    public float getPrezzoTotale() {
        return this.ordine.stream().
                map((e) -> e.getPrezzo() * e.getQuantity()).
                reduce((float) 0, (accumulator, _item) -> accumulator + _item);
    }

    public JSONObject getJSON() {
        JSONObject res = new JSONObject();
        
        try {
            JSONArray array = new JSONArray();
            
            for (ElementoOrdine e : this.ordine) {
                JSONObject je = new JSONObject();

                je.accumulate("id_pizza", e.getId());
                je.accumulate("nome_pizza", e.getNome());
                je.accumulate("quantity", e.getQuantity());

                array.put(je);
            }

            res.accumulate("ordine", array);
            res.accumulate("tot_pizze", this.getLength());
            res.accumulate("prezzo_tot", this.getPrezzoTotale());

        } catch (JSONException jex) {
            System.out.println(jex.getMessage());
        }

        return res;
    }
}
