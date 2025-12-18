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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VentanaLibros extends javax.swing.JFrame {

     private List<Libro> libros;
    private List<Autor> autores;
    private GestorPersistencia gestorPersistencia;
    private JTable tablaLibros;
    private DefaultTableModel modeloTabla;
    private JTextField txtBuscar;
    
    public VentanaLibros(List<Libro> libros, List<Autor> autores, GestorPersistencia gestor) {
        this.libros = libros;
        this.autores = autores;
        this.gestorPersistencia = gestor;
        inicializarComponentes();
        cargarLibrosEnTabla();
    }
    
    private void inicializarComponentes() {
        setTitle("Gestión de Libros");
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel superior - Título y búsqueda
        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));
        
        JLabel lblTitulo = new JLabel("📚 GESTIÓN DE LIBROS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(65, 105, 225));
        
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblBuscar = new JLabel("Buscar:");
        txtBuscar = new JTextField(20);
        JButton btnBuscar = new JButton("🔍 Buscar");
        btnBuscar.addActionListener(e -> buscarLibro());
        JButton btnMostrarTodos = new JButton("Mostrar Todos");
        btnMostrarTodos.addActionListener(e -> cargarLibrosEnTabla());
        
        panelBusqueda.add(lblBuscar);
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);
        panelBusqueda.add(btnMostrarTodos);
        
        panelSuperior.add(lblTitulo, BorderLayout.NORTH);
        panelSuperior.add(panelBusqueda, BorderLayout.CENTER);
        
        // Panel central - Tabla
        String[] columnas = {"Título", "ISBN", "Número de Páginas"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaLibros = new JTable(modeloTabla);
        tablaLibros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaLibros.setRowHeight(25);
        tablaLibros.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        JScrollPane scrollPane = new JScrollPane(tablaLibros);
        
        // Panel inferior - Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton btnAgregar = new JButton("➕ Agregar Libro");
        btnAgregar.setBackground(new Color(34, 139, 34));
        btnAgregar.setForeground(Color.BLACK);
        btnAgregar.setFocusPainted(false);
        btnAgregar.addActionListener(e -> agregarLibro());
        
        JButton btnEliminar = new JButton("🗑️ Eliminar Libro");
        btnEliminar.setBackground(new Color(220, 20, 60));
        btnEliminar.setForeground(Color.BLACK);
        btnEliminar.setFocusPainted(false);
        btnEliminar.addActionListener(e -> eliminarLibro());
        
        JButton btnVerDetalle = new JButton("👁️ Ver Detalle");
        btnVerDetalle.setBackground(new Color(70, 130, 180));
        btnVerDetalle.setForeground(Color.BLACK);
        btnVerDetalle.setFocusPainted(false);
        btnVerDetalle.addActionListener(e -> verDetalle());
        
        JButton btnCerrar = new JButton("❌ Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVerDetalle);
        panelBotones.add(btnCerrar);
        
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }
    
    private void cargarLibrosEnTabla() {
        modeloTabla.setRowCount(0);
        for (Libro libro : libros) {
            Object[] fila = {
                libro.getTitulo(),
                libro.getISBN(),
                libro.getNumeroPaginas()
            };
            modeloTabla.addRow(fila);
        }
    }
    
    private void buscarLibro() {
        String busqueda = txtBuscar.getText().toLowerCase().trim();
        if (busqueda.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un texto para buscar", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        modeloTabla.setRowCount(0);
        boolean encontrado = false;
        
        for (Libro libro : libros) {
            if (libro.getTitulo().toLowerCase().contains(busqueda) || 
                libro.getISBN().toLowerCase().contains(busqueda)) {
                Object[] fila = {
                    libro.getTitulo(),
                    libro.getISBN(),
                    libro.getNumeroPaginas()
                };
                modeloTabla.addRow(fila);
                encontrado = true;
            }
        }
        
        if (!encontrado) {
            JOptionPane.showMessageDialog(this, "No se encontraron libros con ese criterio", 
                "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void agregarLibro() {
        JDialog dialogo = new JDialog(this, "Agregar Nuevo Libro", true);
        dialogo.setSize(500, 400);
        dialogo.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JTextField txtTitulo = new JTextField(20);
        JTextField txtISBN = new JTextField(20);
        JSpinner spinnerPaginas = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
        JTextArea txtContenido = new JTextArea(8, 20);
        txtContenido.setLineWrap(true);
        txtContenido.setWrapStyleWord(true);
        JScrollPane scrollContenido = new JScrollPane(txtContenido);
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Título:"), gbc);
        gbc.gridx = 1;
        panel.add(txtTitulo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1;
        panel.add(txtISBN, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Núm. Páginas:"), gbc);
        gbc.gridx = 1;
        panel.add(spinnerPaginas, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Contenido:"), gbc);
        gbc.gridx = 1;
        gbc.gridheight = 2;
        panel.add(scrollContenido, gbc);
        
        JPanel panelBotones = new JPanel();
        JButton btnGuardar = new JButton("💾 Guardar");
        JButton btnCancelar = new JButton("❌ Cancelar");
        
        btnGuardar.addActionListener(e -> {
            if (txtTitulo.getText().trim().isEmpty() || txtISBN.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialogo, "Complete todos los campos obligatorios", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int numPaginas = (int) spinnerPaginas.getValue();
            List<String> contenidoPaginas = new ArrayList<>();
            String[] lineas = txtContenido.getText().split("\n");
            
            for (int i = 0; i < numPaginas; i++) {
                if (i < lineas.length && !lineas[i].trim().isEmpty()) {
                    contenidoPaginas.add(lineas[i].trim());
                } else {
                    contenidoPaginas.add("Contenido de la página " + (i + 1));
                }
            }
            
            Libro nuevoLibro = new Libro(txtTitulo.getText().trim(), 
                txtISBN.getText().trim(), contenidoPaginas);
            libros.add(nuevoLibro);
            gestorPersistencia.guardarLibros(libros);
            cargarLibrosEnTabla();
            dialogo.dispose();
            JOptionPane.showMessageDialog(this, "Libro agregado exitosamente", 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        });
        
        btnCancelar.addActionListener(e -> dialogo.dispose());
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        dialogo.add(panel, BorderLayout.CENTER);
        dialogo.add(panelBotones, BorderLayout.SOUTH);
        dialogo.setVisible(true);
    }
    
    private void eliminarLibro() {
        int filaSeleccionada = tablaLibros.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un libro para eliminar", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar este libro?", 
            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            libros.remove(filaSeleccionada);
            gestorPersistencia.guardarLibros(libros);
            cargarLibrosEnTabla();
            JOptionPane.showMessageDialog(this, "Libro eliminado exitosamente", 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void verDetalle() {
        int filaSeleccionada = tablaLibros.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un libro para ver detalles", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Libro libro = libros.get(filaSeleccionada);
        StringBuilder detalle = new StringBuilder();
        detalle.append("Título: ").append(libro.getTitulo()).append("\n");
        detalle.append("ISBN: ").append(libro.getISBN()).append("\n");
        detalle.append("Número de Páginas: ").append(libro.getNumeroPaginas()).append("\n\n");
        detalle.append("Contenido de las páginas:\n");
        detalle.append(libro.obtenerContenidoCompleto());
        
        JTextArea areaTexto = new JTextArea(detalle.toString());
        areaTexto.setEditable(false);
        areaTexto.setLineWrap(true);
        areaTexto.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(areaTexto);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        
        JOptionPane.showMessageDialog(this, scrollPane, 
            "Detalle del Libro", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
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
