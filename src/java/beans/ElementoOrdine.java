package beans;


public class ElementoOrdine {
    private int id; //id pizza
    private String nome; 
    private float prezzo; 
    private int quantity; 
    
    public ElementoOrdine() {
        this.id = -1; 
        this.nome = "RANDOM"; 
        this.prezzo = -1; 
        this.quantity = 1; 
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
    public void incrementQuantity() {
        this.quantity++; 
    }
    
    public void decrementQuantity() {
        this.quantity--; 
    }
}
