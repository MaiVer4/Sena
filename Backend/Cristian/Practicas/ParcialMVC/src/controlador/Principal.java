
package controlador;
 
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
 
public class Principal {
 
    public static void main(String[] args) {
        // Aplicar Look and Feel del sistema operativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Si falla, continuar con el L&F por defecto
            System.err.println("No se pudo aplicar el Look and Feel: " + e.getMessage());
        }
 
        // Arrancar la aplicación en el hilo de Swing (EDT)
        SwingUtilities.invokeLater(() -> {
            Coordinador coordinador = new Coordinador();
            coordinador.iniciarAplicacion();
        });
    }
}
 