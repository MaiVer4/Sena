package clases;

import entidades.Estudiante;

public class Procesos {
	public double CalcularPromedio(Estudiante e) {
		double n1 = e.getNota1();
		double n2 = e.getNota2();
		double n3 = e.getNota3();

		double prom = -1;

		if (validar(n1, n2, n3) == true) {
			prom = (n1 + n2 + n2) / 3;
		}
		return prom;
	}

	private boolean validar(double n1, double n2, double n3) {
		if (n1 > 0 && n1 <= 5 && n2 > 0 && n2 <= 5 && n3 > 0 && n3 <= 5) {
			return true;
		}
		return false;
	}
}
