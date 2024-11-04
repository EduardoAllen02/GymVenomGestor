package vista.panels;

import modelo.Usuario;
import modelo.ServicioUsuarios;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import vista.ButtonColumn;
public class UsuariosPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private ServicioUsuarios servicioUsuario;

    public UsuariosPanel() {
        servicioUsuario = new ServicioUsuarios();
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        // Configuración del título
        JLabel titleLabel = new JLabel("Gestión de Usuarios", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, BorderLayout.NORTH);

        // Configuración de la tabla
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Solo las columnas de "Eliminar" y "Editar" son editables (botones)
                return column == 7 || column == 8;
            }
        };
        tableModel.setColumnIdentifiers(new Object[]{"ID", "Nombre", "Apellido", "Teléfono", "Tel. Emergencia 1", "Tel. Emergencia 2", "Edad", "Editar", "Eliminar"});
        table = new JTable(tableModel);
        cargarDatosUsuarios();

        // Configurar botón de "Eliminar" en cada fila
        Action delete = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = Integer.parseInt(e.getActionCommand());
                int idUsuario = (int) tableModel.getValueAt(row, 0);

                int confirm = JOptionPane.showConfirmDialog(null, "¿Seguro que deseas eliminar este usuario?");
                if (confirm == JOptionPane.YES_OPTION) {
                    if (servicioUsuario.eliminarUsuario(idUsuario)) {
                        cargarDatosUsuarios(); // Recargar la tabla después de eliminar
                        JOptionPane.showMessageDialog(null, "Usuario eliminado exitosamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al eliminar el usuario.");
                    }
                }
            }
        };
        
        // Configurar botón de "Editar" en cada fila
        Action edit = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = Integer.parseInt(e.getActionCommand());
                int idUsuario = (int) tableModel.getValueAt(row, 0);
                Usuario usuario = servicioUsuario.obtenerUsuarioPorId(idUsuario);
                
                if (usuario != null) {
                    new EditarUsuarioDialog(usuario, servicioUsuario, UsuariosPanel.this);
                }
            }
        };
        
        new ButtonColumn(table, edit, 7); // Columna "Editar"
        new ButtonColumn(table, delete, 8); // Columna "Eliminar"
        
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Botón de "Registrar Usuario" al final del panel
        JButton addButton = new JButton("Registrar Nuevo Usuario");
        addButton.setFont(new Font("Roboto", Font.PLAIN, 16));
        addButton.setBackground(new Color(34, 139, 34));
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(e -> {
        new RegistrarUsuarioDialog((Frame) SwingUtilities.getWindowAncestor(this), servicioUsuario);
        cargarDatosUsuarios(); // Recargar la tabla después de registrar un nuevo usuario
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(addButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

private void cargarDatosUsuarios() {
    List<Usuario> usuarios = servicioUsuario.obtenerUsuarios();
    tableModel.setRowCount(0); // Limpiar filas existentes

    for (Usuario usuario : usuarios) {
        int edad = calcularEdad(usuario.getFechaNacimiento()); // Calcular la edad
        tableModel.addRow(new Object[]{
            usuario.getId(),
            usuario.getNombre(),
            usuario.getApellido(),
            usuario.getTelUsuario(),
            usuario.getTelEmergencia1(),
            usuario.getTelEmergencia2(),
            edad,
            "Editar",    // Botón de editar
            "Eliminar"   // Botón de eliminar
        });
    }
}


    private int calcularEdad(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) return 0;
        LocalDate fechaActual = LocalDate.now();
        return Period.between(fechaNacimiento, fechaActual).getYears();
    }

    // Método para recargar la tabla (útil después de guardar cambios en el diálogo de edición)
    public void recargarTabla() {
        cargarDatosUsuarios();
    }
}
