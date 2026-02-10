package Array;
import javax.swing.JOptionPane;

//Crea un arreglo de int con 10 posiciones.
public class Ejercicio1 {

	public static void main(String[] args) {
		
		//Arreglo de enteros
		int[] enteros = new int[10];
		
		String mensaje = "";
		
		for (int i = 0; i < enteros.length; i++) {
			enteros[i] = Integer.parseInt(JOptionPane.showInputDialog("Ingrese un valor entero para el indice " + i));
			mensaje += "Indice " + i + "Valor: " + enteros[i] + "\n"; 
		}
		
		JOptionPane.showMessageDialog(null, mensaje);
	}

}

