package simuladorcarreras.dao;

import simuladorcarreras.model.Circuito;
import simuladorcarreras.model.TipoCircuito;
import simuladorcarreras.model.TipoTerreno;
import simuladorcarreras.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CircuitoDAO {

    public List<Circuito> obtenerTodos() {
        List<Circuito> lista = new ArrayList<>();
        String sql = "SELECT * FROM circuitos";

        try (Connection conn = ConexionBD.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Circuito c = new Circuito(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("ubicacion"),
                        rs.getDouble("longitud_km"),
                        rs.getDouble("dificultad"),
                        TipoTerreno.valueOf(rs.getString("terreno").toUpperCase()),
                        TipoCircuito.valueOf(rs.getString("tipo_circuito").toUpperCase())
                );
                lista.add(c);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener circuitos: " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }

    public Circuito obtenerPorId(int id) {
        Circuito circuito = null;
        String sql = "SELECT * FROM circuitos WHERE id = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    circuito = new Circuito(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("ubicacion"),
                            rs.getDouble("longitud_km"),
                            rs.getDouble("dificultad"),
                            TipoTerreno.valueOf(rs.getString("terreno").toUpperCase()),
                            TipoCircuito.valueOf(rs.getString("tipo_circuito").toUpperCase())
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener circuito por id: " + e.getMessage());
            e.printStackTrace();
        }

        return circuito;
    }

    public boolean insertar(Circuito circuito) {
        String sql = "INSERT INTO circuitos (nombre, ubicacion, longitud_km, dificultad, terreno, tipo_circuito) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, circuito.getNombre());
            stmt.setString(2, circuito.getUbicacion());
            stmt.setDouble(3, circuito.getLongitudKm());
            stmt.setDouble(4, circuito.getDificultad());
            stmt.setString(5, circuito.getTerreno().name());
            stmt.setString(6, circuito.getTipoCircuito().name());

            int filas = stmt.executeUpdate();
            if (filas > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        circuito.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar circuito: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean actualizar(Circuito circuito) {
        String sql = "UPDATE circuitos SET nombre = ?, ubicacion = ?, longitud_km = ?, dificultad = ?, terreno = ?, tipo_circuito = ? WHERE id = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, circuito.getNombre());
            stmt.setString(2, circuito.getUbicacion());
            stmt.setDouble(3, circuito.getLongitudKm());
            stmt.setDouble(4, circuito.getDificultad());
            stmt.setString(5, circuito.getTerreno().name());
            stmt.setString(6, circuito.getTipoCircuito().name());
            stmt.setInt(7, circuito.getId());

            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar circuito: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM circuitos WHERE id = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar circuito: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public List<Circuito> obtenerPorTipo(TipoCircuito tipo) {
        List<Circuito> lista = new ArrayList<>();
        String sql = "SELECT * FROM circuitos WHERE tipo_circuito = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tipo.name());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Circuito c = new Circuito(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("ubicacion"),
                            rs.getDouble("longitud_km"),
                            rs.getDouble("dificultad"),
                            TipoTerreno.valueOf(rs.getString("terreno").toUpperCase()),
                            TipoCircuito.valueOf(rs.getString("tipo_circuito").toUpperCase())
                    );
                    lista.add(c);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener circuitos por tipo: " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }
}
