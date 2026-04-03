
package modelo.dto;
 
/**
 * DTO que representa un cliente registrado en la BD de DON APARATO.
 * Extiende la información básica del UsuarioDTO añadiendo el ID de BD.
 */
public class ClienteDTO {
 
    private int    id;
    private String cedula;
    private String nombre;
    private String apellido;
    private int    edad;
    private String telefono;
    private String tipoUsuario;
 
    // ── Constructores ─────────────────────────────────────────────────────────
 
    public ClienteDTO() {}
 
    /** Constructor completo – usado al leer desde BD. */
    public ClienteDTO(int id, String cedula, String nombre, String apellido,
                      int edad, String telefono, String tipoUsuario) {
        this.id          = id;
        this.cedula      = cedula;
        this.nombre      = nombre;
        this.apellido    = apellido;
        this.edad        = edad;
        this.telefono    = telefono;
        this.tipoUsuario = tipoUsuario;
    }
 
    /** Constructor sin ID – usado al insertar un nuevo cliente. */
    public ClienteDTO(String cedula, String nombre, String apellido,
                      int edad, String telefono, String tipoUsuario) {
        this(0, cedula, nombre, apellido, edad, telefono, tipoUsuario);
    }
 
    // ── Getters & Setters ─────────────────────────────────────────────────────
 
    public int    getId()                       { return id; }
    public void   setId(int id)                 { this.id = id; }
 
    public String getCedula()                   { return cedula; }
    public void   setCedula(String cedula)      { this.cedula = cedula; }
 
    public String getNombre()                   { return nombre; }
    public void   setNombre(String nombre)      { this.nombre = nombre; }
 
    public String getApellido()                 { return apellido; }
    public void   setApellido(String apellido)  { this.apellido = apellido; }
 
    public int    getEdad()                     { return edad; }
    public void   setEdad(int edad)             { this.edad = edad; }
 
    public String getTelefono()                 { return telefono; }
    public void   setTelefono(String telefono)  { this.telefono = telefono; }
 
    public String getTipoUsuario()                        { return tipoUsuario; }
    public void   setTipoUsuario(String tipoUsuario)      { this.tipoUsuario = tipoUsuario; }
 
    /** Nombre completo para mostrar en tabla. */
    public String getNombreCompleto() { return nombre + " " + apellido; }
 
    @Override
    public String toString() {
        return "ClienteDTO{id=" + id + ", cedula='" + cedula + '\''
             + ", nombre='" + nombre + " " + apellido + '\''
             + ", tipo='" + tipoUsuario + "'}";
    }
}