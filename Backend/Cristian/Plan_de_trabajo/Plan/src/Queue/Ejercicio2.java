package Queue;

import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JOptionPane;

//Crea una cola de turnos:
//Permite agregar turnos
//Atiende uno por uno mostrando quién sigue

public class Ejercicio2 {
    public static void main(String[] args) {
        Queue<String> turnos = new LinkedList<>();

        while (true) {
            String input = JOptionPane.showInputDialog("Sistema de Turnos\n" +
                    "1: Pedir Turno\n" +
                    "2: Atender Siguiente\n" +
                    "3: Ver Cola\n" +
                    "4: Salir");

            if (input == null)
                break; 
            int option = Integer.parseInt(input);

            switch (option) {
                case 1:
                    String nombre = JOptionPane.showInputDialog("Ingrese su nombre:");
                    if (nombre != null && !nombre.isEmpty()) {
                        turnos.add(nombre); 
                        JOptionPane.showMessageDialog(null, nombre + " ha tomado el turno.");
                    }
                    break;

                case 2: 
                    if (!turnos.isEmpty()) {
                        String atendido = turnos.poll();
                        String siguiente = turnos.isEmpty() ? "Nadie" : turnos.peek();

                        JOptionPane.showMessageDialog(null, "Atendiendo a: " + atendido +
                                "\nSiguiente en fila: " + siguiente);
                    } else {
                        JOptionPane.showMessageDialog(null, "No hay nadie en la cola.");
                    }
                    break;

                case 3: 
                    JOptionPane.showMessageDialog(null, "Cola actual: " + turnos);
                    break;

                case 4: 
                    JOptionPane.showMessageDialog(null, "Cerrando sistema...");
                    System.exit(0);
                    break;
            }
        }
    }
}
