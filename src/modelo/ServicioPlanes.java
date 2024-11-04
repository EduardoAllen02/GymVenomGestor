package modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicioPlanes {

    // Método para obtener todos los planes
    public List<Plan> obtenerPlanes() {
        List<Plan> planes = new ArrayList<>();
        String query = "SELECT * FROM planes";

        try (Connection conn = ConexionBd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Plan plan = new Plan(
                    rs.getInt("idplan"),
                    rs.getString("Nombre"),
                    rs.getInt("Duracion"),
                    rs.getDouble("Costo")
                );
                planes.add(plan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planes;
    }

    // Método para eliminar un plan por ID
    public boolean eliminarPlan(int id) {
        String query = "DELETE FROM planes WHERE idplan = ?";
        try (Connection conn = ConexionBd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener un plan por ID
    public Plan obtenerPlanPorId(int id) {
        String query = "SELECT * FROM planes WHERE idplan = ?";
        try (Connection conn = ConexionBd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Plan(
                    rs.getInt("idplan"),
                    rs.getString("Nombre"),
                    rs.getInt("Duracion"),
                    rs.getDouble("Costo")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para agregar un nuevo plan
    public boolean agregarPlan(Plan plan) {
        String query = "INSERT INTO planes (Nombre, Duracion, Costo) VALUES (?, ?, ?)";
        try (Connection conn = ConexionBd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, plan.getNombre());
            stmt.setInt(2, plan.getDuracion());
            stmt.setDouble(3, plan.getCosto());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para actualizar un plan
    public boolean actualizarPlan(Plan plan) {
        String query = "UPDATE planes SET Nombre = ?, Duracion = ?, Costo = ? WHERE idplan = ?";
        try (Connection conn = ConexionBd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, plan.getNombre());
            stmt.setInt(2, plan.getDuracion());
            stmt.setDouble(3, plan.getCosto());
            stmt.setInt(4, plan.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
