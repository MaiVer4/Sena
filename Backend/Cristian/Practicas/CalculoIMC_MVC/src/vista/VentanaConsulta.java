package vista;

import java.util.List;
import javax.swing.*;
import controlador.Coordinador;
import modelo.dto.PersonaDTO;

public class VentanaConsulta extends JFrame {

	private Coordinador miCoordinador;
	private JTextArea txtAreaRegistros;

	public VentanaConsulta() {
		setTitle("Consulta de Registros");
		setSize(400, 300);
		setLocationRelativeTo(null);

		txtAreaRegistros = new JTextArea();
		txtAreaRegistros.setEditable(false);
		JScrollPane scroll = new JScrollPane(txtAreaRegistros);
		add(scroll);
	}

	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador = miCoordinador;
	}

	// Método que el Coordinador llama para actualizar los datos al abrir la ventana
	public void cargarDatos() {
		txtAreaRegistros.setText("--- HISTORIAL DE PACIENTES ---\n\n");
		List<PersonaDTO> registros = miCoordinador.obtenerRegistros();

		if (registros.isEmpty()) {
			txtAreaRegistros.append("No hay registros almacenados.");
		} else {
			for (PersonaDTO p : registros) {
				txtAreaRegistros.append("Nombre: " + p.getNombre() + "\n" + "Edad: " + p.getEdad() + "\n" + "Peso: " + p.getPeso() + " (Kg)" + "\n" + "Talla: " + p.getTalla() + "\n");
				txtAreaRegistros.append(String.format("IMC: %.2f (%s)\n", p.getImc(), p.getEstado()));
				txtAreaRegistros.append("----------------------------\n");
			}
		}
	}
}