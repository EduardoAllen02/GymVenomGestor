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
public boolean agregarUsuario(Usuario usuario) {
    String query = "INSERT INTO usuarios (Nombre, Apellido, TelUsuario, TelEmergencia1, TelEmergencia2, FechaNacimiento) VALUES (?, ?, ?, ?, ?, ?)";
    try (Connection conn = ConexionBd.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setString(1, usuario.getNombre());
        stmt.setString(2, usuario.getApellido());
        stmt.setString(3, usuario.getTelUsuario());
        stmt.setString(4, usuario.getTelEmergencia1());
        stmt.setString(5, usuario.getTelEmergencia2());
        stmt.setDate(6, java.sql.Date.valueOf(usuario.getFechaNacimiento()));

        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
  // Método para obtener usuarios filtrados por nombre
public List<Usuario> obtenerUsuariosPorNombre(String nombre) {
    List<Usuario> usuarios = new ArrayList<>();
    StringBuilder query = new StringBuilder("SELECT * FROM usuarios WHERE 1=1");

    if (nombre != null && !nombre.isEmpty()) {
        query.append(" AND Nombre LIKE ?");
    }

    try (Connection conn = ConexionBd.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query.toString())) {

        int index = 1;
        if (nombre != null && !nombre.isEmpty()) {
            stmt.setString(index, "%" + nombre + "%");
        }

        ResultSet rs = stmt.executeQuery();

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

// Método para obtener usuarios filtrados por estado de membresía
public List<Usuario> obtenerUsuariosPorEstadoMembresia(String estadoMembresia) {
    List<Usuario> usuarios = new ArrayList<>();
    StringBuilder query = new StringBuilder("SELECT u.* FROM usuarios u LEFT JOIN membresia m ON u.IdUsuario = m.IdUsuario WHERE 1=1");

    if (estadoMembresia != null && !estadoMembresia.equals("AMBAS")) {
        query.append(" AND m.Estado = ?");
    }

    try (Connection conn = ConexionBd.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query.toString())) {

        if (estadoMembresia != null && !estadoMembresia.equals("AMBAS")) {
            stmt.setString(1, estadoMembresia);
        }

        ResultSet rs = stmt.executeQuery();

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
// Método para obtener el estado de la membresía de un usuario específico por ID
public String obtenerEstadoMembresia(int idUsuario) {
    String estadoMembresia = null;
    String query = "SELECT Estado FROM membresia WHERE IdUsuario = ?";

    try (Connection conn = ConexionBd.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, idUsuario);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            estadoMembresia = rs.getString("Estado");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return estadoMembresia;
}

}
