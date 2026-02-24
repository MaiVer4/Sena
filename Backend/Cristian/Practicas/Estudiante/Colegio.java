package Practicas.Estudiante;

import javax.swing.JOptionPane;

public class Colegio {

    public static void main(String[] args) {
        String nombre = JOptionPane.showInputDialog("Ingrese su nombre");
        String materia = JOptionPane.showInputDialog("Ingrese el nombre la materia");
        double nota1 = Double.parseDouble(JOptionPane.showInputDialog("Ingrese la nota 1"));
        double nota2 = Double.parseDouble(JOptionPane.showInputDialog("Ingrese la nota 2"));
        double nota3 = Double.parseDouble(JOptionPane.showInputDialog("Ingrese la nota 3"));

        do {
            
        } while (nota1, nota2);

        double prom = (nota1 + nota2 + nota3) / 3;

        if (prom >= 3.5) {
            JOptionPane.showMessageDialog(null, nombre + " Gano " + materia + " Con " + prom);
        } else {
            JOptionPane.showMessageDialog(null, nombre + " PERDIO LA MATERIA DE " + materia);
            if (prom >= 2.5) {
                JOptionPane.showMessageDialog(null, nombre + " Puede recuperar " + materia);
            }
        }

    }

}