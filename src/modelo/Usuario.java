package modelo;

import java.time.LocalDate;
import java.util.Date;

public class Usuario {
    private int id;
    private String nombre;
    private String apellido;
    private String telUsuario;
    private String telEmergencia1;
    private String telEmergencia2;
    private LocalDate fechaNacimiento;

    public Usuario(int id, String nombre, String apellido, String telUsuario, String telEmergencia1, String telEmergencia2, LocalDate fechaNacimiento) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telUsuario = telUsuario;
        this.telEmergencia1 = telEmergencia1;
        this.telEmergencia2 = telEmergencia2;
        this.fechaNacimiento = fechaNacimiento;
    }

    // Getters y setters
        public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelUsuario() {
        return telUsuario;
    }

    public void setTelUsuario(String telUsuario) {
        this.telUsuario = telUsuario;
    }

    public String getTelEmergencia1() {
        return telEmergencia1;
    }

    public void setTelEmergencia1(String telEmergencia1) {
        this.telEmergencia1 = telEmergencia1;
    }

    public String getTelEmergencia2() {
        return telEmergencia2;
    }

    public void setTelEmergencia2(String telEmergencia2) {
        this.telEmergencia2 = telEmergencia2;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
