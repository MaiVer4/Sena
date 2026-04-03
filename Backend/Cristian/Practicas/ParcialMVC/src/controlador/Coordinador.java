package controlador;
 
import modelo.Procesos;
import modelo.dao.ProductoDAO;
import modelo.dao.ClienteDAO;
import modelo.dao.CompraDAO;
import modelo.dao.VentaDAO;
import modelo.dto.UsuarioDTO;
import modelo.dto.ProductoDTO;
import modelo.dto.ClienteDTO;
import modelo.dto.CompraDTO;
import modelo.dto.ItemCarritoDTO;
 
import javax.swing.*;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
 
public class Coordinador {
 
    private Relaciones relaciones;
 
    public Coordinador() {
        relaciones = new Relaciones(this);
    }
 
    // ─── Arranque ─────────────────────────────────────────────────────────────
 
    /**
     * Muestra la ventana de inicio al arrancar la aplicación
     */
    public void iniciarAplicacion() {
        relaciones.getVentanaInicio().setVisible(true);
    }
 
    /**
     * Navega desde la pantalla de inicio a la pantalla principal de ventas
     */
    public void abrirVentanaPrincipal() {
        relaciones.getVentanaInicio().setVisible(false);
        relaciones.getVentanaPrincipalM().setVisible(true);
    }
 
    /**
     * Vuelve al menú principal (VentanaInicio) desde cualquier módulo
     */
    public void volverAlInicio() {
        relaciones.getVentanaPrincipalM().setVisible(false);
        relaciones.getVentanaInicio().setVisible(true);
    }
 
    /**
     * Abre el módulo de Productos desde el menú principal
     */
    public void abrirVentanaProductos() {
        relaciones.getVentanaInicio().setVisible(false);
        relaciones.getVentanaProductos().setVisible(true);
    }
 
    /**
     * Vuelve al menú principal desde el módulo de Productos
     */
    public void volverDesdeProductos() {
        relaciones.getVentanaProductos().setVisible(false);
        relaciones.getVentanaInicio().setVisible(true);
    }
 
    /**
     * Abre el módulo de Clientes desde el menú principal
     */
    public void abrirVentanaClientes() {
        relaciones.getVentanaInicio().setVisible(false);
        relaciones.getVentanaClientes().setVisible(true);
    }
 
    /**
     * Vuelve al menú principal desde el módulo de Clientes
     */
    public void volverDesdeClientes() {
        relaciones.getVentanaClientes().setVisible(false);
        relaciones.getVentanaInicio().setVisible(true);
    }
 
    /**
     * Abre el módulo de Historial de Compras desde el menú principal
     */
    public void abrirVentanaHistorial() {
        relaciones.getVentanaInicio().setVisible(false);
        relaciones.getVentanaHistorial().cargarTabla(null);
        relaciones.getVentanaHistorial().setVisible(true);
    }
 
    /**
     * Vuelve al menú principal desde el historial
     */
    public void volverDesdeHistorial() {
        relaciones.getVentanaHistorial().setVisible(false);
        relaciones.getVentanaInicio().setVisible(true);
    }
 
    // ─── Clientes (CRUD) ──────────────────────────────────────────────────────
 
    /** Retorna lista de clientes; si filtro es null/vacío trae todos. */
    public List<ClienteDTO> listarClientes(String filtro) throws SQLException {
        return relaciones.getClienteDAO().buscarPorTexto(filtro);
    }
 
    /** Inserta un nuevo cliente validando cédula duplicada. */
    public void agregarCliente(ClienteDTO c) throws Exception {
        ClienteDAO dao = relaciones.getClienteDAO();
        if (dao.existeCedula(c.getCedula(), 0)) {
            throw new IllegalArgumentException(
                "Ya existe un cliente con la cédula \"" + c.getCedula() + "\".");
        }
        dao.insertar(c);
    }
 
    /** Actualiza un cliente existente validando cédula duplicada. */
    public void editarCliente(ClienteDTO c) throws Exception {
        ClienteDAO dao = relaciones.getClienteDAO();
        if (dao.existeCedula(c.getCedula(), c.getId())) {
            throw new IllegalArgumentException(
                "Ya existe otro cliente con la cédula \"" + c.getCedula() + "\".");
        }
        dao.actualizar(c);
    }
 
