/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import web.Query;

/**
 *
 * @author nicol
 */
public class Ingredienti {
    private ArrayList<Ingrediente> listaIngredienti;

    public Ingredienti() {
        this.listaIngredienti = (ArrayList) Query.ingredienteGetAll(); 
    }

    public Collection<Ingrediente> getListaIngredienti() {
        return this.listaIngredienti;
    }
}
