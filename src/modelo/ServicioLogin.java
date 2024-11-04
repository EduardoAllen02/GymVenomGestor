package modelo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class ServicioLogin {
        public boolean validarCredenciales(String username, String password) {
        try (Connection conn = ConexionBd.getConnection()) {
            String query = "SELECT * FROM admin WHERE NombreAdmin=? AND ContraAdmin=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
