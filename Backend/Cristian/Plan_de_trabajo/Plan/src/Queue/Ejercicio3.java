package Queue;

import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JOptionPane;

public class Ejercicio3 {

    // Simula una impresora:
    // documentos entran a una cola
    // imprimen en orden de llegada
    public static void main(String[] args) {
        Queue<String> documentos = new LinkedList<>();
        documentos.add("Documento 1");
        documentos.add("Documento 2");
        documentos.add("Documento 3");
        documentos.add("Documento 4");

        for (int i = 0; i <= documentos.size(); i++) {
            String DocumentoImprimido = documentos.poll();
            String siguienteDocumento = documentos.peek();

            JOptionPane.showMessageDialog(null, DocumentoImprimido + " Se imprimio " +
                    "\nSiguiente documento " + siguienteDocumento);
        }

    }
}
