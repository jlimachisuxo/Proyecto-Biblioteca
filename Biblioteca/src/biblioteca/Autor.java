/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biblioteca;

/**
 *
 * @author Joshelyn
 */
public class Autor {
    private String nombre;
    private String nacionalidad;
    
    // Constructor sin parámetros para Gson
    public Autor() {
    }
    
    // Constructor original
    public Autor(String nombre, String nacionalidad) {
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
    }

    public void mostrarInfo(){
        System.out.println("Nombre del autor: " + nombre + ", Nacionalidad: " + nacionalidad);
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getNacionalidad() {
        return nacionalidad;
    }
    
    // Setters para Gson y edición
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }
}
