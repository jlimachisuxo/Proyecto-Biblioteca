/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package biblioteca;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaPrincipal extends javax.swing.JFrame {
    private List<Libro> libros;
    private List<Autor> autores;
    private List<Estudiante> estudiantes;
    private List<Prestamo> prestamos;
    private GestorPersistencia gestorPersistencia;
    
    public VentanaPrincipal() {
        gestorPersistencia = new GestorPersistencia();
        cargarDatos();
        inicializarComponentes();
    }
    
    private void cargarDatos() {
        libros = gestorPersistencia.cargarLibros();
        autores = gestorPersistencia.cargarAutores();
        estudiantes = gestorPersistencia.cargarEstudiantes();
        prestamos = gestorPersistencia.cargarPrestamos();
    }
    
    private void inicializarComponentes() {
        setTitle("Sistema de Gestión de Biblioteca - UMSA");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Panel principal con imagen de fondo
        JPanel panelPrincipal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(240, 248, 255));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        
        // Panel de título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setOpaque(false);
        JLabel lblTitulo = new JLabel("SISTEMA DE BIBLIOTECA");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(25, 25, 112));
        panelTitulo.add(lblTitulo);
        
        JLabel lblSubtitulo = new JLabel("Universidad Mayor de San Andrés");
        lblSubtitulo.setFont(new Font("Arial", Font.ITALIC, 16));
        lblSubtitulo.setForeground(new Color(70, 130, 180));
        
        JPanel panelEncabezado = new JPanel();
        panelEncabezado.setLayout(new BoxLayout(panelEncabezado, BoxLayout.Y_AXIS));
        panelEncabezado.setOpaque(false);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelEncabezado.add(Box.createVerticalStrut(20));
        panelEncabezado.add(lblTitulo);
        panelEncabezado.add(Box.createVerticalStrut(5));
        panelEncabezado.add(lblSubtitulo);
        
        // Panel central con botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(4, 1, 15, 15));
        panelBotones.setOpaque(false);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));
        
        // Crear botones con estilo
        JButton btnBiblioteca = crearBoton("BIBLIOTECA", new Color(65, 105, 225));
        JButton btnAutores = crearBoton("AUTORES", new Color(34, 139, 34));
        JButton btnEstudiantes = crearBoton("ESTUDIANTES", new Color(220, 20, 60));
        JButton btnPrestamos = crearBoton("PRÉSTAMOS", new Color(255, 140, 0));
        
        // Agregar acciones a los botones
        btnBiblioteca.addActionListener(e -> abrirVentanaLibros());
        btnAutores.addActionListener(e -> abrirVentanaAutores());
        btnEstudiantes.addActionListener(e -> abrirVentanaEstudiantes());
        btnPrestamos.addActionListener(e -> abrirVentanaPrestamos());
        
        panelBotones.add(btnBiblioteca);
        panelBotones.add(btnAutores);
        panelBotones.add(btnEstudiantes);
        panelBotones.add(btnPrestamos);
        
        // Panel inferior
        JPanel panelInferior = new JPanel();
        panelInferior.setOpaque(false);
        JLabel lblFooter = new JLabel("© 2025 Sistema de Gestión Bibliotecaria");
        lblFooter.setFont(new Font("Arial", Font.PLAIN, 11));
        lblFooter.setForeground(Color.GRAY);
        panelInferior.add(lblFooter);
        
        panelPrincipal.add(panelEncabezado, BorderLayout.NORTH);
        panelPrincipal.add(panelBotones, BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }
    
    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Efecto hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(color);
            }
        });
        
        return boton;
    }
    
    private void abrirVentanaLibros() {
        VentanaLibros ventana = new VentanaLibros(libros, autores, gestorPersistencia);
        ventana.setVisible(true);
    }
    
    private void abrirVentanaAutores() {
        VentanaAutores ventana = new VentanaAutores(autores, gestorPersistencia);
        ventana.setVisible(true);
    }
    
    private void abrirVentanaEstudiantes() {
        VentanaEstudiantes ventana = new VentanaEstudiantes(estudiantes, gestorPersistencia);
        ventana.setVisible(true);
    }
    
    private void abrirVentanaPrestamos() {
        VentanaPrestamos ventana = new VentanaPrestamos(prestamos, libros, estudiantes, gestorPersistencia);
        ventana.setVisible(true);
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
