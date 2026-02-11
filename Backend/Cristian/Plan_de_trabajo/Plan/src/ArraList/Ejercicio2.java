package ArraList;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Ejercicio2 {

	public static void main(String[] args) {
		ArrayList<Integer> numeros = new ArrayList<Integer>(List.of(4, 5, 6, 8, 2, 9, 20, 23, 11, 4, 5, 16, 15));

		numeros.removeIf(n -> n % 2 == 0);
		JOptionPane.showMessageDialog(null, numeros);
	}

}
