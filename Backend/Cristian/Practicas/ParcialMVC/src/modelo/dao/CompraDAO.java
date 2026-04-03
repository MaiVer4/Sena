
package modelo.dao;
 
import modelo.conexion.Conexion;
import modelo.dto.CompraDTO;
 
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
 
/**
 * DAO que registra y consulta el historial de compras en MySQL.
 */
public class CompraDAO {
 
    private static final String SQL_INSERTAR =
            "INSERT INTO compras (cliente_nombre, cliente_apellido, cliente_cedula, " +
            "cliente_tipo, producto_nombre, valor_unitario, cantidad, " +
            "total_bruto, porcentaje_desc, valor_descuento, total_real) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
 
    private static final String SQL_LISTAR =
            "SELECT id, fecha_hora, cliente_nombre, cliente_apellido, cliente_cedula, " +
            "cliente_tipo, producto_nombre, valor_unitario, cantidad, " +
            "total_bruto, porcentaje_desc, valor_descuento, total_real " +
            "FROM compras ORDER BY fecha_hora DESC";
 
    private static final String SQL_BUSCAR_TEXTO =
            "SELECT id, fecha_hora, cliente_nombre, cliente_apellido, cliente_cedula, " +
            "cliente_tipo, producto_nombre, valor_unitario, cantidad, " +
            "total_bruto, porcentaje_desc, valor_descuento, total_real " +
            "FROM compras " +
            "WHERE cliente_nombre LIKE ? OR cliente_apellido LIKE ? " +
            "   OR cliente_cedula LIKE ? OR producto_nombre LIKE ? " +
            "ORDER BY fecha_hora DESC";
 
    private static final String SQL_BUSCAR_POR_CEDULA =
            "SELECT id, fecha_hora, cliente_nombre, cliente_apellido, cliente_cedula, " +
            "cliente_tipo, producto_nombre, valor_unitario, cantidad, " +
            "total_bruto, porcentaje_desc, valor_descuento, total_real " +
            "FROM compras WHERE cliente_cedula = ? ORDER BY fecha_hora DESC";
 
    private static final String SQL_BUSCAR_FECHA =
            "SELECT id, fecha_hora, cliente_nombre, cliente_apellido, cliente_cedula, " +
            "cliente_tipo, producto_nombre, valor_unitario, cantidad, " +
            "total_bruto, porcentaje_desc, valor_descuento, total_real " +
            "FROM compras " +
            "WHERE DATE(fecha_hora) BETWEEN ? AND ? " +
            "ORDER BY fecha_hora DESC";
 
    // ── Estadísticas ──────────────────────────────────────────────────────────
 
    /** Últimos 7 días, agrupado por DÍA */
    private static final String SQL_VENTAS_DIARIO =
            "SELECT DATE(fecha_hora) AS etiqueta, " +
            "COUNT(*) AS num_ventas, SUM(total_real) AS total " +
            "FROM compras " +
            "WHERE fecha_hora >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) " +
            "GROUP BY DATE(fecha_hora) ORDER BY etiqueta ASC";
 
    /** Últimas 4 semanas, agrupado por SEMANA (lunes–domingo) */
    private static final String SQL_VENTAS_SEMANAL =
            "SELECT DATE(DATE_SUB(fecha_hora, INTERVAL WEEKDAY(fecha_hora) DAY)) AS etiqueta, " +
            "COUNT(*) AS num_ventas, SUM(total_real) AS total " +
            "FROM compras " +
            "WHERE fecha_hora >= DATE_SUB(CURDATE(), INTERVAL 28 DAY) " +
            "GROUP BY etiqueta ORDER BY etiqueta ASC";
 
    /** Últimos 6 meses, agrupado por MES */
    private static final String SQL_VENTAS_MENSUAL =
            "SELECT DATE_FORMAT(fecha_hora, '%Y-%m-01') AS etiqueta, " +
            "COUNT(*) AS num_ventas, SUM(total_real) AS total " +
            "FROM compras " +
            "WHERE fecha_hora >= DATE_SUB(CURDATE(), INTERVAL 6 MONTH) " +
            "GROUP BY etiqueta ORDER BY etiqueta ASC";
 
    private static final String SQL_TOTAL_VENTAS =
            "SELECT COUNT(*) AS total FROM compras";
 
    private static final String SQL_TOTAL_RECAUDADO =
            "SELECT COALESCE(SUM(total_real), 0) AS total FROM compras";
 
    private static final String SQL_TOTAL_DESCUENTOS =
            "SELECT COALESCE(SUM(valor_descuento), 0) AS total FROM compras";
 
    private static final String SQL_PRODUCTO_TOP =
            "SELECT producto_nombre, SUM(cantidad) AS unidades " +
            "FROM compras GROUP BY producto_nombre " +
            "ORDER BY unidades DESC LIMIT 1";
 
    // ── INSERTAR ──────────────────────────────────────────────────────────────
 
    /**
     * Registra una compra en la tabla compras.
     * @return ID generado por MySQL, o -1 si falló.
     */
    public int insertar(CompraDTO c) throws SQLException {
        Connection con = Conexion.getInstancia().getConexion();
        try (PreparedStatement ps = con.prepareStatement(SQL_INSERTAR,
                                        Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1,  c.getClienteNombre());
            ps.setString(2,  c.getClienteApellido());
            ps.setString(3,  c.getClienteCedula());
            ps.setString(4,  c.getClienteTipo());
            ps.setString(5,  c.getProductoNombre());
            ps.setDouble(6,  c.getValorUnitario());
            ps.setInt   (7,  c.getCantidad());
            ps.setDouble(8,  c.getTotalBruto());
            ps.setDouble(9,  c.getPorcentajeDescuento());
            ps.setDouble(10, c.getValorDescuento());
            ps.setDouble(11, c.getTotalReal());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        }
        return -1;
    }
 
