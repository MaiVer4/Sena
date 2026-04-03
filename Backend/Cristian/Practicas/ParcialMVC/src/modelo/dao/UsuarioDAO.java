
package modelo.dao;
 
import modelo.dto.UsuarioDTO;
 
public class UsuarioDAO {
 
    // Almacenamiento en memoria del usuario actual
    private UsuarioDTO usuarioActual;
 
    /**
     * Guarda o actualiza el usuario actual en memoria
     * @param usuario DTO con los datos del usuario
     */
    public void guardarUsuario(UsuarioDTO usuario) {
        this.usuarioActual = usuario;
    }
 
    /**
     * Obtiene el usuario actualmente almacenado
     * @return UsuarioDTO con los datos del usuario, o null si no hay usuario
     */
    public UsuarioDTO obtenerUsuario() {
        return usuarioActual;
    }
 
    /**
     * Verifica si hay un usuario guardado en memoria
     * @return true si existe un usuario almacenado
     */
    public boolean existeUsuario() {
        return usuarioActual != null;
    }
 
    /**
     * Elimina el usuario actual de la memoria
     */
    public void limpiarUsuario() {
        this.usuarioActual = null;
    }
 
    /**
     * Busca un usuario por su cédula (en este caso en memoria)
     * @param cedula Cédula a buscar
     * @return UsuarioDTO si coincide, null si no
     */
    public UsuarioDTO buscarPorCedula(String cedula) {
        if (usuarioActual != null && usuarioActual.getCedula().equals(cedula)) {
            return usuarioActual;
        }
        return null;
    }
}