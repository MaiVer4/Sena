package practicaVacaciones.veterinaria;

public class mascota {
    private String nombre;
    private int edad;
    private double peso;
    private String dueño;

    public mascota(String nombre, int edad, double peso, String dueño) {
        this.nombre = nombre;
        this.edad = edad;
        this.peso = peso;
        this.dueño = dueño;
    }

    public boolean setEdad(int edad) {
        if (edad >= 0 && edad <= 30) {
            this.edad = edad;
            return true;
        } else {
            System.out.println("Error!! || Ingrese la edad nuevamente");
            return false;
        }
    }

    public int getEdad() {
        return edad;
    }

}
