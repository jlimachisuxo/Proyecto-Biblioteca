/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biblioteca;

/**
 *
 * @author Joshelyn
 */
public class Estudiante {
    private String codigoEstudiante;
    private String nombre;
    
    // Constructor sin parámetros para Gson
    public Estudiante() {
    }
    
    // Constructor original
    public Estudiante(String codigoEstudiante, String nombre) {
        this.codigoEstudiante = codigoEstudiante;
        this.nombre = nombre;
    }
    
    public void mostrarInfo(){
        System.out.println("Código del estudiante: " + codigoEstudiante + ", Nombre: " + nombre);
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getCodigoEstudiante() {
        return codigoEstudiante;
    }
    
    // Setters para Gson y edición
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setCodigoEstudiante(String codigoEstudiante) {
        this.codigoEstudiante = codigoEstudiante;
    }
}
