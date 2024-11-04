package controlador;
import vista.panels.*;

import controlador.ControladorLogin;
import modelo.ServicioLogin;
import vista.*;
public class MainController {
    
    public static void main(String[] args) {
           // Crear la vista de login y el servicio de login
        LoginView loginView = new LoginView();
        ServicioLogin servicioLogin = new ServicioLogin();

        // Crear el controlador de login y pasarle la vista y el servicio
        new ControladorLogin(loginView, servicioLogin);

        // Mostrar la vista de login
        loginView.setVisible(true);
    }
}
