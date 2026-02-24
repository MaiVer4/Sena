import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JOptionPane;

public class ModeloDatos {

	private HashMap<String, Estudiantes> mapaEstudiantes;

	public ModeloDatos() {
		mapaEstudiantes = new HashMap<>();
	}

	public void guardarEstudiante(Estudiantes estudiante) {
		mapaEstudiantes.put(estudiante.getNombre(), estudiante);
		JOptionPane.showMessageDialog(null, "Se guardo Correctamente el estudiante " + estudiante);
	}

	public HashMap<String, Estudiantes> getmapaEstudiantes() {
		return mapaEstudiantes;
	}
	
	public void imprimirLista() {
	    System.out.println("\n==========================================================================================");
	    System.out.println("                         LISTADO DE ESTUDIANTES REGISTRADOS                               ");
	    System.out.println("==========================================================================================");
	   
	    System.out.printf("%-15s | %-12s | %-6s | %-6s | %-6s | %-8s | %-20s %n", 
	                      "NOMBRE", "MATERIA", "N1", "N2", "N3", "PROM", "ESTADO");
	    System.out.println("------------------------------------------------------------------------------------------");

	    for (Estudiantes est : mapaEstudiantes.values()) {
	        System.out.printf("%-15s | %-12s | %-6.1f | %-6.1f | %-6.1f | %-8.2f | %-20s %n", 
	                          est.getNombre(), 
	                          est.getMateria(), 
	                          est.getN1(), 
	                          est.getN2(), 
	                          est.getN3(), 
	                          est.getPromedio(), 
	                          est.getResultado());
	    }
	    System.out.println("==========================================================================================\n");
	}
}
