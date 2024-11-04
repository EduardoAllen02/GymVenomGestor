package modelo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ServicioUsuarios {
    // Método para obtener todos los usuarios
    public List<Usuario> obtenerUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT * FROM usuarios";

        try (Connection conn = ConexionBd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                    rs.getInt("IdUsuario"),
                    rs.getString("Nombre"),
                    rs.getString("Apellido"),
                    rs.getString("TelUsuario"),
                    rs.getString("TelEmergencia1"),
                    rs.getString("TelEmergencia2"),
                    rs.getDate("FechaNacimiento").toLocalDate()
                );
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    // Método para eliminar un usuario por ID
    public boolean eliminarUsuario(int id) {
        String query = "DELETE FROM usuarios WHERE IdUsuario = ?";
        try (Connection conn = ConexionBd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Métodos para agregar y actualizar usuarios irán aquí
    
    // ...
     // Método para actualizar un usuario
    public boolean actualizarUsuario(Usuario usuario) {
        String query = "UPDATE usuarios SET Nombre=?, Apellido=?, TelUsuario=?, TelEmergencia1=?, TelEmergencia2=? WHERE IdUsuario=?";
        try (Connection conn = ConexionBd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getTelUsuario());
            stmt.setString(4, usuario.getTelEmergencia1());
            stmt.setString(5, usuario.getTelEmergencia2());
            stmt.setInt(6, usuario.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
        // Método para obtener un usuario por ID
    public Usuario obtenerUsuarioPorId(int id) {
        String query = "SELECT * FROM usuarios WHERE IdUsuario = ?";
        try (Connection conn = ConexionBd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Usuario(
                    rs.getInt("IdUsuario"),
                    rs.getString("Nombre"),
                    rs.getString("Apellido"),
                    rs.getString("TelUsuario"),
                    rs.getString("TelEmergencia1"),
                    rs.getString("TelEmergencia2"),
                    rs.getDate("FechaNacimiento").toLocalDate()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    // Método para agregar un nuevo usuario
public boolean agregarUsuario(Usuario usuario) {
    String query = "INSERT INTO usuarios (Nombre, Apellido, TelUsuario, TelEmergencia1, TelEmergencia2, FechaNacimiento) VALUES (?, ?, ?, ?, ?, ?)";
    try (Connection conn = ConexionBd.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        
        stmt.setString(1, usuario.getNombre());
        stmt.setString(2, usuario.getApellido());
        stmt.setString(3, usuario.getTelUsuario());
        stmt.setString(4, usuario.getTelEmergencia1());
        stmt.setString(5, usuario.getTelEmergencia2());
        stmt.setDate(6, java.sql.Date.valueOf(usuario.getFechaNacimiento())); // Convertir LocalDate a java.sql.Date
        
        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
}
