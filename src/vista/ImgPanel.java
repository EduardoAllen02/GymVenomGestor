package vista;

import javax.swing.*;
import java.awt.*;

public class ImgPanel extends JPanel {
    private Image backgroundImage;

    // Constructor que recibe la ruta de la imagen
    public ImgPanel(String imagePath) {
        backgroundImage = new ImageIcon(imagePath).getImage();
        if (backgroundImage == null) {
            System.err.println("Error: No se pudo cargar la imagen en la ruta: " + imagePath);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // Dibuja la imagen escalada al tamaño del panel
        }
    }
}

