package modelo.conexion;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
/**
 * Singleton de conexión a MySQL.
 * Ajusta URL, USER y PASSWORD según tu entorno.
 */
public class Conexion {
 
    // ── Configuración ─────────────────────────────────────────────────────────
    private static final String URL      = "jdbc:mysql://localhost:3306/donaparato"
                                         + "?useSSL=false&serverTimezone=America/Bogota"
                                         + "&allowPublicKeyRetrieval=true";
    private static final String USER     = "root";
    private static final String PASSWORD = "root";          // <── cambia tu contraseña aquí
 
    private static Conexion instancia;
    private Connection conexion;
 
    // ── Constructor privado ───────────────────────────────────────────────────
    private Conexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                "Driver MySQL no encontrado. Agrega mysql-connector-j al classpath.", e);
        } catch (SQLException e) {
            throw new RuntimeException(
                "No se pudo conectar a la base de datos: " + e.getMessage(), e);
        }
    }
 
    /**
     * Devuelve la instancia única (Singleton).
     * Si la conexión fue cerrada o es inválida, crea una nueva.
     */
    public static Conexion getInstancia() {
        try {
            if (instancia == null || instancia.conexion.isClosed()) {
                instancia = new Conexion();
            }
        } catch (SQLException e) {
            instancia = new Conexion();
        }
        return instancia;
    }
 
    /** Retorna el objeto {@link Connection} activo. */
    public Connection getConexion() {
        return conexion;
    }
 
    /** Cierra la conexión y destruye la instancia. */
    public static void cerrar() {
        if (instancia != null) {
            try {
                instancia.conexion.close();
            } catch (SQLException ignored) {}
            instancia = null;
        }
    }
}