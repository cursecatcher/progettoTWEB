/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.ArrayList;

/**
 *
 * @author nico
 */
public class Pizza {
    private int id; 
    private String nome;
    private float prezzo; 
    private String listaIngredienti; 
    private ArrayList<Ingrediente> ingredienti; 
    
    public Pizza() {
        this.id = -1; 
        this.nome = ""; 
        this.prezzo = -1; 
        this.listaIngredienti = ""; 
        this.ingredienti = null; 
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
    
    public void setListIngredienti(String s) {
        this.listaIngredienti = s;
    }
    
    public void setIngredienti(ArrayList<Ingrediente> ingredienti) {
        this.ingredienti = ingredienti;
    }
    
    public String getNome() {
        return this.nome; 
    }
    
    public int getId() {
        return this.id;
    }
    
    public float getPrezzo() {
        return this.prezzo;
    }
    
    public String getListaIngredienti() {
        return this.listaIngredienti;
    }
    
    public ArrayList<Ingrediente> getIngredienti() {
        return this.ingredienti; 
    }
    
    public String getIDIngredienti() {
        String ret = ""; 
        
        for (Ingrediente i: this.ingredienti) {
            ret += i.getId() + " ";
        }
        
        return ret.trim(); 
    }
    
    
}
