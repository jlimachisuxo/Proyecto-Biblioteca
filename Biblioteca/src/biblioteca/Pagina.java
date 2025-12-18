/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biblioteca;

/**
 *
 * @author Joshelyn
 */

public class Pagina {
    private int numeroPagina;
    private String contenido;
    
    // Constructor sin parámetros para Gson
    public Pagina() {
    }
    
    // Constructor original
    public Pagina(int numeroPagina, String contenido) {
        this.numeroPagina = numeroPagina;
        this.contenido = contenido;
    }

    public void mostrarContenido() {
        System.out.println("Página " + numeroPagina + ": " + contenido);
    }
    
    public int getNumeroPagina() {
        return numeroPagina;
    }
    
    public String getContenido() {
        return contenido;
    }
    
    // Setters para Gson
    public void setNumeroPagina(int numeroPagina) {
        this.numeroPagina = numeroPagina;
    }
    
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}