    /** Elimina un cliente por ID. */
    public void eliminarCliente(int id) throws SQLException {
        relaciones.getClienteDAO().eliminar(id);
    }
 
    // ─── Productos (CRUD) ─────────────────────────────────────────────────────
 
    /**
     * Retorna lista de productos; si filtro es null/vacío trae todos.
     */
    public List<ProductoDTO> listarProductos(String filtro) throws SQLException {
        ProductoDAO dao = relaciones.getProductoDAO();
        if (filtro == null || filtro.isEmpty()) return dao.listarTodos();
        return dao.buscarPorNombre(filtro);
    }
 
    /**
     * Inserta un nuevo producto validando nombre duplicado.
     */
    public void agregarProducto(ProductoDTO p) throws SQLException {
        ProductoDAO dao = relaciones.getProductoDAO();
        if (dao.existeNombre(p.getNombre(), 0)) {
            throw new IllegalArgumentException(
                "Ya existe un producto con el nombre \"" + p.getNombre() + "\".");
        }
        dao.insertar(p);
    }
 
    /**
     * Actualiza un producto existente validando nombre duplicado.
     */
    public void editarProducto(ProductoDTO p) throws SQLException {
        ProductoDAO dao = relaciones.getProductoDAO();
        if (dao.existeNombre(p.getNombre(), p.getId())) {
            throw new IllegalArgumentException(
                "Ya existe otro producto con el nombre \"" + p.getNombre() + "\".");
        }
        dao.actualizar(p);
    }
 
    /**
     * Elimina un producto por ID.
     */
    public void eliminarProducto(int id) throws SQLException {
        relaciones.getProductoDAO().eliminar(id);
    }
 
    // ─── Lógica de negocio ────────────────────────────────────────────────────
 
