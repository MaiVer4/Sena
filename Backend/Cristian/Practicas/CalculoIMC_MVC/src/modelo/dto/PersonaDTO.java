package modelo.dto;

public class PersonaDTO {
	private String nombre;
	private int edad;
	private double peso;
	private double talla;
	private double imc;
	private String estado;

	public PersonaDTO(String nombre, int edad, double peso, double talla) {
		this.nombre = nombre;
		this.edad = edad;
		this.peso = peso;
		this.talla = talla;
	}

	// Getters y Setters
	public String getNombre() { return nombre; }
	public void setNombre(String nombre) { this.nombre = nombre; }

	public int getEdad() { return edad; }
	public void setEdad(int edad) { this.edad = edad; }

	public double getPeso() { return peso; }
	public void setPeso(double peso) { this.peso = peso; }

	public double getTalla() { return talla; }
	public void setTalla(double talla) { this.talla = talla; }

	public double getImc() { return imc; }
	public void setImc(double imc) { this.imc = imc; }

	public String getEstado() { return estado; }
	public void setEstado(String estado) { this.estado = estado; }
}
