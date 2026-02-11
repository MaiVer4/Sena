package Array;
import javax.swing.JOptionPane;

//Crea un arreglo de double con las notas de 5 estudiantes.
//Calcula el promedio
//Muestra la nota mayor y la nota menor


public class Ejercicio2 {

    public static void main(String[] args) {
        Double[] Estudiantes = new Double[5];
        double suma = 0;

        
        for (int i = 0; i < Estudiantes.length; i++) {
            String input = JOptionPane.showInputDialog("Ingrese una nota para el estudiante: " + (i + 1));
            Estudiantes[i] = Double.parseDouble(input);
            suma += Estudiantes[i];
        }

        double notaMayor = Estudiantes[0];
        double notaMenor = Estudiantes[0];


        for (int j = 0; j < Estudiantes.length; j++) {
            if (Estudiantes[j] > notaMayor) {
                notaMayor = Estudiantes[j];
            }
            if (Estudiantes[j] < notaMenor) {
                notaMenor = Estudiantes[j];
            }
        }

        double promedio = suma / Estudiantes.length;
        
        String resultado = "Resultados Finales:\n" +
                           "Promedio: " + promedio + "\n" +
                           "Nota Mayor: " + notaMayor + "\n" +
                           "Nota Menor: " + notaMenor;

        JOptionPane.showMessageDialog(null, resultado);
    }
}