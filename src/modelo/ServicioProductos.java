package modelo;
import modelo.Producto;
import java.util.ArrayList;
import java.util.List;

public class ServicioProductos {

    private List<Producto> productos;

    public ServicioProductos() {
        productos = new ArrayList<>();
    }

    public void registrarProducto(Producto producto) {
        productos.add(producto);
    }

    public boolean editarProducto(Producto producto) {
        for (Producto p : productos) {
            if (p.getId() == producto.getId()) {
                p.setNombre(producto.getNombre());
                p.setPrecio(producto.getPrecio());
                p.setFechaVenta(producto.getFechaVenta());
                p.setCantidadVendida(producto.getCantidadVendida());
                return true;
            }
        }
        return false;
    }

    public boolean eliminarProducto(int id) {
        for (Producto p : productos) {
            if (p.getId() == id) {
                productos.remove(p);
                return true;
            }
        }
        return false;
    }

    public Producto obtenerProductoPorId(int id) {
        for (Producto p : productos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public List<Producto> obtenerProductos() {
        return productos;
    }
}

