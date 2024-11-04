package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBd {
    // Detalles de conexión para la base de datos MySQL en AWS RDS
    private static final String URL = "jdbc:mysql://bdgym.cn844gw0ooie.us-east-2.rds.amazonaws.com:3306/bdgym";
    private static final String USER = "admin";
    private static final String PASSWORD = "gymvenom";

    public static Connection getConnection() throws SQLException {
        try {
            // Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Conexion exitosa");
        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el driver de MySQL: " + e.getMessage());
        }

        // Retornar la conexión
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

