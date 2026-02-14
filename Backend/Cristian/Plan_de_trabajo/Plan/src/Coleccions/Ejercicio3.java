package Coleccions;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

//Usa un Map<String, Integer> para contar palabras.
//ngresa una frase
//Cuenta cuántas veces aparece cada palabra
public class Ejercicio3 {
    public static void main(String[] args) {
        String frase = JOptionPane.showInputDialog("Ingrese una frase para saber cuantas palabras contiene");

        String palabras[] = frase.split(" ");
        
        Map<String, Integer> conteo = new HashMap<>();

        for (String p : palabras) {
            p = p.toLowerCase();

            if (conteo.containsKey(p)) {
                int valorActual = conteo.get(p);
                conteo.put(p, valorActual+1);
            } else {
                conteo.put(p, 1);
            }
        }
        JOptionPane.showMessageDialog(null, conteo);
    }
}
