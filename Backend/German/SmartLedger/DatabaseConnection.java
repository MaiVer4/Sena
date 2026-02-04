package com.smartledger.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    
    private static final String DATABASE_DIR = "database";
    private static final String DATABASE_NAME = "smartledger.db";
    private static final String DATABASE_PATH = DATABASE_DIR + File.separator + DATABASE_NAME;
    private static final String URL = "jdbc:sqlite:" + DATABASE_PATH;
    
    private static Connection connection = null;

    /**
     * Obtiene una conexión a la base de datos SQLite.
     * Si no existe, crea la base de datos y las tablas.
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Crear directorio database si no existe
                File dbDir = new File(DATABASE_DIR);
                if (!dbDir.exists()) {
                    dbDir.mkdir();
                    System.out.println("📁 Directorio 'database' creado.");
                }

                // Cargar el driver de SQLite
                Class.forName("org.sqlite.JDBC");
                
                // Establecer conexión
                connection = DriverManager.getConnection(URL);
                System.out.println("✅ Conexión a SQLite establecida: " + DATABASE_PATH);
                
                // Crear tablas si no existen
                initializeTables();
                
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver SQLite no encontrado: " + e.getMessage());
            }
        }
        return connection;
    }

    /**
     * Inicializa las tablas de la base de datos
     */
    private static void initializeTables() throws SQLException {
        String createUsuariosTable = 
            "CREATE TABLE IF NOT EXISTS usuarios (" +
            "    id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "    nombre TEXT NOT NULL," +
            "    nombre_usuario TEXT UNIQUE NOT NULL," +
            "    password_hash TEXT NOT NULL," +
            "    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP" +
            ");";

        String createTransaccionesTable = 
            "CREATE TABLE IF NOT EXISTS transacciones (" +
            "    id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "    usuario_id INTEGER NOT NULL," +
            "    monto REAL NOT NULL," +
            "    tipo TEXT NOT NULL CHECK(tipo IN ('ingreso', 'gasto'))," +
            "    categoria TEXT NOT NULL," +
            "    fecha TEXT NOT NULL," +
            "    descripcion TEXT," +
            "    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP," +
            "    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE" +
            ");";

        String createIndexUsuario = 
            "CREATE INDEX IF NOT EXISTS idx_transacciones_usuario " +
            "ON transacciones(usuario_id);";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createUsuariosTable);
            stmt.execute(createTransaccionesTable);
            stmt.execute(createIndexUsuario);
            System.out.println("✅ Tablas verificadas/creadas correctamente.");
        }
    }

    /**
     * Cierra la conexión a la base de datos
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("🔒 Conexión a SQLite cerrada.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }

    /**
     * Verifica si la conexión está activa
     */
    public static boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}