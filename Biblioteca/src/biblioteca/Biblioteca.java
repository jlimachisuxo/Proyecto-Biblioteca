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

public class Biblioteca {
    private String nombre;
    private List<Libro> librosDisponibles;
    private List<Autor> autoresRegistrados;
    private List<Prestamo> prestamosActivos;
    private Horario horarioAtencion;
    // constructor
    public Biblioteca(String nombre){
        this.nombre = nombre;
        this.librosDisponibles = new ArrayList<>();
        this.autoresRegistrados = new ArrayList<>();
        this.prestamosActivos = new ArrayList<>();
        // composicion biblioteca crea su horario
        this.horarioAtencion = new Horario("Lunes a Viernes", "8:00 am", "18:00 pm");    
    }
    // metodos
    public void agregarLibro(Libro libro){
        this.librosDisponibles.add(libro);
        System.out.println("Libro agregado: " + libro.getTitulo());
    }
    public void agregarAutor(Autor autor){
        this.autoresRegistrados.add(autor);
        System.out.println("Autor registrado: " + autor.getNombre());
    }
    public Prestamo prestarLibro(Estudiante estudiante, Libro libro){
        if (librosDisponibles.contains(libro)) {
            Prestamo nuevPrestamo =  new Prestamo(estudiante, libro);
            prestamosActivos.add(nuevPrestamo);
            //librosDisponibles.remove(libro);
            return nuevPrestamo;
        } else{
            System.out.println("El libro" +  libro.getTitulo() + " no esta disponible en la biblioteca.");
            return null;
        }
    }

    public void mostrarEstado(){
        System.out.println("\n*********************************************************************");
        System.out.println("ESTADO ACTUAL DE LA BIBLIOTECA: " + nombre);
        // Muestra Composición (Horario)
        if (horarioAtencion != null) {
            horarioAtencion.mostrarHorario();
        } else {
            System.out.println(">>> Horario de Atención: CERRADA, objeto Horario destruido/no disponible.");
        }
        // muestra la agregacion de libros y autores
        System.out.println("\n>>> Libros agregados (" + librosDisponibles.size() + ")");
        librosDisponibles.forEach(Libro::leer);

        System.out.println("\n>>> Autores agregados (" + autoresRegistrados.size() + ")");
        autoresRegistrados.forEach(Autor::mostrarInfo);

        // mostrar prestamos activos
        System.out.println("\n>>> Préstamos activos (" + prestamosActivos.size() + ")");
        prestamosActivos.forEach(Prestamo::mostrarInfo);
         System.out.println("\n*********************************************************************");
    }

    public void cerrarBiblioteca(){
        System.out.println("");
        System.out.println("La biblioteca " + nombre + " está cerrando. ¡Hasta luego!");
        // composicion: el horario deja de existir al cerrar la biblioteca
        horarioAtencion = null;
        System.out.println("El objeto Horario ha sido destruido.");

        // los prestamos activos dejan de existir (se borra la lista)
        this.prestamosActivos.clear();
        System.out.println("La lista de préstanmos activos ha sido vaciada.");
        System.out.println("Los objetos Libro y Autor permanecen en el sistem (no son eliminados).");
        System.out.println("\n---------------BIBLIOTECA CERRADA---------------------");
    }

}
