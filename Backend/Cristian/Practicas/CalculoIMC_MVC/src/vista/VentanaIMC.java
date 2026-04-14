package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import controlador.Coordinador;
import modelo.dto.PersonaDTO;


public class VentanaIMC extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JTextField txtNombre, txtEdad, txtPeso, txtTalla;
	private JButton btnCalcular, btnLimpiar, btnConsultar, btnAcercaDe;
	private JLabel lblResultado;
	private Coordinador miCoordinador;

	public VentanaIMC() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(680, 450);
		setLocationRelativeTo(null);
		setTitle("Sistema de Salud MVC");
		iniciarComponentes();
	}

	public void iniciarComponentes() {
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panel);
		panel.setLayout(null);

		JLabel lblTitulo = new JLabel("Cálculo del Índice de Masa Corporal");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Trebuchet MS", Font.BOLD, 26));
		lblTitulo.setBounds(10, 10, 600, 40);
		panel.add(lblTitulo);

		// Inputs (Nombre, Edad, Peso, Talla)
		panel.add(crearLabel("Nombre:", 40, 80));
		txtNombre = crearTextField(140, 75, 350);

		panel.add(crearLabel("Edad:", 40, 120));
		txtEdad = crearTextField(140, 115, 100);

		panel.add(crearLabel("Peso (kg):", 40, 160));
		txtPeso = crearTextField(140, 160, 100);

		panel.add(crearLabel("Talla (m):", 40, 200));
		txtTalla = crearTextField(140, 200, 100);

		lblResultado = new JLabel("Esperando datos...");
		lblResultado.setBounds(40, 250, 500, 30);
		lblResultado.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		panel.add(lblResultado);

		// Botones
		btnLimpiar = crearBoton("Limpiar", 40, 320);
		btnCalcular = crearBoton("Calcular", 180, 320);
		btnConsultar = crearBoton("Consultar", 320, 320);
		btnAcercaDe = crearBoton("Acerca de", 460, 320);
	}

	private JLabel crearLabel(String texto, int x, int y) {
		JLabel label = new JLabel(texto);
		label.setBounds(x, y, 100, 20);
		return label;
	}

	private JTextField crearTextField(int x, int y, int width) {
		JTextField txt = new JTextField();
		txt.setBounds(x, y, width, 30);
		panel.add(txt);
		return txt;
	}

	private JButton crearBoton(String texto, int x, int y) {
		JButton btn = new JButton(texto);
		btn.setBounds(x, y, 130, 45);
		btn.addActionListener(this);
		panel.add(btn);
		return btn;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCalcular) calcularIMC();
		else if (e.getSource() == btnLimpiar) limpiar();
		else if (e.getSource() == btnConsultar) miCoordinador.mostrarVentanaConsulta();
		else if (e.getSource() == btnAcercaDe) miCoordinador.mostrarVentanaAcercaDe();
	}

	private void calcularIMC() {
		try {
			// 1. VALIDACIÓN DE DATOS (No permite texto ni vacíos ni negativos)
			String nombre = txtNombre.getText();
			int edad = Integer.parseInt(txtEdad.getText());
			double peso = Double.parseDouble(txtPeso.getText());
			double talla = Double.parseDouble(txtTalla.getText());

			if (nombre.isEmpty() || edad <= 0 || peso <= 0 || talla <= 0) {
				JOptionPane.showMessageDialog(this, "Valores incorrectos. No se permiten negativos ni campos vacíos.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			// 2. Creación del objeto y paso al coordinador
			PersonaDTO nuevaPersona = new PersonaDTO(nombre, edad, peso, talla);
			miCoordinador.procesarPersona(nuevaPersona);

			// 3. Actualización de UI
			lblResultado.setForeground(Color.BLACK);
			lblResultado.setText(String.format("Registrado: %s | IMC: %.2f | Estado: %s",
					nuevaPersona.getNombre(), nuevaPersona.getImc(), nuevaPersona.getEstado()));

		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Por favor ingrese valores numéricos válidos en Edad, Peso y Talla.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void limpiar() {
		txtNombre.setText("");
		txtEdad.setText("");
		txtPeso.setText("");
		txtTalla.setText("");
		lblResultado.setText("Esperando datos...");
		lblResultado.setForeground(Color.BLACK);
	}

	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador = miCoordinador;
	}
}