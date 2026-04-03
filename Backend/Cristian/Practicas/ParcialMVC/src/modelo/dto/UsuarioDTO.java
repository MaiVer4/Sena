package modelo.dto;
 
public class UsuarioDTO {
    private String nombre;
    private String apellido;
    private int edad;
    private String telefono;
    private String cedula;
    private String tipoUsuario;
 
    public UsuarioDTO() {}
 
    public UsuarioDTO(String nombre, String apellido, int edad, String telefono, String cedula, String tipoUsuario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.telefono = telefono;
        this.cedula = cedula;
        this.tipoUsuario = tipoUsuario;
    }
 
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
 
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
 
    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
 
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
 
    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }
 
    public String getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(String tipoUsuario) { this.tipoUsuario = tipoUsuario; }
 
    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", edad=" + edad +
                ", telefono='" + telefono + '\'' +
                ", cedula='" + cedula + '\'' +
                ", tipoUsuario='" + tipoUsuario + '\'' +
                '}';
    }
}
 