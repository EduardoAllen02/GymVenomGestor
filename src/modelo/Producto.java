package modelo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class Producto {
    private String nombre;
    private double precio;
    private Date fechaVenta;
    private int cantidadVendida;

    public Producto(String nombre, double precio, Date fechaVenta, int cantidadVendida) {
        this.nombre = nombre;
        this.precio = precio;
        this.fechaVenta = fechaVenta;
        this.cantidadVendida = cantidadVendida;
    }
    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public  Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public int getCantidadVendida() {
        return cantidadVendida;
    }

    public void setCantidadVendida(int cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }
}
