
package modelo.dao;
 
import modelo.conexion.Conexion;
import modelo.dto.CompraDTO;
import modelo.dto.ItemCarritoDTO;
 
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
 
/**
 * DAO para las tablas {@code ventas} y {@code venta_items}.
 * Reemplaza a CompraDAO para nuevas operaciones multi-producto.
 */
public class VentaDAO {
 
    // ── INSERT cabecera ───────────────────────────────────────────────────────
    private static final String SQL_INSERTAR_VENTA =
            "INSERT INTO ventas (cliente_nombre, cliente_apellido, cliente_cedula, " +
            "cliente_tipo, total_bruto, porcentaje_desc, valor_descuento, total_real) " +
            "VALUES (?,?,?,?,?,?,?,?)";
 
    // ── INSERT ítem ───────────────────────────────────────────────────────────
    private static final String SQL_INSERTAR_ITEM =
            "INSERT INTO venta_items (venta_id, producto_nombre, valor_unitario, cantidad, subtotal) " +
            "VALUES (?,?,?,?,?)";
 
    // ── LISTAR todas las ventas con sus ítems (JOIN) ──────────────────────────
    private static final String SQL_LISTAR =
            "SELECT v.id, v.fecha_hora, v.cliente_nombre, v.cliente_apellido, " +
            "v.cliente_cedula, v.cliente_tipo, " +
            "vi.producto_nombre, vi.valor_unitario, vi.cantidad, vi.subtotal, " +
            "v.total_bruto, v.porcentaje_desc, v.valor_descuento, v.total_real " +
            "FROM ventas v JOIN venta_items vi ON v.id = vi.venta_id " +
            "ORDER BY v.fecha_hora DESC, v.id DESC, vi.id ASC";
 
    // ── BUSCAR por texto ──────────────────────────────────────────────────────
    private static final String SQL_BUSCAR_TEXTO =
            "SELECT v.id, v.fecha_hora, v.cliente_nombre, v.cliente_apellido, " +
            "v.cliente_cedula, v.cliente_tipo, " +
            "vi.producto_nombre, vi.valor_unitario, vi.cantidad, vi.subtotal, " +
            "v.total_bruto, v.porcentaje_desc, v.valor_descuento, v.total_real " +
            "FROM ventas v JOIN venta_items vi ON v.id = vi.venta_id " +
            "WHERE v.cliente_nombre LIKE ? OR v.cliente_apellido LIKE ? " +
            "   OR v.cliente_cedula LIKE ? OR vi.producto_nombre LIKE ? " +
            "ORDER BY v.fecha_hora DESC, v.id DESC, vi.id ASC";
 
    // ── BUSCAR por cédula ─────────────────────────────────────────────────────
    private static final String SQL_BUSCAR_CEDULA =
            "SELECT v.id, v.fecha_hora, v.cliente_nombre, v.cliente_apellido, " +
            "v.cliente_cedula, v.cliente_tipo, " +
            "vi.producto_nombre, vi.valor_unitario, vi.cantidad, vi.subtotal, " +
            "v.total_bruto, v.porcentaje_desc, v.valor_descuento, v.total_real " +
            "FROM ventas v JOIN venta_items vi ON v.id = vi.venta_id " +
            "WHERE v.cliente_cedula = ? " +
            "ORDER BY v.fecha_hora DESC, v.id DESC, vi.id ASC";
 
