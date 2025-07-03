package simuladorcarreras.dao;

import simuladorcarreras.model.Usuario;
import simuladorcarreras.util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public Usuario validarLogin(String nombreUsuario, String contrasenia) {
        Usuario usuario = null;

        String query = "SELECT * FROM usuarios WHERE nombre_usuario = ? AND contrasenia = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nombreUsuario);
            stmt.setString(2, contrasenia);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre_usuario"),
                        rs.getString("contrasenia")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }
}
