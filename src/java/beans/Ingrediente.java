/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author nico
 */
public class Ingrediente {
    private int id; 
    private String nome; 
    private float prezzo; 
    
    public Ingrediente() {
        this.id = -1;
        this.nome = ""; 
        this.prezzo = 0; 
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

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }
    
    
}
