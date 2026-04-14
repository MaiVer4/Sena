package modelo;

import java.util.ArrayList;
import java.util.List;
import controlador.Coordinador;
import modelo.dto.PersonaDTO;

public class Procesos {
	private Coordinador miCoordinador;
	private List<PersonaDTO> baseDeDatosMemoria;

	public Procesos() {
		// Almacenamiento de registros en memoria
		baseDeDatosMemoria = new ArrayList<>();
	}

	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador = miCoordinador;
	}

	// modelo de negocio: Calcula y guarda
	public void calcularYGuardarIMC(PersonaDTO miPersona) {
		double imc = miPersona.getPeso() / (miPersona.getTalla() * miPersona.getTalla());
		miPersona.setImc(imc);

		if (imc < 18.5) {
			miPersona.setEstado("Bajo Peso");
		} else if (imc < 25) {
			miPersona.setEstado("Peso Normal");
		} else if (imc < 30) {
			miPersona.setEstado("Sobre Peso");
		} else {
			miPersona.setEstado("Obesidad");
		}

		// Guardar el registro en el arraylist
		baseDeDatosMemoria.add(miPersona);
	}

	public List<PersonaDTO> obtenerRegistros() {
		return baseDeDatosMemoria;
	}
}