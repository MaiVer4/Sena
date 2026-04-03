package modelo.dao;
 
import modelo.conexion.Conexion;
import modelo.dto.ProductoDTO;
 
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
 
/**
 * DAO que realiza operaciones CRUD sobre la tabla {@code productos} en MySQL.
 */
public class ProductoDAO {
 
    // ── Sentencias SQL ────────────────────────────────────────────────────────
    private static final String SQL_LISTAR =
            "SELECT id, nombre, categoria, descripcion, precio, stock FROM productos ORDER BY id";
 
    private static final String SQL_BUSCAR_ID =
            "SELECT id, nombre, categoria, descripcion, precio, stock FROM productos WHERE id = ?";
 
    private static final String SQL_BUSCAR_NOMBRE =
            "SELECT id, nombre, categoria, descripcion, precio, stock FROM productos "
          + "WHERE nombre LIKE ? ORDER BY nombre";
 
    private static final String SQL_INSERTAR =
            "INSERT INTO productos (nombre, categoria, descripcion, precio, stock) VALUES (?,?,?,?,?)";
 
    private static final String SQL_ACTUALIZAR =
            "UPDATE productos SET nombre=?, categoria=?, descripcion=?, precio=?, stock=? WHERE id=?";
 
    private static final String SQL_ELIMINAR =
            "DELETE FROM productos WHERE id=?";
 
    private static final String SQL_EXISTE_NOMBRE =
            "SELECT COUNT(*) FROM productos WHERE nombre = ? AND id <> ?";
 
    private static final String SQL_DESCUENTO_STOCK =
            "UPDATE productos SET stock = stock - ? WHERE nombre = ? AND stock >= ?";
 
    private static final String SQL_STOCK_ACTUAL =
            "SELECT stock FROM productos WHERE nombre = ?";
 
    // ── LISTAR ────────────────────────────────────────────────────────────────
 
    /**
     * Retorna todos los productos de la tabla.
     */
    public List<ProductoDTO> listarTodos() throws SQLException {
        List<ProductoDTO> lista = new ArrayList<>();
        Connection con = Conexion.getInstancia().getConexion();
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(SQL_LISTAR)) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }
 
    /**
     * Busca productos cuyo nombre contiene el texto indicado (búsqueda parcial).
     */
    public List<ProductoDTO> buscarPorNombre(String texto) throws SQLException {
        List<ProductoDTO> lista = new ArrayList<>();
        Connection con = Conexion.getInstancia().getConexion();
        try (PreparedStatement ps = con.prepareStatement(SQL_BUSCAR_NOMBRE)) {
            ps.setString(1, "%" + texto + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }
 
    /**
     * Busca un producto por su ID.
     * @return ProductoDTO o {@code null} si no existe.
     */
    public ProductoDTO buscarPorId(int id) throws SQLException {
        Connection con = Conexion.getInstancia().getConexion();
        try (PreparedStatement ps = con.prepareStatement(SQL_BUSCAR_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }
 
    // ── INSERTAR ──────────────────────────────────────────────────────────────
 
    /**
     * Inserta un nuevo producto en la base de datos.
     * @return ID generado por MySQL, o -1 si falló.
     */
    public int insertar(ProductoDTO p) throws SQLException {
        Connection con = Conexion.getInstancia().getConexion();
        try (PreparedStatement ps = con.prepareStatement(SQL_INSERTAR,
                                        Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getCategoria());
            ps.setString(3, p.getDescripcion());
            ps.setDouble(4, p.getPrecio());
            ps.setInt   (5, p.getStock());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        }
        return -1;
    }
 
    // ── ACTUALIZAR ────────────────────────────────────────────────────────────
 
    /**
     * Actualiza todos los campos de un producto existente.
     * @return {@code true} si se actualizó al menos una fila.
     */
    public boolean actualizar(ProductoDTO p) throws SQLException {
        Connection con = Conexion.getInstancia().getConexion();
        try (PreparedStatement ps = con.prepareStatement(SQL_ACTUALIZAR)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getCategoria());
            ps.setString(3, p.getDescripcion());
            ps.setDouble(4, p.getPrecio());
            ps.setInt   (5, p.getStock());
            ps.setInt   (6, p.getId());
            return ps.executeUpdate() > 0;
        }
    }
 
    // ── ELIMINAR ──────────────────────────────────────────────────────────────
 
    /**
     * Elimina un producto por su ID.
     * @return {@code true} si se eliminó correctamente.
     */
    public boolean eliminar(int id) throws SQLException {
        Connection con = Conexion.getInstancia().getConexion();
        try (PreparedStatement ps = con.prepareStatement(SQL_ELIMINAR)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
 
    // ── VALIDACIÓN ────────────────────────────────────────────────────────────
 
    /**
     * Verifica si ya existe un producto con ese nombre (distinto del ID indicado).
     * Útil para evitar duplicados al insertar (id=0) o editar (id real).
     */
    public boolean existeNombre(String nombre, int idExcluir) throws SQLException {
        Connection con = Conexion.getInstancia().getConexion();
        try (PreparedStatement ps = con.prepareStatement(SQL_EXISTE_NOMBRE)) {
            ps.setString(1, nombre);
            ps.setInt   (2, idExcluir);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        }
        return false;
    }
 
    // ── STOCK ─────────────────────────────────────────────────────────────────
 
    /**
     * Descuenta la cantidad del stock del producto cuyo nombre coincide.
     * Solo ejecuta si el stock actual es suficiente.
     * @return true si se descontó correctamente, false si no había stock suficiente
     *         o el producto no existe.
     */
    public boolean descontarStock(String nombreProducto, int cantidad) throws SQLException {
        Connection con = Conexion.getInstancia().getConexion();
 
        // Primero verificar stock disponible
        try (PreparedStatement ps = con.prepareStatement(SQL_STOCK_ACTUAL)) {
            ps.setString(1, nombreProducto);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return false;           // producto no encontrado
                int stockActual = rs.getInt("stock");
                if (stockActual < cantidad) return false; // stock insuficiente
            }
        }
 
        // Descontar
        try (PreparedStatement ps = con.prepareStatement(SQL_DESCUENTO_STOCK)) {
            ps.setInt   (1, cantidad);
            ps.setString(2, nombreProducto);
            ps.setInt   (3, cantidad);
            return ps.executeUpdate() > 0;
        }
    }
 
    // ── Mapeo de ResultSet ────────────────────────────────────────────────────
 
    private ProductoDTO mapear(ResultSet rs) throws SQLException {
        return new ProductoDTO(
                rs.getInt   ("id"),
                rs.getString("nombre"),
                rs.getString("categoria"),
                rs.getString("descripcion"),
                rs.getDouble("precio"),
                rs.getInt   ("stock")
        );
    }
}