    // ── LISTAR / BUSCAR ───────────────────────────────────────────────────────
 
    /** Retorna todas las compras ordenadas por fecha descendente. */
    public List<CompraDTO> listarTodas() throws SQLException {
        List<CompraDTO> lista = new ArrayList<>();
        Connection con = Conexion.getInstancia().getConexion();
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(SQL_LISTAR)) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }
 
    /**
     * Busca compras por texto (nombre, apellido, cédula o producto).
     * Si el texto es nulo/vacío retorna todas.
     */
    public List<CompraDTO> buscarPorTexto(String texto) throws SQLException {
        if (texto == null || texto.trim().isEmpty()) return listarTodas();
        List<CompraDTO> lista = new ArrayList<>();
        Connection con = Conexion.getInstancia().getConexion();
        try (PreparedStatement ps = con.prepareStatement(SQL_BUSCAR_TEXTO)) {
            String like = "%" + texto.trim() + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            ps.setString(4, like);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }
 
    /**
     * Retorna todas las compras de un cliente por su cédula.
     */
    public List<CompraDTO> comprasPorCedula(String cedula) throws SQLException {
        List<CompraDTO> lista = new ArrayList<>();
        Connection con = Conexion.getInstancia().getConexion();
        try (PreparedStatement ps = con.prepareStatement(SQL_BUSCAR_POR_CEDULA)) {
            ps.setString(1, cedula);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }
 
    /**
     * Busca compras entre dos fechas (inclusive).
     * @param desde Fecha inicio (formato yyyy-MM-dd)
     * @param hasta Fecha fin    (formato yyyy-MM-dd)
     */
    public List<CompraDTO> buscarPorFecha(String desde, String hasta) throws SQLException {
        List<CompraDTO> lista = new ArrayList<>();
        Connection con = Conexion.getInstancia().getConexion();
        try (PreparedStatement ps = con.prepareStatement(SQL_BUSCAR_FECHA)) {
            ps.setString(1, desde);
            ps.setString(2, hasta);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }
 
    // ── Estadísticas ──────────────────────────────────────────────────────────
 
    /**
     * Ventas de los últimos 7 días, agrupadas por DÍA.
     * Object[]: [etiqueta "yyyy-MM-dd", num_ventas(int), total(double)]
     */
    public List<Object[]> ventasDiario() throws SQLException {
        return ejecutarEstadistica(SQL_VENTAS_DIARIO);
    }
 
    /**
     * Ventas de las últimas 4 semanas, agrupadas por SEMANA (lunes de cada semana).
     * Object[]: [etiqueta "yyyy-MM-dd", num_ventas(int), total(double)]
     */
    public List<Object[]> ventasSemanal() throws SQLException {
        return ejecutarEstadistica(SQL_VENTAS_SEMANAL);
    }
 
    /**
     * Ventas de los últimos 6 meses, agrupadas por MES.
     * Object[]: [etiqueta "yyyy-MM-01", num_ventas(int), total(double)]
     */
    public List<Object[]> ventasMensual() throws SQLException {
        return ejecutarEstadistica(SQL_VENTAS_MENSUAL);
    }
 
    private List<Object[]> ejecutarEstadistica(String sql) throws SQLException {
        List<Object[]> lista = new ArrayList<>();
        Connection con = Conexion.getInstancia().getConexion();
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getString("etiqueta"),
                    rs.getInt   ("num_ventas"),
                    rs.getDouble("total")
                });
            }
        }
        return lista;
    }
 
    /** Total de transacciones registradas. */
    public int totalVentas() throws SQLException {
        Connection con = Conexion.getInstancia().getConexion();
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(SQL_TOTAL_VENTAS)) {
            if (rs.next()) return rs.getInt("total");
        }
        return 0;
    }
 
    /** Suma de total_real de todas las compras. */
    public double totalRecaudado() throws SQLException {
        Connection con = Conexion.getInstancia().getConexion();
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(SQL_TOTAL_RECAUDADO)) {
            if (rs.next()) return rs.getDouble("total");
        }
        return 0;
    }
 
    /** Suma de descuentos aplicados. */
    public double totalDescuentos() throws SQLException {
        Connection con = Conexion.getInstancia().getConexion();
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(SQL_TOTAL_DESCUENTOS)) {
            if (rs.next()) return rs.getDouble("total");
        }
        return 0;
    }
 
    /** Nombre del producto más vendido por unidades totales. */
    public String productoTopVentas() throws SQLException {
        Connection con = Conexion.getInstancia().getConexion();
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(SQL_PRODUCTO_TOP)) {
            if (rs.next()) return rs.getString("producto_nombre");
        }
        return "—";
    }
 
    // ── Mapeo ─────────────────────────────────────────────────────────────────
 
    private CompraDTO mapear(ResultSet rs) throws SQLException {
        Timestamp ts = rs.getTimestamp("fecha_hora");
        LocalDateTime fecha = ts != null ? ts.toLocalDateTime() : LocalDateTime.now();
        return new CompraDTO(
                rs.getInt   ("id"),
                fecha,
                rs.getString("cliente_nombre"),
                rs.getString("cliente_apellido"),
                rs.getString("cliente_cedula"),
                rs.getString("cliente_tipo"),
                rs.getString("producto_nombre"),
                rs.getDouble("valor_unitario"),
                rs.getInt   ("cantidad"),
                rs.getDouble("total_bruto"),
                rs.getDouble("porcentaje_desc"),
                rs.getDouble("valor_descuento"),
                rs.getDouble("total_real")
        );
    }
}