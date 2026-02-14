package ArraList;
import java.util.ArrayList;
import javax.swing.JOptionPane;

//Crea un ArrayList<Double> con precios.
//Permite modificar un precio por índice
//Calcula el total de la compra

public class Ejercicio3 {
	public static void main(String[] args) {
		ArrayList<Double> precios = new ArrayList<Double>();

		precios.add(3.500);
		precios.add(2.300);
		precios.add(1.100);
		precios.add(1.000);
		precios.add(6.000);

		int indicePorModificar = 2;
		double NuevoPrecio = 2.000;

		if (indicePorModificar >= 0 && indicePorModificar < precios.size()) {
			precios.set(indicePorModificar, NuevoPrecio);
			JOptionPane.showMessageDialog(null,
					"Precios en indice " + indicePorModificar + " Modificado a: " + NuevoPrecio);
		}

		double totalCompra = 0;
		for (Double c : precios) {
			totalCompra += c;
		}

		JOptionPane.showMessageDialog(null, "El total de la compra es " + totalCompra);

	}

}
