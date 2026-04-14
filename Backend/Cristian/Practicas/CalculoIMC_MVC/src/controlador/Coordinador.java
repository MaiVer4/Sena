package controlador;

import java.util.List;
import modelo.dto.PersonaDTO;
import modelo.Procesos;
import vista.VentanaAcercaDe;
import vista.VentanaConsulta;
import vista.VentanaIMC;

public class Coordinador {
	private Procesos misProcesos;
	private VentanaIMC miVentanaIMC;
	private VentanaConsulta miVentanaConsulta;
	private VentanaAcercaDe miVentanaAcercaDe;

	// Setters
	public void setProcesos(Procesos misProcesos) { this.misProcesos = misProcesos; }
	public void setVentanaIMC(VentanaIMC miVentanaIMC) { this.miVentanaIMC = miVentanaIMC; }
	public void setVentanaConsulta(VentanaConsulta miVentanaConsulta) { this.miVentanaConsulta = miVentanaConsulta; }
	public void setVentanaAcercaDe(VentanaAcercaDe miVentanaAcercaDe) { this.miVentanaAcercaDe = miVentanaAcercaDe; }

	// Control de Vistas
	public void mostrarVentanaIMC() {
		miVentanaIMC.setVisible(true);
	}

	public void mostrarVentanaConsulta() {
		miVentanaConsulta.cargarDatos(); // Pide actualizar los datos antes de mostrar
		miVentanaConsulta.setVisible(true);
	}

	public void mostrarVentanaAcercaDe() {
		miVentanaAcercaDe.setVisible(true);
	}

	// Comunicación Vista -> Modelo
	public void procesarPersona(PersonaDTO miPersona) {
		misProcesos.calcularYGuardarIMC(miPersona);
	}

	// Comunicación Modelo -> Vista
	public List<PersonaDTO> obtenerRegistros() {
		return misProcesos.obtenerRegistros();
	}
}