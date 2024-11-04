package vista.panels;
import modelo.Producto;
import modelo.ServicioProductos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class RegistrarProductoDialog extends JDialog {

    private ServicioProductos servicioProducto;
    private ProductosPanel productosPanel;

    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JTextField txtFechaVenta;
    private JTextField txtCantidadVendida;

    public RegistrarProductoDialog(Frame owner, ServicioProductos servicioProducto, ProductosPanel productosPanel) {
        super(owner, "Registrar Producto", true);
        this.servicioProducto = servicioProducto;
        this.productosPanel = productosPanel;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(400, 250);
        setLocationRelativeTo(owner);

        // Configuración del panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        // Componentes
        JLabel lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField(20);

        JLabel lblPrecio = new JLabel("Precio:");
        txtPrecio = new JTextField(10);

        JLabel lblFechaVenta = new JLabel("Fecha de Venta:");
        txtFechaVenta = new JTextField(10);

        JLabel lblCantidadVendida = new JLabel("Cantidad Vendida:");
        txtCantidadVendida = new JTextField(5);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarProducto();
            }
        });

        // Agregar componentes al panel
        panel.add(lblNombre);
        panel.add(txtNombre);

        panel.add(lblPrecio);
        panel.add(txtPrecio);

        panel.add(lblFechaVenta);
        panel.add(txtFechaVenta);

        panel.add(lblCantidadVendida);
        panel.add(txtCantidadVendida);

        panel.add(new JLabel()); // Espacio en blanco
        panel.add(btnRegistrar);

        // Agregar panel al diálogo
        add(panel);

        setVisible(true);
    }

    private void registrarProducto() {
        String nombre = txtNombre.getText();
        double precio = Double.parseDouble(txtPrecio.getText());
        LocalDate fechaVenta = LocalDate.parse(txtFechaVenta.getText());
        int cantidadVendida = Integer.parseInt(txtCantidadVendida.getText());

        Producto producto = new Producto(nombre, precio, fechaVenta, cantidadVendida);

        if (servicioProducto.registrarProducto(producto)) {
            JOptionPane.showMessageDialog(this, "Producto registrado exitosamente.");
            productosPanel.recargarTabla();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar el producto.");
        }
    }
}
