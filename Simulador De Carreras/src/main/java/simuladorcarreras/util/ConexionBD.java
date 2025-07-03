package simuladorcarreras.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/simulador_carreras";
    private static final String USUARIO = "root";
    private static final String CONTRASEÑA = "";

    public static Connection obtenerConexion() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos.");
            e.printStackTrace();
        }
        return conexion;
    }
}
