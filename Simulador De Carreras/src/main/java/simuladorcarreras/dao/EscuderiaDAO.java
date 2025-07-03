package simuladorcarreras.dao;

import simuladorcarreras.model.Escuderia;
import simuladorcarreras.model.Piloto;
import simuladorcarreras.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EscuderiaDAO {

    public List<Escuderia> obtenerTodas() {
        List<Escuderia> lista = new ArrayList<>();
        String sql = "SELECT * FROM escuderia";

        try (Connection conn = ConexionBD.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Escuderia e = new Escuderia(
                        rs.getInt("id"),
                        rs.getString("nombre")
                );
                lista.add(e);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar escuderías: " + e.getMessage());
        }

        return lista;
    }

    public List<Piloto> obtenerSuplentesPorEscuderia(int escuderiaId) {
        List<Piloto> suplentes = new ArrayList<>();
        String sql = "SELECT p.* FROM escuderia_suplentes es " +
                "JOIN pilotos p ON es.piloto_id = p.id " +
                "WHERE es.escuderia_id = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, escuderiaId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Piloto piloto = new Piloto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("nacionalidad"),
                        rs.getInt("nivel_ataque"),
                        rs.getBoolean("es_real"),
                        rs.getObject("usuario_id") != null ? rs.getInt("usuario_id") : null
                );
                piloto.setEscuderiaId(rs.getObject("escuderia_id") != null ? rs.getInt("escuderia_id") : null);
                suplentes.add(piloto);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener suplentes: " + e.getMessage());
            e.printStackTrace();
        }

        return suplentes;
    }


    public boolean agregarPilotoSuplente(int escuderiaId, int pilotoId) {
        String sql = "INSERT INTO escuderia_suplentes (escuderia_id, piloto_id) VALUES (?, ?)";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, escuderiaId);
            stmt.setInt(2, pilotoId);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al agregar piloto suplente: " + e.getMessage());
            return false;
        }
    }
    public String obtenerNombreEscuderiaPorId(int escuderiaId) {
        String nombre = "";
        String sql = "SELECT nombre FROM escuderia WHERE id = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, escuderiaId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    nombre = rs.getString("nombre");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener nombre de escudería: " + e.getMessage());
        }

        return nombre;
    }

    public int obtenerPrimerPilotoId(int escuderiaId) {
        int id = -1;
        String sql = "SELECT primer_piloto_id FROM escuderia WHERE id = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, escuderiaId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    id = rs.getInt("primer_piloto_id");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener primer piloto: " + e.getMessage());
        }
        return id;
    }

    public int obtenerSegundoPilotoId(int escuderiaId) {
        int id = -1;
        String sql = "SELECT segundo_piloto_id FROM escuderia WHERE id = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, escuderiaId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    id = rs.getInt("segundo_piloto_id");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener segundo piloto: " + e.getMessage());
        }
        return id;
    }
}
