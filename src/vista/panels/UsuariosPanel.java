package vista.panels;

import modelo.Usuario;
import modelo.ServicioUsuarios;
import modelo.ServicioAsistencias;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import vista.ButtonColumn;
import modelo.ServicioMembresias;

public class UsuariosPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private ServicioUsuarios servicioUsuario;
    private ServicioMembresias servicioMembresias;
    private ServicioAsistencias servicioAsistencias; // Nuevo servicio para asistencia
    private JTextField searchNombreField;
    private JComboBox<String> searchMembresiaCombo;

    public UsuariosPanel() {
        servicioUsuario = new ServicioUsuarios();
        servicioMembresias = new ServicioMembresias();
        servicioAsistencias = new ServicioAsistencias(); // Instancia del servicio para registrar asistencia

        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        // Configuración del título
        JLabel titleLabel = new JLabel("GESTION DE USUARIOS  | ", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        // Panel de filtros
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.setBackground(Color.BLACK);
        searchPanel.add(titleLabel);

        // Campo de texto para buscar por nombre
        searchNombreField = new JTextField(15);
        searchPanel.add(new JLabel("Buscar por nombre:")).setForeground(Color.WHITE);
        searchPanel.add(searchNombreField);

        searchMembresiaCombo = new JComboBox<>(new String[]{"AMBAS", "ACTIVO", "VENCIDO"});
        searchPanel.add(new JLabel("Membresía:")).setForeground(Color.WHITE);
        searchPanel.add(searchMembresiaCombo);

        searchNombreField.addActionListener(e -> cargarDatosUsuarios());
        searchMembresiaCombo.addActionListener(e -> cargarDatosUsuarios());
        add(searchPanel, BorderLayout.NORTH);


        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Solo las columnas de "Editar", "Eliminar", "Membresía" y "Asistencia" son editables
                return column == 7 || column == 8 || column == 9 || column == 10;
            }
        };
        tableModel.setColumnIdentifiers(new Object[]{"ID", "Nombre", "Apellido", "Teléfono", "Tel. Emergencia 1", "Tel. Emergencia 2", "Edad", "Editar", "Eliminar", "Membresía", "Asistencia"});
        table = new JTable(tableModel);
        cargarDatosUsuarios();

        // Configurar botón de "Membresía" en cada fila
        Action membershipAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = Integer.parseInt(e.getActionCommand());
                int idUsuario = (int) tableModel.getValueAt(row, 0);

                new EditarMembresiaDialog(idUsuario, servicioMembresias, UsuariosPanel.this);
            }
        };

        // Configurar botón de "Asistencia" en cada fila
        Action asistenciaAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = Integer.parseInt(e.getActionCommand());
                int idUsuario = (int) tableModel.getValueAt(row, 0);

                // Llamar al método para registrar asistencia
                if (servicioAsistencias.registrarAsistencia(idUsuario)) {
                    JOptionPane.showMessageDialog(null, "Asistencia registrada exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar la asistencia.");
                }
            }
        };

        // Configurar botón de "Eliminar" en cada fila
        Action delete = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = Integer.parseInt(e.getActionCommand());
                int idUsuario = (int) tableModel.getValueAt(row, 0);

                int confirm = JOptionPane.showConfirmDialog(null, "¿Seguro que deseas eliminar este usuario?");
                if (confirm == JOptionPane.YES_OPTION) {
                    if (servicioUsuario.eliminarUsuario(idUsuario)) {
                        cargarDatosUsuarios(); 
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

        new ButtonColumn(table, edit, 7); 
        new ButtonColumn(table, delete, 8);
        new ButtonColumn(table, membershipAction, 9); 
        new ButtonColumn(table, asistenciaAction, 10);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Botón de "Registrar Usuario" al final del panel
        JButton addButton = new JButton("Registrar Nuevo Usuario");
        addButton.setFont(new Font("Roboto", Font.PLAIN, 16));
        addButton.setBackground(new Color(34, 139, 34));
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(e -> {
            new RegistrarUsuarioDialog((Frame) SwingUtilities.getWindowAncestor(this), servicioUsuario);
            cargarDatosUsuarios();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(addButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void cargarDatosUsuarios() {
        String nombre = searchNombreField.getText();
        String estadoMembresia = (String) searchMembresiaCombo.getSelectedItem();

        // Inicialmente obtener todos los usuarios
        List<Usuario> usuarios = servicioUsuario.obtenerUsuarios();

        // Aplicar filtro por nombre si el campo no está vacío
        if (nombre != null && !nombre.isEmpty()) {
            usuarios = servicioUsuario.obtenerUsuariosPorNombre(nombre);
        }

        // Aplicar filtro por estado de membresía si no es "AMBAS"
        if (estadoMembresia != null && !estadoMembresia.equals("AMBAS")) {
            List<Usuario> usuariosFiltradosPorEstado = new ArrayList<>();
            for (Usuario usuario : usuarios) {
                String estadoActual = servicioUsuario.obtenerEstadoMembresia(usuario.getId());
                if (estadoMembresia.equals(estadoActual)) {
                    usuariosFiltradosPorEstado.add(usuario);
                }
            }
            usuarios = usuariosFiltradosPorEstado;
        }

        // Limpiar filas existentes en la tabla
        tableModel.setRowCount(0);

        // Cargar los usuarios filtrados en la tabla
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
                "Editar",  
                "Eliminar",  
                "Membresía",
                "✔"       
            });
        }
    }

    private int calcularEdad(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) return 0;
        LocalDate fechaActual = LocalDate.now();
        return Period.between(fechaNacimiento, fechaActual).getYears();
    }

    public void recargarTabla() {
        cargarDatosUsuarios();
    }
}
