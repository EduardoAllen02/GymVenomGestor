package vista.panels;

import modelo.Usuario;
import modelo.ServicioUsuarios;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;

public class RegistrarUsuarioDialog extends JDialog {
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtTelUsuario;
    private JTextField txtTelEmergencia1;
    private JTextField txtTelEmergencia2;
    private JDatePickerImpl datePicker;
    private ServicioUsuarios servicioUsuarios;

    public RegistrarUsuarioDialog(ServicioUsuarios servicioUsuarios) {
        this.servicioUsuarios = servicioUsuarios;
        setTitle("Registrar Nuevo Usuario");
        setModal(true);
        setLayout(new GridLayout(6, 2));
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Campos de entrada
        txtNombre = new JTextField();
        txtApellido = new JTextField();
        txtTelUsuario = new JTextField();
        txtTelEmergencia1 = new JTextField();
        txtTelEmergencia2 = new JTextField();
        
        // Crear un JDatePicker para seleccionar la fecha de nacimiento
        datePicker = new JDatePickerImpl(new JDatePanelImpl(new UtilDateModel()));

        // Añadir los componentes al diálogo
        add(new JLabel("Nombre:"));
        add(txtNombre);
        add(new JLabel("Apellido:"));
        add(txtApellido);
        add(new JLabel("Teléfono:"));
        add(txtTelUsuario);
        add(new JLabel("Tel. Emergencia 1:"));
        add(txtTelEmergencia1);
        add(new JLabel("Tel. Emergencia 2:"));
        add(txtTelEmergencia2);
        add(new JLabel("Fecha de Nacimiento:"));
        add(datePicker);

        // Botón de registro
        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(this::registrarUsuario);
        add(btnRegistrar);

        // Configuración del diálogo
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void registrarUsuario(ActionEvent e) {
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String telUsuario = txtTelUsuario.getText();
        String telEmergencia1 = txtTelEmergencia1.getText();
        String telEmergencia2 = txtTelEmergencia2.getText();
        LocalDate fechaNacimiento = (LocalDate) datePicker.getModel().getValue();

        if (nombre.isEmpty() || apellido.isEmpty() || telUsuario.isEmpty() || fechaNacimiento == null) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
            return;
        }

        Usuario nuevoUsuario = new Usuario(0, nombre, apellido, telUsuario, telEmergencia1, telEmergencia2, fechaNacimiento);
        if (servicioUsuarios.agregarUsuario(nuevoUsuario)) { // Método que deberías implementar en ServicioUsuarios
            JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente.");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar el usuario.");
        }
    }
}
