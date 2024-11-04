/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.panels;

/**
 *
 * @author Yeyian PC
 */
import javax.swing.*;
import java.awt.*;
public class ProductosPanel extends JPanel {
    public ProductosPanel() {
        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Gestión de Productos", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Agregar contenido para la gestión de productos
        JPanel productForm = new JPanel(); // Aquí se pueden añadir campos de formulario
        productForm.add(new JLabel("Producto:"));
        productForm.add(new JTextField(20));
        add(productForm, BorderLayout.CENTER);
    }
}
