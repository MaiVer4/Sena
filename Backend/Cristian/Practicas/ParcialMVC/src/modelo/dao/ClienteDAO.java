
package modelo.dao;
 
import modelo.conexion.Conexion;
import modelo.dto.ClienteDTO;
 
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
 
/**
 * DAO que realiza operaciones CRUD sobre la tabla {@code clientes} en MySQL.
 */
public class ClienteDAO {
 
    // ── Sentencias SQL ────────────────────────────────────────────────────────
    private static final String SQL_LISTAR =
            "SELECT id, cedula, nombre, apellido, edad, telefono, tipo_usuario " +
            "FROM clientes ORDER BY apellido, nombre";
 
    private static final String SQL_BUSCAR_ID =
            "SELECT id, cedula, nombre, apellido, edad, telefono, tipo_usuario " +
            "FROM clientes WHERE id = ?";
 
    private static final String SQL_BUSCAR_CEDULA =
            "SELECT id, cedula, nombre, apellido, edad, telefono, tipo_usuario " +
            "FROM clientes WHERE cedula = ?";
 
    private static final String SQL_BUSCAR_TEXTO =
            "SELECT id, cedula, nombre, apellido, edad, telefono, tipo_usuario " +
            "FROM clientes WHERE nombre LIKE ? OR apellido LIKE ? OR cedula LIKE ? " +
            "ORDER BY apellido, nombre";
 
    private static final String SQL_INSERTAR =
            "INSERT INTO clientes (cedula, nombre, apellido, edad, telefono, tipo_usuario) " +
            "VALUES (?,?,?,?,?,?)";
 
    private static final String SQL_ACTUALIZAR =
            "UPDATE clientes SET cedula=?, nombre=?, apellido=?, edad=?, telefono=?, tipo_usuario=? " +
            "WHERE id=?";
 
    private static final String SQL_ELIMINAR =
            "DELETE FROM clientes WHERE id=?";
 
    private static final String SQL_EXISTE_CEDULA =
            "SELECT COUNT(*) FROM clientes WHERE cedula = ? AND id <> ?";
 
    // ── LISTAR ────────────────────────────────────────────────────────────────
 
    /** Retorna todos los clientes ordenados por apellido. */
    public List<ClienteDTO> listarTodos() throws SQLException {
        List<ClienteDTO> lista = new ArrayList<>();
        Connection con = Conexion.getInstancia().getConexion();
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(SQL_LISTAR)) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }
 
    /**
     * Búsqueda parcial por nombre, apellido o cédula.
     * Si el texto está vacío retorna todos.
     */
    public List<ClienteDTO> buscarPorTexto(String texto) throws SQLException {
        if (texto == null || texto.trim().isEmpty()) return listarTodos();
        List<ClienteDTO> lista = new ArrayList<>();
        Connection con = Conexion.getInstancia().getConexion();
        try (PreparedStatement ps = con.prepareStatement(SQL_BUSCAR_TEXTO)) {
            String like = "%" + texto.trim() + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }
 
    /** Busca un cliente por su ID. Retorna null si no existe. */
    public ClienteDTO buscarPorId(int id) throws SQLException {
        Connection con = Conexion.getInstancia().getConexion();
        try (PreparedStatement ps = con.prepareStatement(SQL_BUSCAR_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }
 
    /** Busca un cliente por su cédula. Retorna null si no existe. */
    public ClienteDTO buscarPorCedula(String cedula) throws SQLException {
        Connection con = Conexion.getInstancia().getConexion();
        try (PreparedStatement ps = con.prepareStatement(SQL_BUSCAR_CEDULA)) {
            ps.setString(1, cedula);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }
 
    // ── INSERTAR ──────────────────────────────────────────────────────────────
 
    /**
     * Inserta un nuevo cliente.
     * @return ID generado por MySQL, o -1 si falló.
     */
    public int insertar(ClienteDTO c) throws SQLException {
        Connection con = Conexion.getInstancia().getConexion();
        try (PreparedStatement ps = con.prepareStatement(SQL_INSERTAR,
                                        Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getCedula());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getApellido());
            ps.setInt   (4, c.getEdad());
            ps.setString(5, c.getTelefono());
            ps.setString(6, c.getTipoUsuario());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        }
        return -1;
    }
 
    // ── ACTUALIZAR ────────────────────────────────────────────────────────────
 
    /**
     * Actualiza todos los campos de un cliente existente.
     * @return true si se actualizó al menos una fila.
     */
    public boolean actualizar(ClienteDTO c) throws SQLException {
        Connection con = Conexion.getInstancia().getConexion();
        try (PreparedStatement ps = con.prepareStatement(SQL_ACTUALIZAR)) {
            ps.setString(1, c.getCedula());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getApellido());
            ps.setInt   (4, c.getEdad());
            ps.setString(5, c.getTelefono());
            ps.setString(6, c.getTipoUsuario());
            ps.setInt   (7, c.getId());
            return ps.executeUpdate() > 0;
        }
    }
 
    // ── ELIMINAR ──────────────────────────────────────────────────────────────
 
    /**
     * Elimina un cliente por ID.
     * @return true si se eliminó correctamente.
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
     * Verifica si ya existe un cliente con esa cédula (excluyendo el ID dado).
     * Usar idExcluir=0 al insertar, idExcluir=id real al editar.
     */
    public boolean existeCedula(String cedula, int idExcluir) throws SQLException {
        Connection con = Conexion.getInstancia().getConexion();
        try (PreparedStatement ps = con.prepareStatement(SQL_EXISTE_CEDULA)) {
            ps.setString(1, cedula);
            ps.setInt   (2, idExcluir);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        }
        return false;
    }
 
    // ── Mapeo de ResultSet ────────────────────────────────────────────────────
 
    private ClienteDTO mapear(ResultSet rs) throws SQLException {
        return new ClienteDTO(
                rs.getInt   ("id"),
                rs.getString("cedula"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getInt   ("edad"),
                rs.getString("telefono"),
                rs.getString("tipo_usuario")
        );
    }
}