import javax.swing.JOptionPane;

public class Operario {
    String documento;
    String nombre;
    double sueldo;
    int antiguedad;

    public Operario(String documento, String nombre, double sueldo, int antiguedad){
        this.documento = documento;
        this.nombre = nombre;
        this.sueldo = sueldo;
        this.antiguedad = antiguedad;

    }

    public double calcularSueldoFina(){
        if (sueldo < 500.00 && antiguedad >= 10) {
            return sueldo +(sueldo * 0.20);
        } 
        else if (sueldo > 500 && antiguedad < 10){
            return sueldo + (sueldo * 0.05);
        } else {
            return sueldo;
        }
    }

    public void mostrarDatos(){
        JOptionPane.showMessageDialog(null ,
                                            "Documento: " + documento + "\n" +
                                            "Nombre: " + nombre + "\n" + 
                                            "Sueldo Origina: " + sueldo + "\n" + 
                                            "Antiguedad: " + antiguedad + "\n" +
                                            "Sueldo Fina: " + calcularSueldoFina()
                                        );

    }
}

