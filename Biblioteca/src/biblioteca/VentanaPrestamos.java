/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package biblioteca;

/**
 *
 * @author Joshelyn
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaPrestamos extends javax.swing.JFrame {

     private List<Prestamo> prestamos;
    private List<Libro> libros;
    private List<Estudiante> estudiantes;
    private GestorPersistencia gestorPersistencia;
    private JTable tablaPrestamos;
    private DefaultTableModel modeloTabla;
    
    public VentanaPrestamos(List<Prestamo> prestamos, List<Libro> libros, 
                           List<Estudiante> estudiantes, GestorPersistencia gestor) {
        this.prestamos = prestamos;
        this.libros = libros;
        this.estudiantes = estudiantes;
        this.gestorPersistencia = gestor;
        inicializarComponentes();
        cargarPrestamosEnTabla();
    }
    
    private void inicializarComponentes() {
        setTitle("Gestión de Préstamos");
        setSize(900, 600);
        setLocationRelativeTo(null);
        
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel superior - Título
        JLabel lblTitulo = new JLabel("📋 GESTIÓN DE PRÉSTAMOS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(255, 140, 0));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        // Panel central - Tabla
        String[] columnas = {"Estudiante", "Código", "Libro", "Fecha Préstamo", "Fecha Devolución"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaPrestamos = new JTable(modeloTabla);
        tablaPrestamos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaPrestamos.setRowHeight(25);
        tablaPrestamos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        JScrollPane scrollPane = new JScrollPane(tablaPrestamos);
        
        // Panel inferior - Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton btnNuevoPrestamo = new JButton("➕ Nuevo Préstamo");
        btnNuevoPrestamo.setBackground(new Color(34, 139, 34));
        btnNuevoPrestamo.setForeground(Color.BLACK);
        btnNuevoPrestamo.setFocusPainted(false);
        btnNuevoPrestamo.addActionListener(e -> nuevoPrestamo());
        
        JButton btnDevolver = new JButton("↩️ Devolver Libro");
        btnDevolver.setBackground(new Color(70, 130, 180));
        btnDevolver.setForeground(Color.BLACK);
        btnDevolver.setFocusPainted(false);
        btnDevolver.addActionListener(e -> devolverLibro());
        
        JButton btnVerDetalle = new JButton("👁️ Ver Detalle");
        btnVerDetalle.setBackground(new Color(138, 43, 226));
        btnVerDetalle.setForeground(Color.BLACK);
        btnVerDetalle.setFocusPainted(false);
        btnVerDetalle.addActionListener(e -> verDetalle());
        
        JButton btnCerrar = new JButton("❌ Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        
        panelBotones.add(btnNuevoPrestamo);
        panelBotones.add(btnDevolver);
        panelBotones.add(btnVerDetalle);
        panelBotones.add(btnCerrar);
        
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }
    
    private void cargarPrestamosEnTabla() {
        modeloTabla.setRowCount(0);
        for (Prestamo prestamo : prestamos) {
            Object[] fila = {
                prestamo.getEstudiante().getNombre(),
                prestamo.getEstudiante().getCodigoEstudiante(),
                prestamo.getLibro().getTitulo(),
                prestamo.getFechaPrestamo(),
                prestamo.getFechaDevolucion()
            };
            modeloTabla.addRow(fila);
        }
    }
    
    private void nuevoPrestamo() {
        if (libros.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No hay libros disponibles en el sistema", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (estudiantes.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No hay estudiantes registrados en el sistema", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        JDialog dialogo = new JDialog(this, "Registrar Nuevo Préstamo", true);
        dialogo.setSize(500, 300);
        dialogo.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // ComboBox de Estudiantes
        JComboBox<String> comboEstudiantes = new JComboBox<>();
        for (Estudiante est : estudiantes) {
            comboEstudiantes.addItem(est.getCodigoEstudiante() + " - " + est.getNombre());
        }
        
        // ComboBox de Libros
        JComboBox<String> comboLibros = new JComboBox<>();
        for (Libro libro : libros) {
            comboLibros.addItem(libro.getTitulo() + " (ISBN: " + libro.getISBN() + ")");
        }
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Estudiante:"), gbc);
        gbc.gridx = 1;
        panel.add(comboEstudiantes, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Libro:"), gbc);
        gbc.gridx = 1;
        panel.add(comboLibros, gbc);
        
        // Información adicional
        JLabel lblInfo = new JLabel("<html><i>El préstamo tendrá una duración de 7 días</i></html>");
        lblInfo.setForeground(Color.GRAY);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(lblInfo, gbc);
        
        JPanel panelBotones = new JPanel();
        JButton btnGuardar = new JButton("💾 Registrar Préstamo");
        JButton btnCancelar = new JButton("❌ Cancelar");
        
        btnGuardar.addActionListener(e -> {
            int indiceEstudiante = comboEstudiantes.getSelectedIndex();
            int indiceLibro = comboLibros.getSelectedIndex();
            
            if (indiceEstudiante == -1 || indiceLibro == -1) {
                JOptionPane.showMessageDialog(dialogo, 
                    "Seleccione un estudiante y un libro", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Estudiante estudianteSeleccionado = estudiantes.get(indiceEstudiante);
            Libro libroSeleccionado = libros.get(indiceLibro);
            
            // Verificar si el estudiante ya tiene ese libro prestado
            for (Prestamo p : prestamos) {
                if (p.getEstudiante().getCodigoEstudiante()
                        .equals(estudianteSeleccionado.getCodigoEstudiante()) &&
                    p.getLibro().getISBN().equals(libroSeleccionado.getISBN())) {
                    JOptionPane.showMessageDialog(dialogo, 
                        "El estudiante ya tiene este libro prestado", 
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            
            Prestamo nuevoPrestamo = new Prestamo(estudianteSeleccionado, libroSeleccionado);
            prestamos.add(nuevoPrestamo);
            gestorPersistencia.guardarPrestamos(prestamos);
            cargarPrestamosEnTabla();
            dialogo.dispose();
            JOptionPane.showMessageDialog(this, 
                "Préstamo registrado exitosamente\n" +
                "Fecha de devolución: " + nuevoPrestamo.getFechaDevolucion(), 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        });
        
        btnCancelar.addActionListener(e -> dialogo.dispose());
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        dialogo.add(panel, BorderLayout.CENTER);
        dialogo.add(panelBotones, BorderLayout.SOUTH);
        dialogo.setVisible(true);
    }
    
    private void devolverLibro() {
        int filaSeleccionada = tablaPrestamos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, 
                "Seleccione un préstamo para devolver", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Prestamo prestamo = prestamos.get(filaSeleccionada);
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Confirmar la devolución del libro?\n\n" +
            "Libro: " + prestamo.getLibro().getTitulo() + "\n" +
            "Estudiante: " + prestamo.getEstudiante().getNombre(), 
            "Confirmar devolución", JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            prestamos.remove(filaSeleccionada);
            gestorPersistencia.guardarPrestamos(prestamos);
            cargarPrestamosEnTabla();
            JOptionPane.showMessageDialog(this, "Libro devuelto exitosamente", 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void verDetalle() {
        int filaSeleccionada = tablaPrestamos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, 
                "Seleccione un préstamo para ver detalles", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Prestamo prestamo = prestamos.get(filaSeleccionada);
        
        StringBuilder detalle = new StringBuilder();
        detalle.append("DETALLES DEL PRÉSTAMO\n\n");
        detalle.append("━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n");
        detalle.append("ESTUDIANTE:\n");
        detalle.append("  Código: ").append(prestamo.getEstudiante().getCodigoEstudiante()).append("\n");
        detalle.append("  Nombre: ").append(prestamo.getEstudiante().getNombre()).append("\n\n");
        detalle.append("LIBRO:\n");
        detalle.append("  Título: ").append(prestamo.getLibro().getTitulo()).append("\n");
        detalle.append("  ISBN: ").append(prestamo.getLibro().getISBN()).append("\n\n");
        detalle.append("FECHAS:\n");
        detalle.append("  Fecha de Préstamo: ").append(prestamo.getFechaPrestamo()).append("\n");
        detalle.append("  Fecha de Devolución: ").append(prestamo.getFechaDevolucion()).append("\n\n");
        detalle.append("━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        
        JTextArea areaTexto = new JTextArea(detalle.toString());
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(areaTexto);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        
        JOptionPane.showMessageDialog(this, scrollPane, 
            "Detalle del Préstamo", JOptionPane.INFORMATION_MESSAGE);
    }
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
