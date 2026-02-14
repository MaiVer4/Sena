package Coleccions;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

//*Usa List<String> para almacenar materias.
// Muestra cuántas materias hay
// Imprime todas */

public class Ejercicio1 {
    public static void main(String[] args) {
        List<String> materia = new ArrayList<>();
        materia.add("Ingles");
        materia.add("Matematicas");
        materia.add("Español");
        materia.add("Sociales");
        materia.add("Tecnologia");

        String mjs = "";
        for (int i = 0; i < materia.size(); i++) {
            mjs += "Materia #" + (i + 1) + " : " + materia.get(i) + "\n";
        }
        JOptionPane.showMessageDialog(null, mjs);

    }
}
