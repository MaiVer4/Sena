package Queue;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.JOptionPane;

public class Ejercicio1 {
    // Simula una cola de atención:
    // Agrega 5 personas
    // Atiende a la primera
    public static void main(String[] args) {
        Queue<String> cola = new LinkedList<>();
        cola.add("Pepe");
        cola.add("Carlos");
        cola.add("Gabriel");
        cola.add("Sofia");
        cola.add("Luis");

        String atendido = cola.poll();

        JOptionPane.showMessageDialog(null, atendido + " Fue antendido!\n" + "Quedan " + cola + " en la cola");

    }
}
