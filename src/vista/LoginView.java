package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
public class LoginView extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton;

    public LoginView() {
        
        setTitle("GymVenom - Inicio de Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        setResizable(true);
        setSize(1300, 900);
        
        // Establecer layout principal
        setLayout(new BorderLayout());

        // Panel lateral con imagen
        ImgPanel imgPanel = new ImgPanel("src/assets/images/GymVenomCover.jpg"); // Asegurarse de que la ruta sea correcta        
        imgPanel.setPreferredSize(new Dimension(450, 900));
        add(imgPanel, BorderLayout.WEST);

        // Panel de login
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(Color.white);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        //Texto Inicio Sesion
         
        JLabel titleLabel = new JLabel("INICIAR SESIÓN ADMIN");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setFont(loadRobotoFont(24));  // Cargar y aplicar la fuente Roboto

        // Campos de texto
        userField = new JTextField(15);
        passField = new JPasswordField(15);
        loginButton = new JButton("Iniciar Sesión");

        userField.setBorder(BorderFactory.createTitledBorder("Usuario"));
        passField.setBorder(BorderFactory.createTitledBorder("Contraseña"));

        // Añadir componentes al panel de login
        gbc.gridx = 0; gbc.gridy = 0;
        loginPanel.add(titleLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        loginPanel.add(userField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        loginPanel.add(passField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        loginPanel.add(loginButton, gbc);

        add(loginPanel, BorderLayout.CENTER);
    }

    public void setLoginAction(ActionListener action) {
        loginButton.addActionListener(action);
    }

    public String getUser() {
        return userField.getText();
    }

    public String getPassword() {
        return new String(passField.getPassword());
    }
       // Método para cargar la fuente Roboto
    private Font loadRobotoFont(float size) {
        try {
            // Cargar la fuente desde el archivo .ttf
            Font robotoFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/assets/fonts/Roboto-Regular.ttf"));
            return robotoFont.deriveFont(size); // Devolver la fuente con el tamaño especificado
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return new Font("SansSerif", Font.PLAIN, 24); // Fuente alternativa si hay error
        }
    }
}


