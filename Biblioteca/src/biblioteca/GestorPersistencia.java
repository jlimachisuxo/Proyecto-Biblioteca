/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biblioteca;

/**
 *
 * @author Joshelyn
 */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestorPersistencia {
    private static final String ARCHIVO_LIBROS = "libros.json";
    private static final String ARCHIVO_AUTORES = "autores.json";
    private static final String ARCHIVO_ESTUDIANTES = "estudiantes.json";
    private static final String ARCHIVO_PRESTAMOS = "prestamos.json";
    
    private Gson gson;
    
    public GestorPersistencia() {
        // Registrar el adaptador para LocalDate
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
    }
    
    // Métodos para Libros
    public void guardarLibros(List<Libro> libros) {
        try (Writer writer = new FileWriter(ARCHIVO_LIBROS)) {
            gson.toJson(libros, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar libros: " + e.getMessage());
        }
    }
    
    public List<Libro> cargarLibros() {
        File archivo = new File(ARCHIVO_LIBROS);
        if (!archivo.exists()) {
            return new ArrayList<>();
        }
        
        try (Reader reader = new FileReader(ARCHIVO_LIBROS)) {
            Type listType = new TypeToken<ArrayList<Libro>>(){}.getType();
            List<Libro> libros = gson.fromJson(reader, listType);
            return libros != null ? libros : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error al cargar libros: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    // Métodos para Autores
    public void guardarAutores(List<Autor> autores) {
        try (Writer writer = new FileWriter(ARCHIVO_AUTORES)) {
            gson.toJson(autores, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar autores: " + e.getMessage());
        }
    }
    
    public List<Autor> cargarAutores() {
        File archivo = new File(ARCHIVO_AUTORES);
        if (!archivo.exists()) {
            return new ArrayList<>();
        }
        
        try (Reader reader = new FileReader(ARCHIVO_AUTORES)) {
            Type listType = new TypeToken<ArrayList<Autor>>(){}.getType();
            List<Autor> autores = gson.fromJson(reader, listType);
            return autores != null ? autores : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error al cargar autores: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    // Métodos para Estudiantes
    public void guardarEstudiantes(List<Estudiante> estudiantes) {
        try (Writer writer = new FileWriter(ARCHIVO_ESTUDIANTES)) {
            gson.toJson(estudiantes, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar estudiantes: " + e.getMessage());
        }
    }
    
    public List<Estudiante> cargarEstudiantes() {
        File archivo = new File(ARCHIVO_ESTUDIANTES);
        if (!archivo.exists()) {
            return new ArrayList<>();
        }
        
        try (Reader reader = new FileReader(ARCHIVO_ESTUDIANTES)) {
            Type listType = new TypeToken<ArrayList<Estudiante>>(){}.getType();
            List<Estudiante> estudiantes = gson.fromJson(reader, listType);
            return estudiantes != null ? estudiantes : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error al cargar estudiantes: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    // Métodos para Préstamos
    public void guardarPrestamos(List<Prestamo> prestamos) {
        try (Writer writer = new FileWriter(ARCHIVO_PRESTAMOS)) {
            gson.toJson(prestamos, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar préstamos: " + e.getMessage());
        }
    }
    
    public List<Prestamo> cargarPrestamos() {
        File archivo = new File(ARCHIVO_PRESTAMOS);
        if (!archivo.exists()) {
            return new ArrayList<>();
        }
        
        try (Reader reader = new FileReader(ARCHIVO_PRESTAMOS)) {
            Type listType = new TypeToken<ArrayList<Prestamo>>(){}.getType();
            List<Prestamo> prestamos = gson.fromJson(reader, listType);
            return prestamos != null ? prestamos : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error al cargar préstamos: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}