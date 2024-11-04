package controlador;
import java.util.*;
import java.sql.*;

public class RegistroProductosController{
    private List<Producto> productos;

    public RegistroProductosController() {
        productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void eliminarProducto(int indice) {
        productos.remove(indice);
    }

    public void modificarProducto(int indice, Producto producto) {
        productos.set(indice, producto);
    }
}

