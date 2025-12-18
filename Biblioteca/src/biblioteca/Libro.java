/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biblioteca;

/**
 *
 * @author Joshelyn
 */
import java.util.ArrayList;
import java.util.List;

public class Libro {
    private String titulo;
    private String ISBN;
    private List<Pagina> paginas;
    
    // Constructor sin parámetros para Gson
    public Libro() {
        this.paginas = new ArrayList<>();
    }
    
    // Constructor original
    public Libro(String titulo, String ISBN, List<String> contenidoPaginas) {
        this.titulo = titulo;
        this.ISBN = ISBN;
        this.paginas = new ArrayList<>();
        // composicion libro crea sus paginas
        for(int i=0; i<contenidoPaginas.size(); i++){
            this.paginas.add(new Pagina(i + 1, contenidoPaginas.get(i)));
        } 
    }
    
    // metodo
    public void leer(){
        System.out.println("Leyendo el libro: " + titulo);
        for(Pagina pagina : paginas){
            pagina.mostrarContenido();
        }
        System.out.println("---Fin del libro---");
    }

    public String getTitulo(){
        return titulo;
    }
    
    public String getISBN() {
        return ISBN;
    }
    
    public int getNumeroPaginas() {
        return paginas != null ? paginas.size() : 0;
    }
    
    public String obtenerContenidoCompleto() {
        StringBuilder contenido = new StringBuilder();
        if (paginas != null) {
            for (Pagina pagina : paginas) {
                contenido.append("Página ").append(pagina.getNumeroPagina())
                        .append(": ").append(pagina.getContenido()).append("\n");
            }
        }
        return contenido.toString();
    }
    
    // Setters para Gson
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
}
