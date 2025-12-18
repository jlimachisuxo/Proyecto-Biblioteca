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

public class VentanaEstudiantes extends javax.swing.JFrame {

    private List<Estudiante> estudiantes;
    private GestorPersistencia gestorPersistencia;
    private JTable tablaEstudiantes;
    private DefaultTableModel modeloTabla;
    
    public VentanaEstudiantes(List<Estudiante> estudiantes, GestorPersistencia gestor) {
        this.estudiantes = estudiantes;
        this.gestorPersistencia = gestor;
        inicializarComponentes();
        cargarEstudiantesEnTabla();
    }
    
    private void inicializarComponentes() {
        setTitle("Gestión de Estudiantes");
        setSize(700, 500);
        setLocationRelativeTo(null);
        
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel superior - Título
        JLabel lblTitulo = new JLabel("👨‍🎓 GESTIÓN DE ESTUDIANTES");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(220, 20, 60));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        // Panel central - Tabla
        String[] columnas = {"Código", "Nombre"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaEstudiantes = new JTable(modeloTabla);
        tablaEstudiantes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaEstudiantes.setRowHeight(25);
        tablaEstudiantes.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        JScrollPane scrollPane = new JScrollPane(tablaEstudiantes);
        
        // Panel inferior - Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton btnAgregar = new JButton("➕ Agregar Estudiante");
        btnAgregar.setBackground(new Color(34, 139, 34));
        btnAgregar.setForeground(Color.BLACK);
        btnAgregar.setFocusPainted(false);
        btnAgregar.addActionListener(e -> agregarEstudiante());
        
        JButton btnEliminar = new JButton("🗑️ Eliminar Estudiante");
        btnEliminar.setBackground(new Color(220, 20, 60));
        btnEliminar.setForeground(Color.BLACK);
        btnEliminar.setFocusPainted(false);
        btnEliminar.addActionListener(e -> eliminarEstudiante());
        
        JButton btnEditar = new JButton("✏️ Editar Estudiante");
        btnEditar.setBackground(new Color(255, 140, 0));
        btnEditar.setForeground(Color.BLACK);
        btnEditar.setFocusPainted(false);
        btnEditar.addActionListener(e -> editarEstudiante());
        
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
    
    private void cargarEstudiantesEnTabla() {
        modeloTabla.setRowCount(0);
        for (Estudiante estudiante : estudiantes) {
            Object[] fila = {
                estudiante.getCodigoEstudiante(),
                estudiante.getNombre()
            };
            modeloTabla.addRow(fila);
        }
    }
    
    private void agregarEstudiante() {
        JDialog dialogo = new JDialog(this, "Agregar Nuevo Estudiante", true);
        dialogo.setSize(400, 200);
        dialogo.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JTextField txtCodigo = new JTextField(20);
        JTextField txtNombre = new JTextField(20);
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Código:"), gbc);
        gbc.gridx = 1;
        panel.add(txtCodigo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        panel.add(txtNombre, gbc);
        
        JPanel panelBotones = new JPanel();
        JButton btnGuardar = new JButton("💾 Guardar");
        JButton btnCancelar = new JButton("❌ Cancelar");
        
        btnGuardar.addActionListener(e -> {
            if (txtCodigo.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialogo, "Complete todos los campos", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validar que el código no exista
            String codigo = txtCodigo.getText().trim();
            for (Estudiante est : estudiantes) {
                if (est.getCodigoEstudiante().equals(codigo)) {
                    JOptionPane.showMessageDialog(dialogo, 
                        "Ya existe un estudiante con ese código", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            Estudiante nuevoEstudiante = new Estudiante(codigo, txtNombre.getText().trim());
            estudiantes.add(nuevoEstudiante);
            gestorPersistencia.guardarEstudiantes(estudiantes);
            cargarEstudiantesEnTabla();
            dialogo.dispose();
            JOptionPane.showMessageDialog(this, "Estudiante agregado exitosamente", 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        });
        
        btnCancelar.addActionListener(e -> dialogo.dispose());
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        dialogo.add(panel, BorderLayout.CENTER);
        dialogo.add(panelBotones, BorderLayout.SOUTH);
        dialogo.setVisible(true);
    }
    
    private void editarEstudiante() {
        int filaSeleccionada = tablaEstudiantes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un estudiante para editar", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Estudiante estudiante = estudiantes.get(filaSeleccionada);
        
        JDialog dialogo = new JDialog(this, "Editar Estudiante", true);
        dialogo.setSize(400, 200);
        dialogo.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JTextField txtCodigo = new JTextField(estudiante.getCodigoEstudiante(), 20);
        txtCodigo.setEnabled(false); // No permitir cambiar el código
        JTextField txtNombre = new JTextField(estudiante.getNombre(), 20);
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Código:"), gbc);
        gbc.gridx = 1;
        panel.add(txtCodigo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        panel.add(txtNombre, gbc);
        
        JPanel panelBotones = new JPanel();
        JButton btnGuardar = new JButton("💾 Guardar");
        JButton btnCancelar = new JButton("❌ Cancelar");
        
        btnGuardar.addActionListener(e -> {
            if (txtNombre.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialogo, "El nombre no puede estar vacío", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            estudiante.setNombre(txtNombre.getText().trim());
            gestorPersistencia.guardarEstudiantes(estudiantes);
            cargarEstudiantesEnTabla();
            dialogo.dispose();
            JOptionPane.showMessageDialog(this, "Estudiante editado exitosamente", 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        });
        
        btnCancelar.addActionListener(e -> dialogo.dispose());
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        dialogo.add(panel, BorderLayout.CENTER);
        dialogo.add(panelBotones, BorderLayout.SOUTH);
        dialogo.setVisible(true);
    }
    
    private void eliminarEstudiante() {
        int filaSeleccionada = tablaEstudiantes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un estudiante para eliminar", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar este estudiante?", 
            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            estudiantes.remove(filaSeleccionada);
            gestorPersistencia.guardarEstudiantes(estudiantes);
            cargarEstudiantesEnTabla();
            JOptionPane.showMessageDialog(this, "Estudiante eliminado exitosamente", 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
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
