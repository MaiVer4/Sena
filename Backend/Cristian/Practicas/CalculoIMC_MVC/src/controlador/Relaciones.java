package controlador;

import modelo.Procesos;
import vista.VentanaAcercaDe;
import vista.VentanaConsulta;
import vista.VentanaIMC;

public class Relaciones {
	public Relaciones() {
		// 1. Crear instancias
		Procesos misProcesos = new Procesos();
		VentanaIMC miVentanaIMC = new VentanaIMC();
		VentanaConsulta miVentanaConsulta = new VentanaConsulta();
		VentanaAcercaDe miVentanaAcercaDe = new VentanaAcercaDe();
		Coordinador miCoordinador = new Coordinador();

		// 2. Enviar coordinador a las instancias
		misProcesos.setCoordinador(miCoordinador);
		miVentanaIMC.setCoordinador(miCoordinador);
		miVentanaConsulta.setCoordinador(miCoordinador);
		miVentanaAcercaDe.setCoordinador(miCoordinador);

		// 3. Enviar instancias al coordinador
		miCoordinador.setProcesos(misProcesos);
		miCoordinador.setVentanaIMC(miVentanaIMC);
		miCoordinador.setVentanaConsulta(miVentanaConsulta);
		miCoordinador.setVentanaAcercaDe(miVentanaAcercaDe);

		// 4. Iniciar la aplicación
		miCoordinador.mostrarVentanaIMC();
	}
}