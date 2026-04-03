
package modelo.dto;
 
/**
 * DTO que representa un producto del catálogo de DON APARATO.
 */
public class ProductoDTO {
 
    private int    id;
    private String nombre;
    private String categoria;
    private String descripcion;
    private double precio;
    private int    stock;
 
    // ── Constructores ─────────────────────────────────────────────────────────
 
    public ProductoDTO() {}
 
    /** Constructor completo (con ID – usado al leer desde BD). */
    public ProductoDTO(int id, String nombre, String categoria,
                       String descripcion, double precio, int stock) {
        this.id          = id;
        this.nombre      = nombre;
        this.categoria   = categoria;
        this.descripcion = descripcion;
        this.precio      = precio;
        this.stock       = stock;
    }
 
    /** Constructor sin ID (usado al insertar nuevo producto). */
    public ProductoDTO(String nombre, String categoria,
                       String descripcion, double precio, int stock) {
        this(0, nombre, categoria, descripcion, precio, stock);
    }
 
    // ── Getters & Setters ─────────────────────────────────────────────────────
 
    public int    getId()          { return id; }
    public void   setId(int id)    { this.id = id; }
 
    public String getNombre()                  { return nombre; }
    public void   setNombre(String nombre)     { this.nombre = nombre; }
 
    public String getCategoria()               { return categoria; }
    public void   setCategoria(String cat)     { this.categoria = cat; }
 
    public String getDescripcion()             { return descripcion; }
    public void   setDescripcion(String desc)  { this.descripcion = desc; }
 
    public double getPrecio()                  { return precio; }
    public void   setPrecio(double precio)     { this.precio = precio; }
 
    public int    getStock()                   { return stock; }
    public void   setStock(int stock)          { this.stock = stock; }
 
    @Override
    public String toString() {
        return "ProductoDTO{id=" + id + ", nombre='" + nombre + '\''
             + ", categoria='" + categoria + '\'' + ", precio=" + precio
             + ", stock=" + stock + '}';
    }
}