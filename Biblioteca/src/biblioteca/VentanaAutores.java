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

public class VentanaAutores extends javax.swing.JFrame {

    private List<Autor> autores;
    private GestorPersistencia gestorPersistencia;
    private JTable tablaAutores;
    private DefaultTableModel modeloTabla;
    
    public VentanaAutores(List<Autor> autores, GestorPersistencia gestor) {
        this.autores = autores;
        this.gestorPersistencia = gestor;
        inicializarComponentes();
        cargarAutoresEnTabla();
    }
    
        private void inicializarComponentes() {
        setTitle("Gestión de Autores");
        setSize(700, 500);
        setLocationRelativeTo(null);
        
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel superior - Título
        JLabel lblTitulo = new JLabel("✍️ GESTIÓN DE AUTORES");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(34, 139, 34));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        // Panel central - Tabla
        String[] columnas = {"Nombre", "Nacionalidad"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaAutores = new JTable(modeloTabla);
        tablaAutores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaAutores.setRowHeight(25);
        tablaAutores.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        JScrollPane scrollPane = new JScrollPane(tablaAutores);
        
        // Panel inferior - Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton btnAgregar = new JButton("➕ Agregar Autor");
        btnAgregar.setBackground(new Color(34, 139, 34));
        btnAgregar.setForeground(Color.BLACK);
        btnAgregar.setFocusPainted(false);
        btnAgregar.addActionListener(e -> agregarAutor());
        
        JButton btnEliminar = new JButton("🗑️ Eliminar Autor");
        btnEliminar.setBackground(new Color(220, 20, 60));
        btnEliminar.setForeground(Color.BLACK);
        btnEliminar.setFocusPainted(false);
        btnEliminar.addActionListener(e -> eliminarAutor());
        
        JButton btnEditar = new JButton("✏️ Editar Autor");
        btnEditar.setBackground(new Color(255, 140, 0));
        btnEditar.setForeground(Color.BLACK);
        btnEditar.setFocusPainted(false);
        btnEditar.addActionListener(e -> editarAutor());
        
        JButton btnCerrar = new JButton("❌ Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnCerrar);
        
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }
        
    
    private void cargarAutoresEnTabla() {
        modeloTabla.setRowCount(0);
        for (Autor autor : autores) {
            Object[] fila = {
                autor.getNombre(),
                autor.getNacionalidad()
            };
            modeloTabla.addRow(fila);
        }
    }
    
    private void agregarAutor() {
        JDialog dialogo = new JDialog(this, "Agregar Nuevo Autor", true);
        dialogo.setSize(400, 200);
        dialogo.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JTextField txtNombre = new JTextField(20);
        JTextField txtNacionalidad = new JTextField(20);
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        panel.add(txtNombre, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Nacionalidad:"), gbc);
        gbc.gridx = 1;
        panel.add(txtNacionalidad, gbc);
        
        JPanel panelBotones = new JPanel();
        JButton btnGuardar = new JButton("💾 Guardar");
        JButton btnCancelar = new JButton("❌ Cancelar");
        
        btnGuardar.addActionListener(e -> {
            if (txtNombre.getText().trim().isEmpty() || txtNacionalidad.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialogo, "Complete todos los campos", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Autor nuevoAutor = new Autor(txtNombre.getText().trim(), 
                txtNacionalidad.getText().trim());
            autores.add(nuevoAutor);
            gestorPersistencia.guardarAutores(autores);
            cargarAutoresEnTabla();
            dialogo.dispose();
            JOptionPane.showMessageDialog(this, "Autor agregado exitosamente", 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        });
        
        btnCancelar.addActionListener(e -> dialogo.dispose());
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        dialogo.add(panel, BorderLayout.CENTER);
        dialogo.add(panelBotones, BorderLayout.SOUTH);
        dialogo.setVisible(true);
    }

    
     private void editarAutor() {
        int filaSeleccionada = tablaAutores.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un autor para editar", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Autor autor = autores.get(filaSeleccionada);
        
        JDialog dialogo = new JDialog(this, "Editar Autor", true);
        dialogo.setSize(400, 200);
        dialogo.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JTextField txtNombre = new JTextField(autor.getNombre(), 20);
        JTextField txtNacionalidad = new JTextField(autor.getNacionalidad(), 20);
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        panel.add(txtNombre, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Nacionalidad:"), gbc);
        gbc.gridx = 1;
        panel.add(txtNacionalidad, gbc);
        
        JPanel panelBotones = new JPanel();
        JButton btnGuardar = new JButton("💾 Guardar");
        JButton btnCancelar = new JButton("❌ Cancelar");
        
        btnGuardar.addActionListener(e -> {
            if (txtNombre.getText().trim().isEmpty() || txtNacionalidad.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialogo, "Complete todos los campos", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            autor.setNombre(txtNombre.getText().trim());
            autor.setNacionalidad(txtNacionalidad.getText().trim());
            gestorPersistencia.guardarAutores(autores);
            cargarAutoresEnTabla();
            dialogo.dispose();
            JOptionPane.showMessageDialog(this, "Autor editado exitosamente", 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        });
        
        btnCancelar.addActionListener(e -> dialogo.dispose());
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        dialogo.add(panel, BorderLayout.CENTER);
        dialogo.add(panelBotones, BorderLayout.SOUTH);
        dialogo.setVisible(true);
    }
    
    private void eliminarAutor() {
        int filaSeleccionada = tablaAutores.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un autor para eliminar", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar este autor?", 
            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            autores.remove(filaSeleccionada);
            gestorPersistencia.guardarAutores(autores);
            cargarAutoresEnTabla();
            JOptionPane.showMessageDialog(this, "Autor eliminado exitosamente", 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
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
