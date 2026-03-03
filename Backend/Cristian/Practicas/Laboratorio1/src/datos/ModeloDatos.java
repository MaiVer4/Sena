package datos;

import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JOptionPane;

import entidades.Estudiante;

public class ModeloDatos {
	HashMap<Integer, Estudiante> EstudiantesMap;

	public ModeloDatos() {
		EstudiantesMap = new HashMap<Integer, Estudiante>();
	}

	public String registrarEstudiante(Estudiante est) {
		if (!EstudiantesMap.containsKey(est.getId())) {
			EstudiantesMap.put(est.getId(), est);
			return "Ok";
		} else {
			return "Estudiante existente";
		}
	}

	public Estudiante consultarEstudiante(String busquedaId) {
		Estudiante esTemporal = null;
		try {

			int id = Integer.parseInt(busquedaId);

			if (EstudiantesMap.containsKey(id)) {
				esTemporal = EstudiantesMap.get(id);
			}
		} catch (NumberFormatException e) {
			System.out.println("Error: El ID debe ser un número");
		}
		return esTemporal;
	}

	public Estudiante eliminarEstudiante(String eliminarEst) {
		Estudiante elimEs = null;
		try {
			int id = Integer.parseInt(eliminarEst);
			if (EstudiantesMap.containsKey(id)) {
				elimEs = EstudiantesMap.remove(id);
				JOptionPane.showMessageDialog(null, "Estudiante con ID " + id + " eliminado correctamente.");
			} else {
				JOptionPane.showMessageDialog(null, "No se encontró ningún estudiante con el ID: " + id);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error: El ID debe ser un numero", eliminarEst,
					JOptionPane.ERROR_MESSAGE);
		}
		return elimEs;
	}

	public String actualizarEstudiante(Estudiante estudianteEditado) {
	    try {
	        int id = estudianteEditado.getId();
	        if (EstudiantesMap.containsKey(id)) {
	            EstudiantesMap.put(id, estudianteEditado);
	            return "Ok";
	        } else {
	            return "No se encontró ningún estudiante con el ID: " + id;
	        }
	    } catch (Exception e) {
	        return "Error al actualizar: " + e.getMessage();
	    }
	}

	public String imprimirListaEstudiantes() {
		String msj = "DATOS ESTUDIANTES\n";
		for (Estudiante estudiante : EstudiantesMap.values()) {
			msj += "Nombre: " + estudiante.getNombre() + ", Materia: " + estudiante.getMateria() + "\n";
			msj += "ID: " + estudiante.getId() + ", Nota 1: " + estudiante.getNota1() + ", ";
			msj += "Nota 2: " + estudiante.getNota2() + ",  Nota 3: " + estudiante.getNota3() + "\n";
			msj += "Promedio" + estudiante.getPoromedio() + "\n";

			msj += "-----------------------------------------------------------------------------------------\n";
		}

		System.out.println(msj);

		return msj;
	}

}