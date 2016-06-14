/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

/**
 *
 * @author nicol
 */
public class Constants {
    /* Nomi per la connessione al database */
    public static final String DB_URL = "jdbc:derby://localhost:1527/pizzeriaDB";  
    public static final String DB_USER = "nicola"; 
    public static final String DB_PASSWORD = "asd123"; 
    /* Ingredienti di default - x creare pizze nel menu' anche per i meno fortunati */
    public static final String INGREDIENTI_BASE = "pomodoro,mozzarella";
    public static final String INGREDIENTI_BASE_BIANCA = "mozzarella";
    public static final String INGREDIENTI_BASE_NO_LATT = "pomodoro";  
}
