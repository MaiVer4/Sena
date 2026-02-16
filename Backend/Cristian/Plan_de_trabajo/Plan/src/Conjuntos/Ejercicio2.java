package Conjuntos;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;

public class Ejercicio2 {

    // Guarda números ingresados en un Set<Integer>.
    // Muestra cuántos números únicos hay
    public static void main(String[] args) {
        Set<Integer> numeros = new HashSet<>(); 
        boolean agregarNuevo;
        do {
            int nIngresado = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese un numero para guardarlo"));
            
            agregarNuevo = numeros.add(nIngresado);

            JOptionPane.showMessageDialog(null, "Los numeros unicos guardados son: " + numeros);

            JOptionPane.showMessageDialog(null, "En total hay " + numeros.size() + " Numeros guardados");
        } while (agregarNuevo);
    }
}
