package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;

public class MainView extends JFrame {
    private JButton btnUsuarios;
    private JButton btnProductos;
    private JButton btnAsistencias;
    private JButton btnAdministradores;
    private JButton btnCerrarSesion;
    private JPanel contentPanel;
    private ImgPanel imgPanel; // Cambiamos a variable de instancia

    public MainView() {
        setTitle("GymVenom - Panel Principal");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        // Maximizar la ventana al iniciar
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        // Barra de navegación (panel superior)
        JPanel navBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        navBar.setBackground(Color.BLACK);

        btnUsuarios = createNavButton("Usuarios");
        btnProductos = createNavButton("Productos");
        btnAsistencias = createNavButton("Asistencias");
        btnAdministradores = createNavButton("Administradores");
        btnCerrarSesion = createNavButton("Cerrar Sesión");

        // Agregar botones a la barra de navegación
        navBar.add(btnUsuarios);
        navBar.add(btnProductos);
        navBar.add(btnAsistencias);
        navBar.add(btnAdministradores);
        navBar.add(Box.createHorizontalGlue()); // Espacio flexible entre botones y el botón de cerrar sesión
        navBar.add(btnCerrarSesion);

        add(navBar, BorderLayout.NORTH);

        // Panel de contenido dinámico
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
 
        add(contentPanel, BorderLayout.WEST);
       
        // Panel lateral con imagen
        imgPanel = new ImgPanel("src/assets/images/Cover2.jpg"); // Asegurarse de que la ruta sea correcta        
        imgPanel.setPreferredSize(new Dimension(450, 900)); // Tamaño preferido inicial
        add(imgPanel, BorderLayout.EAST);

        // Escuchar cambios en el tamaño de la ventana para ajustar el panel de imagen
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int widthIMG = (int) (getWidth() * 0.29); // 30% del ancho de la ventana
                int height = getHeight(); // Altura completa de la ventana
                int widthContent = (int) (getWidth()*0.70); // 70 % para contenido
                imgPanel.setPreferredSize(new Dimension(widthIMG, height));
                imgPanel.revalidate(); // Refrescar el panel para aplicar el nuevo tamaño
                contentPanel.setPreferredSize(new Dimension(widthContent, height));
            }
        });
    }

    // Método para crear botones de la barra de navegación con estilo
    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setFont(loadRobotoFont(17));
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    // Método para cambiar el panel de contenido dinámico
    public void setContentPanel(JPanel newPanel) {
        contentPanel.removeAll();           // Quitar el contenido actual
        contentPanel.add(newPanel, BorderLayout.CENTER); // Añadir el nuevo panel
        contentPanel.revalidate();          // Refrescar el panel para actualizar cambios
        contentPanel.repaint();
    }

    // Métodos para asignar listeners a cada botón de la barra de navegación
    public void setActionListeners(ActionListener usuariosAction, ActionListener productosAction, ActionListener asistenciasAction, ActionListener adminsAction, ActionListener cerrarSesionAction) {
        btnUsuarios.addActionListener(usuariosAction);
        btnProductos.addActionListener(productosAction);
        btnAsistencias.addActionListener(asistenciasAction);
        btnAdministradores.addActionListener(adminsAction);
        btnCerrarSesion.addActionListener(cerrarSesionAction);
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
