import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        HashMap<String, Operario> operarios = new HashMap<>();
        int opcion = 0;
        while (opcion != 3) {
            String input = JOptionPane.showInputDialog(
                    "Seleccione una opción:\n" +
                    "1: Ingresar operario\n" +
                    "2: Ver Operarios\n" +
                    "3: Salir"
            );
            if (input == null) { // Usuario canceló
                break;
            }
            try {
                opcion = Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Opción inválida.");
                continue;
            }

            if (opcion == 1) {
                String Documento = JOptionPane.showInputDialog("Ingrese el documento");
                if (Documento == null || Documento.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Documento inválido.");
                    continue;
                }
                String Nombre = JOptionPane.showInputDialog("Ingrese el nombre");
                if (Nombre == null) Nombre = "";
                double sueldo;
                try {
                    sueldo = Double.parseDouble(JOptionPane.showInputDialog("Ingrese su sueldo"));
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Sueldo inválido.");
                    continue;
                }
                int antiguedad;
                try {
                    antiguedad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese su antiguedad"));
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Antiguedad inválida.");
                    continue;
                }
                Operario op1 = new Operario(Documento, Nombre, sueldo, antiguedad);
                operarios.put(Documento, op1);
                JOptionPane.showMessageDialog(null, "Operario guardado.");
            } else if (opcion == 2) {
                if (operarios.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No hay operarios ingresados.");
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (Map.Entry<String, Operario> e : operarios.entrySet()) {
                        Operario o = e.getValue();
                        sb.append("Documento: ").append(o.documento).append("\n")
                          .append("Nombre: ").append(o.nombre).append("\n")
                          .append("Sueldo Original: ").append(o.sueldo).append("\n")
                          .append("Antiguedad: ").append(o.antiguedad).append("\n")
                          .append("Sueldo Final: ").append(o.calcularSueldoFina()).append("\n\n");
                    }
                    JOptionPane.showMessageDialog(null, sb.toString());
                }
            } else if (opcion == 3) {
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Opción no válida.");
            }
        }
    }
}
