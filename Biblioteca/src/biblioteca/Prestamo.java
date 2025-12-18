/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biblioteca;

/**
 *
 * @author Joshelyn
 */
import java.time.LocalDate;

public class Prestamo {
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private Estudiante estudiante;
    private Libro libro;
    
    // Constructor sin parámetros para Gson
    public Prestamo() {
    }
    
    // Constructor original
    public Prestamo(Estudiante estudiante, Libro libro){
        this.estudiante = estudiante;
        this.libro = libro;
        this.fechaPrestamo = LocalDate.now();
        this.fechaDevolucion = this.fechaPrestamo.plusDays(7);
        System.out.println("Préstamo creado para: " + estudiante.getNombre() + " Libro: " + libro.getTitulo());
        System.out.println("");
    }
    
    // metodo
    public void mostrarInfo(){
        System.out.println("    Detalles del préstamo:");
        System.out.println("Libro: " + libro.getTitulo());
        System.out.println("Estudiante: " + estudiante.getNombre());
        System.out.println("Fecha de préstamo: " + fechaPrestamo);
        System.out.println("Fecha de devolución: " + fechaDevolucion);
    }
    
    // Getters para la interfaz gráfica
    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }
    
    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }
    
    public Estudiante getEstudiante() {
        return estudiante;
    }
    
    public Libro getLibro() {
        return libro;
    }
    
    // Setters para Gson
    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }
    
    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }
    
    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
    
    public void setLibro(Libro libro) {
        this.libro = libro;
    }
}
