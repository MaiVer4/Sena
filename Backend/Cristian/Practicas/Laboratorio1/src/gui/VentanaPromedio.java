package gui;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import clases.Procesos;
import datos.ModeloDatos;
import entidades.Estudiante;

public class VentanaPromedio extends JFrame implements ActionListener {
	private JTextField txtMateria, txtNombre, txtID, txtNota1, txtNota2, txtNota3;
	private JButton btnCalcular, btnLimpiar, btnConsultaIndividual, btnLista, btnEliminar, btnEditar;
	private JLabel lblResultado;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	Procesos procesos;
	ModeloDatos ModeloDatos;

	public VentanaPromedio() {
		procesos = new Procesos();
		ModeloDatos = new ModeloDatos();
		setTitle("Promedio estudiantes");
		setSize(660, 600);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		iniciarComponentes();
	}

	private void iniciarComponentes() {
		JLabel lblTitulo = new JLabel("SISTEMA CONTROL DE NOTAS");
		lblTitulo.setFont(new Font("Verdana", Font.BOLD, 25));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(10, 20, 606, 50);
		add(lblTitulo);

		// --- FILA 1: Nombre ---
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNombre.setBounds(24, 90, 72, 22);
		add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(106, 93, 201, 19);
		add(txtNombre);

		// --- FILA 2: ID (Nuevo campo debajo del nombre) ---
		JLabel lblID = new JLabel("ID / Doc:");
		lblID.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblID.setBounds(24, 125, 72, 22);
		add(lblID);

		txtID = new JTextField(); // No olvides declararlo arriba como variable de instancia
		txtID.setBounds(106, 128, 201, 19);
		add(txtID);

		// Materia (al lado del ID)
		JLabel lblMateria = new JLabel("Materia:");
		lblMateria.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblMateria.setBounds(348, 90, 72, 22);
		add(lblMateria);

		txtMateria = new JTextField();
		txtMateria.setBounds(430, 92, 180, 19);
		add(txtMateria);

		// --- FILA 3: Notas (Bajamos un poco la Y a 170) ---
		JLabel lblNota1 = new JLabel("Nota1:");
		lblNota1.setBounds(24, 170, 72, 22);
		add(lblNota1);
		txtNota1 = new JTextField();
		txtNota1.setBounds(106, 172, 90, 19);
		add(txtNota1);

		JLabel lblNota2 = new JLabel("Nota2:");
		lblNota2.setBounds(220, 170, 72, 22);
		add(lblNota2);
		txtNota2 = new JTextField();
		txtNota2.setBounds(280, 172, 90, 19);
		add(txtNota2);

		JLabel lblNota3 = new JLabel("Nota3:");
		lblNota3.setBounds(410, 170, 72, 22);
		add(lblNota3);
		txtNota3 = new JTextField();
		txtNota3.setBounds(480, 172, 90, 19);
		add(txtNota3);

		// --- Resto de componentes (Resultado, Botones, Scroll) ---
		lblResultado = new JLabel("Resultado: ");
		lblResultado.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblResultado.setBounds(24, 215, 586, 22);
		add(lblResultado);

		int anchoBoton = 95; // Reducimos un poco para que quepan 6
		int yBotones = 260; // Posición vertical

		btnCalcular = new JButton("Calcular");
		btnCalcular.setBounds(20, yBotones, anchoBoton, 30);
		btnCalcular.addActionListener(this);
		add(btnCalcular);

		btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setBounds(120, yBotones, anchoBoton, 30);
		btnLimpiar.addActionListener(this);
		add(btnLimpiar);

		btnConsultaIndividual = new JButton("Consultar");
		btnConsultaIndividual.setBounds(220, yBotones, anchoBoton, 30);
		btnConsultaIndividual.addActionListener(this);
		add(btnConsultaIndividual);

		btnLista = new JButton("Lista");
		btnLista.setBounds(320, yBotones, anchoBoton, 30);
		btnLista.addActionListener(this);
		add(btnLista);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(420, yBotones, anchoBoton, 30);
		btnEliminar.setBackground(new Color(255, 182, 193));
		btnEliminar.addActionListener(this);
		add(btnEliminar);

		// --- NUEVO BOTÓN EDITAR ---
		btnEditar = new JButton("Editar");
		btnEditar.setBounds(520, yBotones, anchoBoton, 30);
		btnEditar.setBackground(new Color(173, 216, 230)); // Color azul claro opcional
		btnEditar.addActionListener(this);
		add(btnEditar);

		textArea = new JTextArea();
		scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(30, 310, 580, 210);
		add(scrollPane);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCalcular) {
			calcular();
		} else if (e.getSource() == btnLimpiar) {
			limpiar();
		} else if (e.getSource() == btnConsultaIndividual) {
			consultaIndividual();
		} else if (e.getSource() == btnLista) {
			consultarLista();
		} else if (e.getSource() == btnEliminar) {
			eliminarEstudiante();
		} else if (e.getSource() == btnEditar) {
			actualizarEstudiante();
		}
	}

	private void calcular() {
		try {
			Estudiante miEstudiante = new Estudiante();
			miEstudiante.setNombre(txtNombre.getText());
			miEstudiante.setMateria(txtMateria.getText());
			miEstudiante.setNota1(Double.parseDouble(txtNota1.getText()));
			miEstudiante.setNota2(Double.parseDouble(txtNota2.getText()));
			miEstudiante.setNota3(Double.parseDouble(txtNota3.getText()));
			miEstudiante.setId(Integer.parseInt(txtID.getText()));

			double promedio = procesos.CalcularPromedio(miEstudiante);

			miEstudiante.setPoromedio(promedio);

			if (promedio >= 0 && promedio <= 5) {
				lblResultado.setForeground(new Color(0, 128, 0));
				lblResultado.setText("Resultado: Hola " + miEstudiante.getNombre() + ", ID: " + miEstudiante.getId()
						+ ", su promedio es: " + promedio);
				textArea.append(miEstudiante.getNombre() + " - Promedio: " + promedio + "\n");

				String registro = ModeloDatos.registrarEstudiante(miEstudiante);
				if (!registro.equals("Ok")) {
					JOptionPane.showMessageDialog(null, registro, "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
				}

			} else {
				lblResultado.setForeground(Color.RED);
				lblResultado.setText("Error: Notas fuera de rango (0-5)");
			}
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Por favor ingrese números válidos en las notas.");
		}
	}

	private void limpiar() {
		txtNombre.setText("");
		txtMateria.setText("");
		txtNota1.setText("");
		txtNota2.setText("");
		txtNota3.setText("");
		lblResultado.setText("Resultado: ");
		textArea.setText("");
		txtID.setText("");
	}

	private void consultarLista() {
		String listaConsultada = ModeloDatos.imprimirListaEstudiantes();
		textArea.setText(listaConsultada);
	}

	private void consultaIndividual() {
		String busquedaId = JOptionPane.showInputDialog("Ingrese el ID del estudiante a consultar");

		if (busquedaId == null || busquedaId.trim().isEmpty()) {
			return;
		}

		Estudiante estudianteEncontrado = ModeloDatos.consultarEstudiante(busquedaId);

		if (estudianteEncontrado != null) {

			txtID.setText(estudianteEncontrado.getId() + "");
			txtNombre.setText(estudianteEncontrado.getNombre());
			txtMateria.setText(estudianteEncontrado.getMateria());
			txtNota1.setText(estudianteEncontrado.getNota1() + "");
			txtNota2.setText(estudianteEncontrado.getNota2() + "");
			txtNota3.setText(estudianteEncontrado.getNota3() + "");
			lblResultado.setText("El promedio es: " + estudianteEncontrado.getPoromedio());

			String infoCompleta = "===== INFORMACIÓN DEL ESTUDIANTE =====\n" + "ID: " + estudianteEncontrado.getId()
					+ "\n" + "Nombre: " + estudianteEncontrado.getNombre() + "\n" + "Materia: "
					+ estudianteEncontrado.getMateria() + "\n" + "Nota 1: " + estudianteEncontrado.getNota1() + "\n"
					+ "Nota 2: " + estudianteEncontrado.getNota2() + "\n" + "Nota 3: " + estudianteEncontrado.getNota3()
					+ "\n" + "--------------------------------------\n" + "PROMEDIO FINAL: "
					+ estudianteEncontrado.getPoromedio() + "\n" + "======================================";

			textArea.setText(infoCompleta);

		} else {
			JOptionPane.showMessageDialog(null, "No se encontró el estudiante con ID: " + busquedaId, "ADVERTENCIA",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	private void eliminarEstudiante() {
		String eliminarEst = JOptionPane.showInputDialog("Ingrese el ID del estudiante a eliminar");

		if (eliminarEst == null || eliminarEst.trim().isEmpty()) {
			return;
		}

		Estudiante eliminarEstudiante = ModeloDatos.eliminarEstudiante(eliminarEst);
	}

	private void actualizarEstudiante() {
		try {

			if (txtID.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Debe consutlar el estudiante para actualizar.");
				return;
			}

			Estudiante estActualizado = new Estudiante();
			estActualizado.setId(Integer.parseInt(txtID.getText()));
			estActualizado.setNombre(txtNombre.getText());
			estActualizado.setMateria(txtMateria.getText());
			estActualizado.setNota1(Double.parseDouble(txtNota1.getText()));
			estActualizado.setNota2(Double.parseDouble(txtNota2.getText()));
			estActualizado.setNota3(Double.parseDouble(txtNota3.getText()));

			double promedio = procesos.CalcularPromedio(estActualizado);
			estActualizado.setPoromedio(promedio);

			String resultado = ModeloDatos.actualizarEstudiante(estActualizado);

			if (resultado.equals("Ok")) {
				JOptionPane.showMessageDialog(this, "Estudiante actualizado con éxito.");
				consultarLista();
			} else {
				JOptionPane.showMessageDialog(this, resultado, "Error", JOptionPane.ERROR_MESSAGE);
			}

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Asegúrese de que el ID y las notas sean números válidos.");
		}
	}

}
