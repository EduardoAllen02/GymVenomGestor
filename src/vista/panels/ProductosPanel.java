package vista.panels;
import modelo.Producto;
import modelo.ServicioProductos;
import vista.ButtonColumn;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class ProductosPanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private ServicioProductos servicioProducto;

    public ProductosPanel() {
        servicioProducto = new ServicioProductos();
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        // Configuración del título
        JLabel titleLabel = new JLabel("Gestión de Productos", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, BorderLayout.NORTH);

        // Configuración de la tabla
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Solo las columnas de "Editar" y "Eliminar" son editables (botones)
                return column == 4 || column == 5;
            }
        };
        tableModel.setColumnIdentifiers(new Object[]{"ID", "Nombre", "Precio", "Fecha de Venta", "Cantidad Vendida", "Editar", "Eliminar"});
        table = new JTable(tableModel);
        cargarDatosProductos();

        // Configurar botón de "Eliminar" en cada fila
        Action delete = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = Integer.parseInt(e.getActionCommand());
                int idProducto = (int) tableModel.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(null, "¿Seguro que deseas eliminar este producto?");
                if (confirm == JOptionPane.YES_OPTION) {
                    if (servicioProducto.eliminarProducto(idProducto)) {
                        cargarDatosProductos();
                        JOptionPane.showMessageDialog(null, "Producto eliminado exitosamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al eliminar el producto.");
                    }
                }
            }
        };

        // Configurar botón de "Editar" en cada fila
        Action edit = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = Integer.parseInt(e.getActionCommand());
                int idProducto = (int) tableModel.getValueAt(row, 0);
                Producto producto = servicioProducto.obtenerProductoPorId(idProducto);
                if (producto != null) {
                    new EditarProductoDialog(producto, servicioProducto, ProductosPanel.this);
                }
            }
        };

        new ButtonColumn(table, edit, 4); // Columna "Editar"
        new ButtonColumn(table, delete, 5); // Columna "Eliminar"

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Botón de "Registrar Producto" al final del panel
        JButton addButton = new JButton("Registrar Nuevo Producto");
        addButton.setFont(new Font("Roboto", Font.PLAIN, 16));
        addButton.setBackground(new Color(34, 139, 34));
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(e -> {
            new RegistrarProductoDialog((Frame) SwingUtilities.getWindowAncestor(this), servicioProducto);
            cargarDatosProductos();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(addButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void cargarDatosProductos() {
        List<Producto> productos = servicioProducto.obtenerProductos();
        tableModel.setRowCount(0); // Limpiar filas existentes
        for (Producto producto : productos) {
            tableModel.addRow(new Object[]{
                producto.getId(),
                producto.getNombre(),
                producto.getPrecio(),
                producto.getFechaVenta(),
                producto.getCantidadVendida(),
                "Editar",
                "Eliminar"
            });
        }
    }

    // Método para recargar la tabla (útil después de guardar cambios en el diálogo de edición)
    public void recargarTabla() {
        cargarDatosProductos();
    }
}

