package vista.panels;
import modelo.Producto;
import modelo.ServicioProductos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class EditarProductoDialog extends JDialog {

    private ServicioProductos servicioProducto;
    private ProductosPanel productosPanel;
    private Producto producto;

    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JTextField txtFechaVenta;
    private JTextField txtCantidadVendida;

    public EditarProductoDialog(Producto producto, ServicioProductos servicioProducto, ProductosPanel productosPanel) {
        super((Frame) SwingUtilities.getWindowAncestor(productosPanel), "Editar Producto", true);
        this.producto = producto;
        this.servicioProducto = servicioProducto;
        this.productosPanel = productosPanel;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(400, 250);
        setLocationRelativeTo((Frame) SwingUtilities.getWindowAncestor(productosPanel));

        // Configuración del panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        // Componentes
        JLabel lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField(producto.getNombre(), 20);

        JLabel lblPrecio = new JLabel("Precio:");
        txtPrecio = new JTextField(String.valueOf(producto.getPrecio()), 10);

        JLabel lblFechaVenta = new JLabel("Fecha de Venta:");
        txtFechaVenta = new JTextField(producto.getFechaVenta().toString(), 10);

        JLabel lblCantidadVendida = new JLabel("Cantidad Vendida:");
        txtCantidadVendida = new JTextField(String.valueOf(producto.getCantidadVendida()), 5);

        JButton btnEditar = new JButton("Editar");
        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarProducto();
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
        panel.add(btnEditar);

        // Agregar panel al diálogo
        add(panel);

        setVisible(true);
    }

    private void editarProducto() {
        String nombre = txtNombre.getText();
        double precio = Double.parseDouble(txtPrecio.getText());
        LocalDate fechaVenta = LocalDate.parse(txtFechaVenta.getText());
        int cantidadVendida = Integer.parseInt(txtCantidadVendida.getText());

        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setFechaVenta(fechaVenta);
        producto.setCantidadVendida(cantidadVendida);

        if (servicioProducto.editarProducto(producto)) {
            JOptionPane.showMessageDialog(this, "Producto editado exitosamente.");
            productosPanel.recargarTabla();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al editar el producto.");
        }
    }
}
