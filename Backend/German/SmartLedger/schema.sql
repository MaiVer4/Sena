-- schema.sql
-- Esquema de base de datos para SmartLedger

-- Tabla de usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT NOT NULL,
    nombre_usuario TEXT UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de transacciones
CREATE TABLE IF NOT EXISTS transacciones (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    usuario_id INTEGER NOT NULL,
    monto REAL NOT NULL,
    tipo TEXT NOT NULL CHECK(tipo IN ('ingreso', 'gasto')),
    categoria TEXT NOT NULL,
    fecha TEXT NOT NULL,
    descripcion TEXT,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- Índice para mejorar consultas por usuario
CREATE INDEX IF NOT EXISTS idx_transacciones_usuario 
ON transacciones(usuario_id);

-- Índice para búsquedas por fecha
CREATE INDEX IF NOT EXISTS idx_transacciones_fecha 
ON transacciones(fecha);