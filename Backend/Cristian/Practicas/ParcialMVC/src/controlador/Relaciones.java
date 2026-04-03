package controlador;
 
import modelo.Procesos;
import modelo.dao.ProductoDAO;
import modelo.dao.ClienteDAO;
import modelo.dao.CompraDAO;
import modelo.dao.VentaDAO;
import vista.VentanaInicio;
import vista.VentanaPrincipalM;
import vista.VentanaProductos;
import vista.VentanaClientes;
import vista.VentanaHistorial;
 
public class Relaciones {
 
    private Coordinador coordinador;
 
    // ── Modelo ────────────────────────────────────────────────────────────────
    private Procesos    procesos;
    private ProductoDAO productoDAO;
    private ClienteDAO  clienteDAO;
    private CompraDAO   compraDAO;
    private VentaDAO    ventaDAO;
 
    // ── Vistas ────────────────────────────────────────────────────────────────
    private VentanaInicio     ventanaInicio;
    private VentanaPrincipalM ventanaPrincipalM;
    private VentanaProductos  ventanaProductos;
    private VentanaClientes   ventanaClientes;
    private VentanaHistorial  ventanaHistorial;
 
    public Relaciones(Coordinador coordinador) {
        this.coordinador = coordinador;
    }
 
    // ── Modelo ────────────────────────────────────────────────────────────────
 
    public Procesos getProcesos() {
        if (procesos == null) procesos = new Procesos();
        return procesos;
    }
 
    public ProductoDAO getProductoDAO() {
        if (productoDAO == null) productoDAO = new ProductoDAO();
        return productoDAO;
    }
 
    public ClienteDAO getClienteDAO() {
        if (clienteDAO == null) clienteDAO = new ClienteDAO();
        return clienteDAO;
    }
 
    public CompraDAO getCompraDAO() {
        if (compraDAO == null) compraDAO = new CompraDAO();
        return compraDAO;
    }
 
    public VentaDAO getVentaDAO() {
        if (ventaDAO == null) ventaDAO = new VentaDAO();
        return ventaDAO;
    }
 
    // ── Vistas ────────────────────────────────────────────────────────────────
 
    public VentanaInicio getVentanaInicio() {
        if (ventanaInicio == null) ventanaInicio = new VentanaInicio(coordinador);
        return ventanaInicio;
    }
 
    public VentanaPrincipalM getVentanaPrincipalM() {
        if (ventanaPrincipalM == null) ventanaPrincipalM = new VentanaPrincipalM(coordinador);
        return ventanaPrincipalM;
    }
 
    public VentanaProductos getVentanaProductos() {
        if (ventanaProductos == null) ventanaProductos = new VentanaProductos(coordinador);
        return ventanaProductos;
    }
 
    public VentanaClientes getVentanaClientes() {
        if (ventanaClientes == null) ventanaClientes = new VentanaClientes(coordinador);
        return ventanaClientes;
    }
 
    public VentanaHistorial getVentanaHistorial() {
        if (ventanaHistorial == null) ventanaHistorial = new VentanaHistorial(coordinador);
        return ventanaHistorial;
    }
 
    // ── Setters ───────────────────────────────────────────────────────────────
 
    public void setVentanaInicio(VentanaInicio v)          { this.ventanaInicio = v; }
    public void setVentanaPrincipalM(VentanaPrincipalM v)  { this.ventanaPrincipalM = v; }
    public void setVentanaProductos(VentanaProductos v)    { this.ventanaProductos = v; }
    public void setVentanaClientes(VentanaClientes v)      { this.ventanaClientes = v; }
    public void setVentanaHistorial(VentanaHistorial v)    { this.ventanaHistorial = v; }
}