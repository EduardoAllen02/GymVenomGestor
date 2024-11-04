
package vista.panels;
import javax.swing.*;
import java.awt.*;

public class AdministradoresPanel extends JPanel {
    public AdministradoresPanel() {
        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Gestión de Administradores", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Agregar formulario o tabla para administrar administradores
        JPanel adminForm = new JPanel();
        adminForm.add(new JLabel("Administrador:"));
        adminForm.add(new JTextField(20));
        add(adminForm, BorderLayout.CENTER);
    }
}