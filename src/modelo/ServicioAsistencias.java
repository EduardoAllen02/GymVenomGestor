package modelo;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ServicioAsistencias {
  // Método para registrar una asistencia en la base de datos
    public boolean registrarAsistencia(int idUsuario) {
        // Verificar el estado de la membresía antes de registrar la asistencia
        String estadoMembresia = obtenerEstadoMembresia(idUsuario);

        if ("VENCIDO".equals(estadoMembresia)) {
            JOptionPane.showMessageDialog(null, "Membresía vencida. No se puede registrar la asistencia.");
            return false;
        } else if ("ACTIVO".equals(estadoMembresia)) {
            // Registrar la asistencia solo si la membresía está activa
            String query = "INSERT INTO asistencia (IdUsuario, Fecha, Hora) VALUES (?, ?, ?)";
            try (Connection conn = ConexionBd.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setInt(1, idUsuario);
                stmt.setDate(2, java.sql.Date.valueOf(LocalDate.now())); // Fecha actual
                stmt.setTime(3, Time.valueOf(LocalTime.now())); // Hora actual

                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Membresía vigente. Asistencia registrada correctamente.");
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al registrar la asistencia.");
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error: Estado de membresía desconocido.");
            return false;
        }
    }

    // Método privado para obtener el estado de la membresía del usuario
    private String obtenerEstadoMembresia(int idUsuario) {
        String query = "SELECT Estado FROM membresia WHERE IdUsuario = ?";
        try (Connection conn = ConexionBd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("Estado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Devuelve null si ocurre algún error o no se encuentra el usuario
    }
       // Método para obtener asistencias filtradas por nombre y mes
    public List<Asistencia> obtenerAsistencias(String nombre, int mes) {
        List<Asistencia> asistencias = new ArrayList<>();
        StringBuilder query = new StringBuilder(
                "SELECT a.IdAsistencia, a.IdUsuario, u.Nombre, u.Apellido, a.Fecha, a.Hora " +
                "FROM asistencia a " +
                "JOIN usuarios u ON a.IdUsuario = u.IdUsuario " +
                "WHERE 1=1");

        if (nombre != null && !nombre.isEmpty()) {
            query.append(" AND u.Nombre LIKE ?");
        }
        if (mes > 0) {
            query.append(" AND MONTH(a.Fecha) = ?");
        }

        try (Connection conn = ConexionBd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query.toString())) {

            int paramIndex = 1;
            if (nombre != null && !nombre.isEmpty()) {
                stmt.setString(paramIndex++, "%" + nombre + "%");
            }
            if (mes > 0) {
                stmt.setInt(paramIndex, mes);
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Asistencia asistencia = new Asistencia(
                    rs.getInt("IdAsistencia"),
                    rs.getInt("IdUsuario"),
                    rs.getString("Nombre"),
                    rs.getString("Apellido"),
                    rs.getDate("Fecha").toLocalDate(),
                    rs.getTime("Hora").toLocalTime()
                );
                asistencias.add(asistencia);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return asistencias;
    }

    // Método para eliminar una asistencia por ID
    public boolean eliminarAsistencia(int idAsistencia) {
        String query = "DELETE FROM asistencia WHERE IdAsistencia = ?";
        try (Connection conn = ConexionBd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idAsistencia);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para limpiar asistencias anteriores al mes actual
    public boolean limpiarAsistenciasAntiguas() {
        String query = "DELETE FROM asistencia WHERE Fecha < ?";
        LocalDate inicioMesActual = LocalDate.now().withDayOfMonth(1); // Primer día del mes actual

        try (Connection conn = ConexionBd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDate(1, java.sql.Date.valueOf(inicioMesActual));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
