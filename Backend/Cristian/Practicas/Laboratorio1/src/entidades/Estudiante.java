package entidades;

public class Estudiante {
	private String nombre;
	private String materia;
	private double nota1;
	private double nota2;
	private double nota3;
	private double poromedio;
	private int id;

	public Estudiante() {
		
	}
	
	public Estudiante(String nombre, String materia, double nota1, double nota2, double nota3, double poromedio, int id) {
		this.nombre = nombre;
		this.materia = materia;
		this.nota1 = nota1;
		this.nota2 = nota2;
		this.nota3 = nota3;
		this.poromedio = poromedio;
		this.id = id;
	}

	

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

	public double getNota1() {
		return nota1;
	}

	public void setNota1(double nota1) {
		this.nota1 = nota1;
	}

	public double getNota2() {
		return nota2;
	}

	public void setNota2(double nota2) {
		this.nota2 = nota2;
	}

	public double getNota3() {
		return nota3;
	}

	public void setNota3(double nota3) {
		this.nota3 = nota3;
	}

	public double getPoromedio() {
		return poromedio;
	}

	public void setPoromedio(double poromedio) {
		this.poromedio = poromedio;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

}
