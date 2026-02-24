
public class Estudiantes {
	private String nombre;
	private String materia;
	private double n1, n2, n3,promedio;
	private String resultado;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getMateria() {
		return materia;
	}
	public void setMateria(String materia) {
		this.materia = materia;
	}
	public double getN1() {
		return n1;
	}
	public void setN1(double n1) {
		this.n1 = n1;
	}
	public double getN2() {
		return n2;
	}
	public void setN2(double n2) {
		this.n2 = n2;
	}
	public double getN3() {
		return n3;
	}
	public void setN3(double n3) {
		this.n3 = n3;
	}
	public double getPromedio() {
		
		return promedio;
	}
	public void setPromedio(double promedio) {
		this.promedio = promedio;
	}
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	
	@Override
	public String toString() {
		return "Estudiante [nombre=" + nombre + ", materia=" + materia + ", nota1=" + n1 + ", nota2=" + n2
				+ ", nota3=" + n3 + ", promedio=" + promedio + ", resultado=" + resultado + "]";
	}
	
}