    /**
     * Procesa una venta con múltiples productos.
     * Valida al cliente, descuenta stock de cada ítem, guarda en BD y retorna el resultado.
     *
     * @param nombre    Nombre del cliente
     * @param apellido  Apellido
     * @param edadStr   Edad como texto
     * @param telefono  Teléfono
     * @param cedula    Cédula
     * @param tipo      Tipo de usuario (A, B, C o Sin tipo)
     * @param items     Lista de ítems del carrito
     * @return ResultadoVenta con totales calculados, o null si hubo error
     */
    public ResultadoVenta realizarVenta(String nombre, String apellido, String edadStr,
                                        String telefono, String cedula, String tipo,
                                        List<ItemCarritoDTO> items) {
        Procesos procesos = relaciones.getProcesos();
 
        // Validar datos del cliente
        if (nombre.trim().isEmpty() || apellido.trim().isEmpty()
                || telefono.trim().isEmpty() || cedula.trim().isEmpty()) {
            mostrarError("Por favor complete todos los datos del cliente.");
            return null;
        }
        int edad;
        try {
            edad = Integer.parseInt(edadStr.trim());
            if (edad <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            mostrarError("La edad debe ser un número entero positivo.");
            return null;
        }
        if (items == null || items.isEmpty()) {
            mostrarError("El carrito está vacío. Agrega al menos un producto.");
            return null;
        }
 
        // Registrar cliente si no existe
        try {
            ClienteDAO clienteDAO = relaciones.getClienteDAO();
            if (clienteDAO.buscarPorCedula(cedula.trim()) == null) {
                clienteDAO.insertar(new ClienteDTO(
                        cedula.trim(), nombre.trim(), apellido.trim(),
                        edad, telefono.trim(), tipo));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Advertencia: no se pudo registrar el cliente:\n" + ex.getMessage(),
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
 
        // Descontar stock de cada ítem
        for (ItemCarritoDTO item : items) {
            try {
                relaciones.getProductoDAO().descontarStock(
                        item.getNombreProducto(), item.getCantidad());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,
                        "Error al descontar stock de \"" + item.getNombreProducto() + "\":\n"
                        + ex.getMessage(), "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        }
 
        // Calcular totales
        double totalBruto    = items.stream().mapToDouble(ItemCarritoDTO::getSubtotal).sum();
        double porcDesc      = procesos.calcularDescuento(tipo);
        double valorDesc     = procesos.calcularValorDescuento(totalBruto, porcDesc);
        double totalReal     = procesos.calcularTotalReal(totalBruto, valorDesc);
 
        // Guardar en BD con la nueva estructura ventas + venta_items
        try {
            relaciones.getVentaDAO().insertarVenta(
                    nombre.trim(), apellido.trim(), cedula.trim(), tipo,
                    items, totalBruto, porcDesc, valorDesc, totalReal);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "La venta se procesó pero no se guardó en historial:\n" + ex.getMessage(),
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
 
        UsuarioDTO usuario = new UsuarioDTO(nombre.trim(), apellido.trim(), edad,
                telefono.trim(), cedula.trim(), tipo);
        return new ResultadoVenta(usuario, items, totalBruto, porcDesc, valorDesc, totalReal);
    }
 
    // ─── Compras / Ventas ────────────────────────────────────────────────────
 
    /** Lista ítems de ventas; si filtro es null/vacío trae todos. */
    public List<CompraDTO> listarCompras(String filtro) throws SQLException {
        return relaciones.getVentaDAO().buscarPorTexto(filtro);
    }
 
    /** Retorna todos los ítems de ventas de un cliente por cédula. */
    public List<CompraDTO> comprasPorCedula(String cedula) throws SQLException {
        return relaciones.getVentaDAO().buscarPorCedula(cedula);
    }
 
    // ─── Estadísticas para el dashboard ──────────────────────────────────────
 
    public List<Object[]> ventasDiario()  throws SQLException { return relaciones.getVentaDAO().ventasDiario();  }
    public List<Object[]> ventasSemanal() throws SQLException { return relaciones.getVentaDAO().ventasSemanal(); }
    public List<Object[]> ventasMensual() throws SQLException { return relaciones.getVentaDAO().ventasMensual(); }
    public int    totalVentas()     throws SQLException { return relaciones.getVentaDAO().totalVentas();     }
    public double totalRecaudado()  throws SQLException { return relaciones.getVentaDAO().totalRecaudado();  }
    public double totalDescuentos() throws SQLException { return relaciones.getVentaDAO().totalDescuentos(); }
    public String productoTopVentas() throws SQLException { return relaciones.getVentaDAO().productoTopVentas(); }
    public int totalClientes()  throws SQLException { return relaciones.getClienteDAO().listarTodos().size(); }
    public int totalProductos() throws SQLException { return relaciones.getProductoDAO().listarTodos().size(); }
 
    /** Expone Procesos para cálculos en la vista (preview del carrito). */
    public modelo.Procesos getProcesos() { return relaciones.getProcesos(); }
 
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
 
    public static String formatearMoneda(double valor) {
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("es", "CO"));
        nf.setMinimumFractionDigits(0);
        nf.setMaximumFractionDigits(2);
        return "$ " + nf.format(valor);
    }
 
    // ─── Clase resultado de venta ─────────────────────────────────────────────
 
    public static class ResultadoVenta {
        public final UsuarioDTO          usuario;
        public final List<ItemCarritoDTO> items;
        public final double totalBruto;
        public final double porcentajeDescuento;
        public final double valorDescuento;
        public final double totalReal;
 
        public ResultadoVenta(UsuarioDTO usuario, List<ItemCarritoDTO> items,
                              double totalBruto, double porcentajeDescuento,
                              double valorDescuento, double totalReal) {
            this.usuario             = usuario;
            this.items               = items;
            this.totalBruto          = totalBruto;
            this.porcentajeDescuento = porcentajeDescuento;
            this.valorDescuento      = valorDescuento;
            this.totalReal           = totalReal;
        }
 
        public boolean tieneDescuento()    { return porcentajeDescuento > 0; }
        public int     getPorcentajeEntero() { return (int)(porcentajeDescuento * 100); }
        public int     totalItems()        { return items.size(); }
    }
}