    // ── Estadísticas ──────────────────────────────────────────────────────────
    private static final String SQL_TOTAL_VENTAS =
            "SELECT COUNT(*) AS total FROM ventas";
    private static final String SQL_TOTAL_RECAUDADO =
            "SELECT COALESCE(SUM(total_real), 0) AS total FROM ventas";
    private static final String SQL_TOTAL_DESCUENTOS =
            "SELECT COALESCE(SUM(valor_descuento), 0) AS total FROM ventas";
    private static final String SQL_PRODUCTO_TOP =
            "SELECT producto_nombre, SUM(cantidad) AS unidades " +
            "FROM venta_items GROUP BY producto_nombre ORDER BY unidades DESC LIMIT 1";
    private static final String SQL_VENTAS_DIARIO =
            "SELECT DATE(fecha_hora) AS etiqueta, COUNT(*) AS num_ventas, SUM(total_real) AS total " +
            "FROM ventas WHERE fecha_hora >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) " +
            "GROUP BY DATE(fecha_hora) ORDER BY etiqueta ASC";
    private static final String SQL_VENTAS_SEMANAL =
            "SELECT DATE(DATE_SUB(fecha_hora, INTERVAL WEEKDAY(fecha_hora) DAY)) AS etiqueta, " +
            "COUNT(*) AS num_ventas, SUM(total_real) AS total FROM ventas " +
            "WHERE fecha_hora >= DATE_SUB(CURDATE(), INTERVAL 28 DAY) " +
            "GROUP BY etiqueta ORDER BY etiqueta ASC";
    private static final String SQL_VENTAS_MENSUAL =
            "SELECT DATE_FORMAT(fecha_hora, '%Y-%m-01') AS etiqueta, " +
            "COUNT(*) AS num_ventas, SUM(total_real) AS total FROM ventas " +
            "WHERE fecha_hora >= DATE_SUB(CURDATE(), INTERVAL 6 MONTH) " +
            "GROUP BY etiqueta ORDER BY etiqueta ASC";
 
    // ═════════════════════════════════════════════════════════════════════════
    // INSERTAR venta completa (cabecera + ítems) en una transacción
    // ═════════════════════════════════════════════════════════════════════════
 
    /**
     * Guarda una venta completa con todos sus ítems.
     * Usa una transacción: si algo falla, hace rollback.
     * @return ID de la venta generada, o -1 si falló.
     */
    public int insertarVenta(String clienteNombre, String clienteApellido,
                             String clienteCedula, String clienteTipo,
                             List<ItemCarritoDTO> items,
                             double totalBruto, double porcDesc,
                             double valorDesc, double totalReal) throws SQLException {
 
        Connection con = Conexion.getInstancia().getConexion();
        boolean autoCommit = con.getAutoCommit();
        con.setAutoCommit(false);
        try {
            // 1. Insertar cabecera
            int ventaId;
            try (PreparedStatement ps = con.prepareStatement(
                    SQL_INSERTAR_VENTA, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, clienteNombre);
                ps.setString(2, clienteApellido);
                ps.setString(3, clienteCedula);
                ps.setString(4, clienteTipo);
                ps.setDouble(5, totalBruto);
                ps.setDouble(6, porcDesc);
                ps.setDouble(7, valorDesc);
                ps.setDouble(8, totalReal);
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (!keys.next()) { con.rollback(); return -1; }
                    ventaId = keys.getInt(1);
                }
            }
 
            // 2. Insertar ítems
            try (PreparedStatement ps = con.prepareStatement(SQL_INSERTAR_ITEM)) {
                for (ItemCarritoDTO item : items) {
                    ps.setInt   (1, ventaId);
                    ps.setString(2, item.getNombreProducto());
                    ps.setDouble(3, item.getPrecioUnitario());
                    ps.setInt   (4, item.getCantidad());
                    ps.setDouble(5, item.getSubtotal());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
 
            con.commit();
            return ventaId;
 
        } catch (SQLException ex) {
            con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(autoCommit);
        }
    }
 
    // ═════════════════════════════════════════════════════════════════════════
    // LISTAR / BUSCAR
    // ═════════════════════════════════════════════════════════════════════════
 
    /** Todas las ventas con sus ítems (una fila por ítem). */
    public List<CompraDTO> listarTodas() throws SQLException {
        return ejecutarQuery(SQL_LISTAR, null);
    }
 
    /** Busca por texto en nombre, apellido, cédula o producto. */
    public List<CompraDTO> buscarPorTexto(String texto) throws SQLException {
        if (texto == null || texto.trim().isEmpty()) return listarTodas();
        String like = "%" + texto.trim() + "%";
        Connection con = Conexion.getInstancia().getConexion();
        List<CompraDTO> lista = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(SQL_BUSCAR_TEXTO)) {
            ps.setString(1, like); ps.setString(2, like);
            ps.setString(3, like); ps.setString(4, like);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }
 
    /** Todas las ventas de un cliente por cédula. */
    public List<CompraDTO> buscarPorCedula(String cedula) throws SQLException {
        Connection con = Conexion.getInstancia().getConexion();
        List<CompraDTO> lista = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(SQL_BUSCAR_CEDULA)) {
            ps.setString(1, cedula);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }
 
    // ═════════════════════════════════════════════════════════════════════════
    // ESTADÍSTICAS
    // ═════════════════════════════════════════════════════════════════════════
 
    public int    totalVentas()      throws SQLException { return queryInt(SQL_TOTAL_VENTAS,     "total"); }
    public double totalRecaudado()   throws SQLException { return queryDouble(SQL_TOTAL_RECAUDADO, "total"); }
    public double totalDescuentos()  throws SQLException { return queryDouble(SQL_TOTAL_DESCUENTOS,"total"); }
 
    public String productoTopVentas() throws SQLException {
        Connection con = Conexion.getInstancia().getConexion();
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(SQL_PRODUCTO_TOP)) {
            if (rs.next()) return rs.getString("producto_nombre");
        }
        return "—";
    }
 
