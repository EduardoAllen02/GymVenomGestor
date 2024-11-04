package controlador;

import vista.LoginView;
import vista.MainView;
import vista.panels.*;
import modelo.ServicioLogin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class ControladorLogin {
    private LoginView loginView;
    private ServicioLogin servicioLogin;

    public ControladorLogin(LoginView loginView, ServicioLogin servicioLogin) {
        this.loginView = loginView;
        this.servicioLogin = servicioLogin;

        this.loginView.setLoginAction(new LoginAction());
    }

    class LoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUser();
            String password = loginView.getPassword();

            if (servicioLogin.validarCredenciales(username, password)) {
                JOptionPane.showMessageDialog(loginView, "Bienvenido a GymVenom, " + username);
                loginView.dispose(); // Cerrar LoginView

                // Iniciar la MainView y configurar los listeners para los botones
                MainView mainView = new MainView();
                configurarMainView(mainView);
                mainView.setVisible(true); // Mostrar la MainView después del login
            } else {
                JOptionPane.showMessageDialog(loginView, "Credenciales inválidas");
            }
        }
    }

    private void configurarMainView(MainView mainView) {
        mainView.setActionListeners(
            e -> mainView.setContentPanel(new UsuariosPanel()),      // Panel de usuarios
            e -> mainView.setContentPanel(new ProductosPanel()),      // Panel de productos
            e -> mainView.setContentPanel(new AsistenciasPanel()),    // Panel de asistencias
            e -> mainView.setContentPanel(new AdministradoresPanel()), // Panel de administradores
            e -> System.exit(0)                                       // Acción para cerrar sesión
        );

        // Establecer un panel predeterminado al abrir la vista
        mainView.setContentPanel(new UsuariosPanel());
    }
}
