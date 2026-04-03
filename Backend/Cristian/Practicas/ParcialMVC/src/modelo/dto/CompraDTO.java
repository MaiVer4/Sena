package modelo.dto;
 
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
 
/**
 * DTO que representa una compra registrada en el historial.
 */
public class CompraDTO {
 
    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
 
    private int           id;
    private LocalDateTime fechaHora;
    private String        clienteNombre;
    private String        clienteApellido;
    private String        clienteCedula;
    private String        clienteTipo;
    private String        productoNombre;
    private double        valorUnitario;
    private int           cantidad;
    private double        totalBruto;
    private double        porcentajeDescuento;
    private double        valorDescuento;
    private double        totalReal;
 
    // ── Constructores ─────────────────────────────────────────────────────────
 
    public CompraDTO() {}
 
    /** Constructor completo – usado al leer desde BD. */
    public CompraDTO(int id, LocalDateTime fechaHora,
                     String clienteNombre, String clienteApellido,
                     String clienteCedula, String clienteTipo,
                     String productoNombre, double valorUnitario, int cantidad,
                     double totalBruto, double porcentajeDescuento,
                     double valorDescuento, double totalReal) {
        this.id                  = id;
        this.fechaHora           = fechaHora;
        this.clienteNombre       = clienteNombre;
        this.clienteApellido     = clienteApellido;
        this.clienteCedula       = clienteCedula;
        this.clienteTipo         = clienteTipo;
        this.productoNombre      = productoNombre;
        this.valorUnitario       = valorUnitario;
        this.cantidad            = cantidad;
        this.totalBruto          = totalBruto;
        this.porcentajeDescuento = porcentajeDescuento;
        this.valorDescuento      = valorDescuento;
        this.totalReal           = totalReal;
    }
 
    /** Constructor sin ID ni fecha (para insertar nueva compra). */
    public CompraDTO(String clienteNombre, String clienteApellido,
                     String clienteCedula, String clienteTipo,
                     String productoNombre, double valorUnitario, int cantidad,
                     double totalBruto, double porcentajeDescuento,
                     double valorDescuento, double totalReal) {
        this(0, LocalDateTime.now(), clienteNombre, clienteApellido,
             clienteCedula, clienteTipo, productoNombre, valorUnitario,
             cantidad, totalBruto, porcentajeDescuento, valorDescuento, totalReal);
    }
 
    // ── Getters & Setters ─────────────────────────────────────────────────────
 
    public int           getId()                     { return id; }
    public void          setId(int id)               { this.id = id; }
 
    public LocalDateTime getFechaHora()              { return fechaHora; }
    public void          setFechaHora(LocalDateTime f){ this.fechaHora = f; }
    public String        getFechaHoraStr()           {
        return fechaHora != null ? fechaHora.format(FMT) : "—";
    }
 
    public String  getClienteNombre()                      { return clienteNombre; }
    public void    setClienteNombre(String v)              { this.clienteNombre = v; }
 
    public String  getClienteApellido()                    { return clienteApellido; }
    public void    setClienteApellido(String v)            { this.clienteApellido = v; }
 
    public String  getClienteNombreCompleto()              {
        return clienteNombre + " " + clienteApellido;
    }
 
    public String  getClienteCedula()                      { return clienteCedula; }
    public void    setClienteCedula(String v)              { this.clienteCedula = v; }
 
    public String  getClienteTipo()                        { return clienteTipo; }
    public void    setClienteTipo(String v)                { this.clienteTipo = v; }
 
    public String  getProductoNombre()                     { return productoNombre; }
    public void    setProductoNombre(String v)             { this.productoNombre = v; }
 
    public double  getValorUnitario()                      { return valorUnitario; }
    public void    setValorUnitario(double v)              { this.valorUnitario = v; }
 
    public int     getCantidad()                           { return cantidad; }
    public void    setCantidad(int v)                      { this.cantidad = v; }
 
    public double  getTotalBruto()                         { return totalBruto; }
    public void    setTotalBruto(double v)                 { this.totalBruto = v; }
 
    public double  getPorcentajeDescuento()                { return porcentajeDescuento; }
    public void    setPorcentajeDescuento(double v)        { this.porcentajeDescuento = v; }
    public int     getPorcentajeDescuentoEntero()          { return (int)(porcentajeDescuento * 100); }
 
    public double  getValorDescuento()                     { return valorDescuento; }
    public void    setValorDescuento(double v)             { this.valorDescuento = v; }
 
    public double  getTotalReal()                          { return totalReal; }
    public void    setTotalReal(double v)                  { this.totalReal = v; }
 
    public boolean tieneDescuento()                        { return porcentajeDescuento > 0; }
}