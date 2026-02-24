import javax.swing.JDialog;

public class procesos {
	Estudiantes miEs;
	
	void calcularResultado(Estudiantes miEs) {
		double p = (miEs.getN1() + miEs.getN2() + miEs.getN3()) / 3;
		miEs.setPromedio(p);
		
		if (p >= 3.5) {
            miEs.setResultado(miEs.getNombre() + " GANA LA MATERIA: " + miEs.getMateria());
        } else {
            String mensaje = miEs.getNombre() + " PIERDE LA MATERIA: " + miEs.getMateria();
            if (p >= 2.5) {
                miEs.setResultado(mensaje + " (PUEDE RECUPERAR)");
            } else {
                miEs.setResultado(mensaje + " (NO LA PUEDE RECUPERAR)");
            }
        }
	}

	public void registrar(Estudiantes miEstudiante) {
		// TODO Auto-generated method stub
		
	}
}
