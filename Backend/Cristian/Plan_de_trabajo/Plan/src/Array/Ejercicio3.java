package Array;
import java.util.Iterator;
import javax.swing.JOptionPane;

//Crea un arreglo de String con nombres de productos.
//Busca un producto ingresado por el usuario
//Indica si existe y en qué posición está


public class Ejercicio3 {

	public static void main(String[] args) {
		String[] productos = {"Leche", "Pollo", "Arroz", "Frijol", "Platano", "Detoditos", "Carne"};
		
		String search = JOptionPane.showInputDialog("Ingresa un producto para buscarlo en el invetario");

		boolean encontrado = false;
		int posicion = -1;
		for (int i = 0; i < productos.length; i++) {
			if (productos[i].equalsIgnoreCase(search)) {
				encontrado = true;;
				posicion = i;
				break;
				
				
			}
		}
		if (encontrado) {
			JOptionPane.showMessageDialog(null, "Producto encontrado¡! " + search + " esta en el indice " + posicion);
		} else {
			JOptionPane.showMessageDialog(null, "El producto " + search + " NO FUE ENCONTRADO!");
		}
	}

}
