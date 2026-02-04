package Cpractica.CeroUno.Persona;

public class Persona {
    private String nombre;
    private int edad;
    private String hobby;

    public Persona(String nombre, int edad, String hobby) {
        this.nombre = nombre;
        this.edad = edad;
        this.hobby = hobby;
    }

    public void saludar() {
        System.out.println("Hola, soy " + nombre + ". Tengo " + edad + " y me gusta " + hobby + ".");
    }

    @Override
    public String toString() {
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", hobby='" + hobby + '\'' +
                '}';
    }

}
