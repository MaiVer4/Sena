package Conjuntos;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;

public class Ejercicio1 {

    // Crea un Set<String> con correos electrónicos.
    // Intenta agregar repetidos
    // Muestra el conjunto
    public static void main(String[] args) {
        Set<String> correoE = new HashSet<>();
        boolean esNuevo;
        do {
            String n = JOptionPane.showInputDialog("Ingrese un correo");
            n = n.toLowerCase();
            if (n == null)
                break;

            esNuevo = correoE.add(n);
            if (!esNuevo) {
                JOptionPane.showMessageDialog(null, "Error!!\n " + "El correo ya existe");
            }

            correoE.add(n);
        } while (esNuevo);
    }
}