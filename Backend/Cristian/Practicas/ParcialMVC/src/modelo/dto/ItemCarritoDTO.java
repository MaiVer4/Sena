
package modelo.dto;
 
/**
 * Representa un ítem en el carrito de compras (en memoria, antes de confirmar).
 */
public class ItemCarritoDTO {
 
    private ProductoDTO producto;
    private int         cantidad;
 
    public ItemCarritoDTO(ProductoDTO producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }
 
    public ProductoDTO getProducto()          { return producto; }
    public void        setProducto(ProductoDTO p) { this.producto = p; }
 
    public int  getCantidad()                 { return cantidad; }
    public void setCantidad(int cantidad)     { this.cantidad = cantidad; }
 
    /** Subtotal bruto de este ítem (precio × cantidad). */
    public double getSubtotal() {
        return producto.getPrecio() * cantidad;
    }
 
    public String getNombreProducto() { return producto.getNombre(); }
    public double getPrecioUnitario() { return producto.getPrecio(); }
}
 