package ArraList;
import java.util.ArrayList;
import javax.swing.JOptionPane;

//Crea un ArrayList<String> para guardar nombres.
//Permite agregar nombres hasta que el usuario decida parar
//Muestra la lista completa

public class Ejercicio1 {

	public static void main(String[] args) {
		ArrayList<String> nombres = new ArrayList<>();

		boolean agregarMasNombres = true;

		while (agregarMasNombres) {
			String opcionS = JOptionPane.showInputDialog("1: Agregar un nombre\n" +
					"2: Ver nombres\n" +
					"3: Salir");

			if (opcionS == null)
				break;

			int opcion = Integer.parseInt(opcionS);

			if (opcion == 1) {
				String nombre = JOptionPane.showInputDialog(null, "Agrega el nombre: ");
				nombres.add(nombre);
				JOptionPane.showMessageDialog(null, "Nombre guardado con exito!");
			} else if (opcion == 2) {
				JOptionPane.showMessageDialog(null, nombres);
			} else {
				break;
			}
		}

	}

}
