package Conjuntos;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;

public class Ejercicio3 {
    //Crea dos conjuntos:
    //Uno con estudiantes del curso A
    //Otro con estudiantes del curso B
    //Muestra los estudiantes que están en ambos cursos
    public static void main(String[] args) {
        Set<String> cursoA = new HashSet<>();
        Set<String> cursoB = new HashSet<>();

        cursoA.add("Felipe");
        cursoA.add("Oscar");
        cursoA.add("Andres");

        cursoB.add("Juan");
        cursoB.add("Oscar");
        cursoB.add("Felipe");

        Set<String> ambosCursos = new HashSet<>(cursoA);
        ambosCursos.retainAll(cursoB);

        

        if (!ambosCursos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Los siguientes estudiantes estan en el mismo curso" + ambosCursos);
        } else {
            JOptionPane.showMessageDialog(null, "No hay estudiantes en el mismo curso!");
        }
        

    }
}
