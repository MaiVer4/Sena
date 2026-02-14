package Coleccions;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

//Crea un Map<String, Double> con:
//Nombre del estudiante → nota
//Muestra la nota de un estudiante ingresado

public class Ejercicio2 {
    public static void main(String[] args) {
        Map<String, Double> estudiantes = new HashMap<>();
        estudiantes.put("Andres", 3.5);
        estudiantes.put("Pepito", 2.1);
        estudiantes.put("Sergio", 4.1);
        estudiantes.put("Alejo", 5.0);

        JOptionPane.showMessageDialog(null, estudiantes);
    }
}

//Al imprimir estudiantes y analizar los datos que me mostro en pantalla pude visualizar que me los mostro en orden
//alfabetico aunque no los haya ingresado asi.