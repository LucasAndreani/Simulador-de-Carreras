package simuladorcarreras.dao;

import simuladorcarreras.model.Piloto;
import simuladorcarreras.model.Vehiculo;
import simuladorcarreras.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PilotoDAO {

    private VehiculoDAO vehiculoDAO = new VehiculoDAO();

    public boolean crearPiloto(Piloto piloto) {
        String sql = "INSERT INTO pilotos (nombre, nacionalidad, nivel_ataque, es_real, usuario_id, escuderia_id, vehiculo_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, piloto.getNombre());
            stmt.setString(2, piloto.getNacionalidad());
            stmt.setInt(3, piloto.getNivelAtaque());
            stmt.setBoolean(4, piloto.isEsReal());

            if (piloto.getUsuarioId() != null) {
                stmt.setInt(5, piloto.getUsuarioId());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }

            if (piloto.getEscuderiaId() != null) {
                stmt.setInt(6, piloto.getEscuderiaId());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }

            if (piloto.getVehiculo() != null) {
                stmt.setInt(7, piloto.getVehiculo().getId());
            } else {
                stmt.setNull(7, Types.INTEGER);
            }

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al crear piloto: " + e.getMessage());
            return false;
        }
    }

    private Piloto construirPilotoDesdeResultSet(ResultSet rs) throws SQLException {
        Piloto p = new Piloto(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("nacionalidad"),
                rs.getInt("nivel_ataque"),
                rs.getBoolean("es_real"),
                rs.getObject("usuario_id") != null ? rs.getInt("usuario_id") : null
        );

        p.setEscuderiaId(rs.getObject("escuderia_id") != null ? rs.getInt("escuderia_id") : null);

        int vehiculoId = rs.getInt("vehiculo_id");
        if (!rs.wasNull()) {
            Vehiculo v = vehiculoDAO.obtenerPorId(vehiculoId);
            p.setVehiculo(v);
        }

        return p;
    }

    public List<Piloto> obtenerTodos() {
        List<Piloto> lista = new ArrayList<>();
        String sql = "SELECT * FROM pilotos";

        try (Connection conn = ConexionBD.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Piloto p = construirPilotoDesdeResultSet(rs);
                lista.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar pilotos: " + e.getMessage());
        }

        return lista;
    }

    public List<Piloto> obtenerPorUsuario(int usuarioId) {
        List<Piloto> lista = new ArrayList<>();
        String sql = "SELECT * FROM pilotos WHERE usuario_id = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Piloto p = construirPilotoDesdeResultSet(rs);
                    lista.add(p);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar pilotos por usuario: " + e.getMessage());
        }

        return lista;
    }

    public List<Piloto> obtenerPorEscuderia(int escuderiaId) {
        List<Piloto> lista = new ArrayList<>();
        String sql = "SELECT * FROM pilotos WHERE escuderia_id = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, escuderiaId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Piloto p = construirPilotoDesdeResultSet(rs);
                    lista.add(p);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar pilotos por escudería: " + e.getMessage());
        }

        return lista;
    }

    public List<Piloto> obtenerPilotosReales() {
        List<Piloto> lista = new ArrayList<>();
        String sql = "SELECT * FROM pilotos WHERE es_real = 1";

        try (Connection conn = ConexionBD.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Piloto p = construirPilotoDesdeResultSet(rs);
                lista.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar pilotos reales: " + e.getMessage());
        }

        return lista;
    }
}
