package vista.panels;

import modelo.Usuario;
import modelo.ServicioUsuarios;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditarUsuarioDialog extends JDialog {
    private JTextField nombreField;
    private JTextField apellidoField;
    private JTextField telUsuarioField;
    private JTextField telEmergencia1Field;
    private JTextField telEmergencia2Field;
    private JButton guardarButton;
    private Usuario usuario;
    private ServicioUsuarios servicioUsuario;
    private UsuariosPanel usuariosPanel;

    public EditarUsuarioDialog(Usuario usuario, ServicioUsuarios servicioUsuario, UsuariosPanel usuariosPanel) {
        this.usuario = usuario;
        this.servicioUsuario = servicioUsuario;
        this.usuariosPanel = usuariosPanel;

        setTitle("Editar Usuario");
        setSize(400, 300);
        setLayout(new GridLayout(6, 2, 10, 10));
        setLocationRelativeTo(null);

        // Crear campos de entrada
        add(new JLabel("Nombre:"));
        nombreField = new JTextField(usuario.getNombre());
        add(nombreField);

        add(new JLabel("Apellido:"));
        apellidoField = new JTextField(usuario.getApellido());
        add(apellidoField);

        add(new JLabel("Teléfono:"));
        telUsuarioField = new JTextField(usuario.getTelUsuario());
        add(telUsuarioField);

        add(new JLabel("Tel. Emergencia 1:"));
        telEmergencia1Field = new JTextField(usuario.getTelEmergencia1());
        add(telEmergencia1Field);

        add(new JLabel("Tel. Emergencia 2:"));
        telEmergencia2Field = new JTextField(usuario.getTelEmergencia2());
        add(telEmergencia2Field);

        guardarButton = new JButton("Guardar");
        guardarButton.addActionListener(new GuardarAction());
        add(guardarButton);

        setVisible(true);
    }

    private class GuardarAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            usuario.setNombre(nombreField.getText());
            usuario.setApellido(apellidoField.getText());
            usuario.setTelUsuario(telUsuarioField.getText());
            usuario.setTelEmergencia1(telEmergencia1Field.getText());
            usuario.setTelEmergencia2(telEmergencia2Field.getText());

            if (servicioUsuario.actualizarUsuario(usuario)) {
                JOptionPane.showMessageDialog(EditarUsuarioDialog.this, "Usuario actualizado exitosamente.");
                usuariosPanel.recargarTabla(); // Refrescar la tabla
                dispose();
            } else {
                JOptionPane.showMessageDialog(EditarUsuarioDialog.this, "Error al actualizar el usuario.");
            }
        }
    }
}