    public List<Object[]> ventasDiario()  throws SQLException { return ejecutarEstadistica(SQL_VENTAS_DIARIO);  }
    public List<Object[]> ventasSemanal() throws SQLException { return ejecutarEstadistica(SQL_VENTAS_SEMANAL); }
    public List<Object[]> ventasMensual() throws SQLException { return ejecutarEstadistica(SQL_VENTAS_MENSUAL); }
 
    // ═════════════════════════════════════════════════════════════════════════
    // HELPERS PRIVADOS
    // ═════════════════════════════════════════════════════════════════════════
 
    private List<CompraDTO> ejecutarQuery(String sql, PreparedStatement ps) throws SQLException {
        List<CompraDTO> lista = new ArrayList<>();
        Connection con = Conexion.getInstancia().getConexion();
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }
 
    private List<Object[]> ejecutarEstadistica(String sql) throws SQLException {
        List<Object[]> lista = new ArrayList<>();
        Connection con = Conexion.getInstancia().getConexion();
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(new Object[]{
                rs.getString("etiqueta"),
                rs.getInt("num_ventas"),
                rs.getDouble("total")
            });
        }
        return lista;
    }
 
    private int queryInt(String sql, String col) throws SQLException {
        Connection con = Conexion.getInstancia().getConexion();
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(col);
        }
        return 0;
    }
 
    private double queryDouble(String sql, String col) throws SQLException {
        Connection con = Conexion.getInstancia().getConexion();
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getDouble(col);
        }
        return 0;
    }
 
    private CompraDTO mapear(ResultSet rs) throws SQLException {
        Timestamp ts = rs.getTimestamp("fecha_hora");
        LocalDateTime fecha = ts != null ? ts.toLocalDateTime() : LocalDateTime.now();
        // Reutilizamos CompraDTO mapeando subtotal del ítem como total_bruto del ítem
        CompraDTO dto = new CompraDTO(
                rs.getInt   ("id"),
                fecha,
                rs.getString("cliente_nombre"),
                rs.getString("cliente_apellido"),
                rs.getString("cliente_cedula"),
                rs.getString("cliente_tipo"),
                rs.getString("producto_nombre"),
                rs.getDouble("valor_unitario"),
                rs.getInt   ("cantidad"),
                rs.getDouble("subtotal"),         // subtotal del ítem
                rs.getDouble("porcentaje_desc"),
                rs.getDouble("valor_descuento"),
                rs.getDouble("total_real")
        );
        return dto;
    }
}
 