package modelo;
 
import modelo.dto.UsuarioDTO;
 
public class Procesos {
 
    // Constantes de descuento por tipo de usuario
    public static final double DESCUENTO_A = 0.40;
    public static final double DESCUENTO_B = 0.20;
    public static final double DESCUENTO_C = 0.10;
    public static final double SIN_DESCUENTO = 0.0;
 
    /**
     * Calcula el porcentaje de descuento según el tipo de usuario
     * @param tipoUsuario Tipo de usuario (A, B, C o Sin tipo)
     * @return porcentaje de descuento como decimal
     */
    public double calcularDescuento(String tipoUsuario) {
        if (tipoUsuario == null || tipoUsuario.equalsIgnoreCase("Sin tipo")) {
            return SIN_DESCUENTO;
        }
        switch (tipoUsuario.toUpperCase()) {
            case "A": return DESCUENTO_A;
            case "B": return DESCUENTO_B;
            case "C": return DESCUENTO_C;
            default:  return SIN_DESCUENTO;
        }
    }
 
    /**
     * Calcula el total de la compra antes de descuento
     * @param valorUnitario Precio unitario del producto
     * @param cantidad Cantidad de productos
     * @return Total bruto de la compra
     */
    public double calcularTotalBruto(double valorUnitario, int cantidad) {
        return valorUnitario * cantidad;
    }
 
    /**
     * Calcula el valor del descuento en dinero
     * @param totalBruto Total antes de descuento
     * @param porcentajeDescuento Porcentaje de descuento (decimal)
     * @return Valor monetario del descuento
     */
    public double calcularValorDescuento(double totalBruto, double porcentajeDescuento) {
        return totalBruto * porcentajeDescuento;
    }
 
    /**
     * Calcula el total real a pagar después del descuento
     * @param totalBruto Total antes de descuento
     * @param valorDescuento Valor monetario del descuento
     * @return Total real a pagar
     */
    public double calcularTotalReal(double totalBruto, double valorDescuento) {
        return totalBruto - valorDescuento;
    }
 
    /**
     * Valida que los campos básicos del usuario no estén vacíos
     * @param usuario DTO con los datos del usuario
     * @return true si todos los campos requeridos tienen datos
     */
    public boolean validarDatosUsuario(UsuarioDTO usuario) {
        if (usuario == null) return false;
        return usuario.getNombre() != null && !usuario.getNombre().trim().isEmpty()
            && usuario.getApellido() != null && !usuario.getApellido().trim().isEmpty()
            && usuario.getTelefono() != null && !usuario.getTelefono().trim().isEmpty()
            && usuario.getCedula() != null && !usuario.getCedula().trim().isEmpty();
    }
 
    /**
     * Valida los datos del producto para la compra
     * @param nombreProducto Nombre del producto
     * @param valorUnitario Valor unitario como texto
     * @param cantidad Cantidad como texto
     * @return true si los datos son válidos
     */
    public boolean validarDatosProducto(String nombreProducto, String valorUnitario, String cantidad) {
        if (nombreProducto == null || nombreProducto.trim().isEmpty()) return false;
        try {
            double valor = Double.parseDouble(valorUnitario);
            int cant = Integer.parseInt(cantidad);
            return valor > 0 && cant > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
 
    /**
     * Obtiene la descripción del tipo de usuario para mostrar en interfaz
     * @param tipoUsuario Tipo de usuario
     * @return Descripción del tipo y su descuento
     */
    public String obtenerDescripcionTipo(String tipoUsuario) {
        if (tipoUsuario == null || tipoUsuario.equalsIgnoreCase("Sin tipo")) {
            return "Sin tipo – Sin descuento";
        }
        switch (tipoUsuario.toUpperCase()) {
            case "A": return "Tipo A – 40% de descuento";
            case "B": return "Tipo B – 20% de descuento";
            case "C": return "Tipo C – 10% de descuento";
            default:  return "Sin tipo – Sin descuento";
        }
    }
}