package simuladorcarreras.dao;

import simuladorcarreras.model.AutoF1;
import simuladorcarreras.model.Vehiculo;
import simuladorcarreras.util.ConexionBD;

import java.sql.*;

public class VehiculoDAO {

    public Vehiculo obtenerPorId(int id) {
        String sqlVehiculo = "SELECT * FROM vehiculos WHERE id = ?";
        Vehiculo vehiculo = null;

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sqlVehiculo)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String tipo = rs.getString("tipo");
                    int vehiculoId = rs.getInt("id");
                    String modelo = rs.getString("modelo");
                    String fabricante = rs.getString("fabricante");
                    double velocidadMaxima = rs.getDouble("velocidad_maxima");
                    double confiabilidad = rs.getDouble("confiabilidad");
                    double condicionNeumaticos = rs.getDouble("condicion_neumaticos");

                    if ("F1".equalsIgnoreCase(tipo)) {
                        String sqlF1 = "SELECT aerodinamica FROM auto_f1 WHERE vehiculo_id = ?";
                        try (PreparedStatement stmtF1 = conn.prepareStatement(sqlF1)) {
                            stmtF1.setInt(1, vehiculoId);
                            try (ResultSet rsF1 = stmtF1.executeQuery()) {
                                if (rsF1.next()) {
                                    double aerodinamica = rsF1.getDouble("aerodinamica");
                                    vehiculo = new AutoF1(vehiculoId, modelo, fabricante, velocidadMaxima, confiabilidad, condicionNeumaticos, aerodinamica);
                                } else {
                                    System.out.println("Warning: Vehículo F1 sin datos en auto_f1");
                                    vehiculo = new Vehiculo(vehiculoId, modelo, fabricante, velocidadMaxima, confiabilidad, condicionNeumaticos) {
                                        @Override
                                        public double calcularEficiencia() {
                                            return velocidadMaxima * confiabilidad * condicionNeumaticos;
                                        }
                                    };
                                }
                            }
                        }
                    } else {
                        vehiculo = new Vehiculo(vehiculoId, modelo, fabricante, velocidadMaxima, confiabilidad, condicionNeumaticos) {
                            @Override
                            public double calcularEficiencia() {
                                return velocidadMaxima * confiabilidad * condicionNeumaticos;
                            }
                        };
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener vehículo: " + e.getMessage());
            e.printStackTrace();
        }

        return vehiculo;
    }

    public boolean insertar(Vehiculo vehiculo) {
        String sqlInsertVehiculo = "INSERT INTO vehiculos (modelo, velocidad_maxima, condicion_neumaticos, fabricante, tipo, confiabilidad, escuderia_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sqlInsertVehiculo, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, vehiculo.getModelo());
            stmt.setDouble(2, vehiculo.getVelocidadMaxima());
            stmt.setDouble(3, vehiculo.getCondicionNeumaticos());
            stmt.setString(4, vehiculo.getFabricante());
            String tipo = (vehiculo instanceof AutoF1) ? "F1" : "Normal";
            stmt.setString(5, tipo);
            stmt.setDouble(6, vehiculo.getConfiabilidad());
            stmt.setNull(7, Types.INTEGER); // O cambiar según corresponda

            int filas = stmt.executeUpdate();
            if (filas == 0) return false;

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idGenerado = generatedKeys.getInt(1);
                    vehiculo.setId(idGenerado);

                    if (vehiculo instanceof AutoF1) {
                        String sqlInsertF1 = "INSERT INTO auto_f1 (vehiculo_id, aerodinamica) VALUES (?, ?)";
                        try (PreparedStatement stmtF1 = conn.prepareStatement(sqlInsertF1)) {
                            stmtF1.setInt(1, idGenerado);
                            stmtF1.setDouble(2, ((AutoF1) vehiculo).getAerodinamica());
                            stmtF1.executeUpdate();
                        }
                    }
                    return true;
                } else {
                    return false;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al insertar vehículo: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Vehiculo vehiculo) {
        String sqlUpdateVehiculo = "UPDATE vehiculos SET modelo = ?, velocidad_maxima = ?, condicion_neumaticos = ?, fabricante = ?, tipo = ?, confiabilidad = ? WHERE id = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sqlUpdateVehiculo)) {

            stmt.setString(1, vehiculo.getModelo());
            stmt.setDouble(2, vehiculo.getVelocidadMaxima());
            stmt.setDouble(3, vehiculo.getCondicionNeumaticos());
            stmt.setString(4, vehiculo.getFabricante());
            String tipo = (vehiculo instanceof AutoF1) ? "F1" : "Normal";
            stmt.setString(5, tipo);
            stmt.setDouble(6, vehiculo.getConfiabilidad());
            stmt.setInt(7, vehiculo.getId());

            int filas = stmt.executeUpdate();
            if (filas == 0) return false;

            if (vehiculo instanceof AutoF1) {
                String sqlUpdateF1 = "UPDATE auto_f1 SET aerodinamica = ? WHERE vehiculo_id = ?";
                try (PreparedStatement stmtF1 = conn.prepareStatement(sqlUpdateF1)) {
                    stmtF1.setDouble(1, ((AutoF1) vehiculo).getAerodinamica());
                    stmtF1.setInt(2, vehiculo.getId());
                    stmtF1.executeUpdate();
                }
            }
            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar vehículo: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        try (Connection conn = ConexionBD.obtenerConexion()) {
            String sqlDeleteF1 = "DELETE FROM auto_f1 WHERE vehiculo_id = ?";
            try (PreparedStatement stmtF1 = conn.prepareStatement(sqlDeleteF1)) {
                stmtF1.setInt(1, id);
                stmtF1.executeUpdate();
            }

            String sqlDeleteVehiculo = "DELETE FROM vehiculos WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlDeleteVehiculo)) {
                stmt.setInt(1, id);
                int filas = stmt.executeUpdate();
                return filas > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar vehículo: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public Vehiculo buscarPorEscuderiaId(int escuderiaId) {
        String sql = "SELECT id FROM vehiculos WHERE escuderia_id = ? LIMIT 1";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, escuderiaId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int idVehiculo = rs.getInt("id");
                    return obtenerPorId(idVehiculo); // ya devuelve F1 o normal
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar vehículo por escudería: " + e.getMessage());
        }
        return null;
    }
}
