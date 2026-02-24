import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VentanaPrincipal extends JFrame implements ActionListener {

	JPanel miPanel;
	JLabel miTitulo, EsNombre, EsMateria, EsNotas, EsN1, EsN2, EsN3;
	JButton miBoton, miBotonLimpiar;
	JTextField txtNombre, txtMateria, txtN1, txtN2, txtN3;
	JLabel ImpResultado;
	procesos misProcesos ;
	ModeloDatos miData = new ModeloDatos();


	public VentanaPrincipal() {
		iniciarComponentes();
		this.add(miPanel);
		setTitle("Principal");
		setSize(600, 500);
		setLocationRelativeTo(null);
		misProcesos = new procesos();
		

	}

	public void iniciarComponentes() {
		// Panel
		miPanel = new JPanel();
		miPanel.setLayout(null);
		miPanel.setBackground(Color.darkGray);

		// TxtTitulo
		miTitulo = new JLabel("Calcular promedio");
		miTitulo.setForeground(Color.WHITE);
		miTitulo.setFont(new Font("Arial", Font.BOLD, 22));
		miTitulo.setBounds(230, 20, 200, 30);
		miPanel.add(miTitulo);

		// TxtNombre
		EsNombre = new JLabel("Nombre:");
		EsNombre.setForeground(Color.WHITE);
		EsNombre.setBounds(50, 80, 80, 25);
		miPanel.add(EsNombre);

		// TxtMateria
		EsMateria = new JLabel("Materia:");
		EsMateria.setForeground(Color.WHITE);
		EsMateria.setBounds(50, 120, 80, 25);
		miPanel.add(EsMateria);

		// TxtN1
		EsN1 = new JLabel("Nota 1");
		EsN1.setForeground(Color.WHITE);
		EsN1.setBounds(125, 169, 80, 25);
		miPanel.add(EsN1);

		// TxtN2
		EsN2 = new JLabel("Nota 2");
		EsN2.setForeground(Color.WHITE);
		EsN2.setBounds(195, 169, 80, 25);
		miPanel.add(EsN2);

		// TxtN3
		EsN3 = new JLabel("Nota 3");
		EsN3.setForeground(Color.WHITE);
		EsN3.setBounds(265, 169, 80, 25);
		miPanel.add(EsN3);

		// TxtN
		EsNotas = new JLabel("Notas:");
		EsNotas.setForeground(Color.WHITE);
		EsNotas.setBounds(50, 200, 80, 25);
		miPanel.add(EsNotas);

		
		
		txtNombre = new JTextField();
		txtNombre.setBounds(130, 80, 200, 25);
		miPanel.add(txtNombre);

		txtMateria = new JTextField();
		txtMateria.setBounds(130, 120, 200, 25);
		miPanel.add(txtMateria);

		txtN1 = new JTextField();
		txtN1.setBounds(200, 200, 25, 25);
		miPanel.add(txtN1);

		txtN2 = new JTextField();
		txtN2.setBounds(270, 200, 25, 25);
		miPanel.add(txtN2);

		txtN3 = new JTextField();
		txtN3.setBounds(130, 200, 25, 25);
		miPanel.add(txtN3);

		miBoton = new JButton("Calcular");
		miBoton.setBounds(450, 400, 100, 30);
		miBoton.addActionListener(this);
		miPanel.add(miBoton);
		
		miBotonLimpiar = new JButton("Limpiar");
		miBotonLimpiar.setBounds(340, 400, 100, 30); 
		miBotonLimpiar.addActionListener(this);
		miPanel.add(miBotonLimpiar);

		//
		ImpResultado = new JLabel("Resultado:");
		ImpResultado.setForeground(Color.cyan); 
		ImpResultado.setFont(new Font("Arial", Font.PLAIN, 16));
		ImpResultado.setBounds(50, 250, 500, 100);
		miPanel.add(ImpResultado);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == miBoton) {
			try {
				Estudiantes miEstudiante = new Estudiantes();
				miEstudiante.setNombre(txtNombre.getText());
				miEstudiante.setMateria(txtMateria.getText());
				miEstudiante.setN1(Double.parseDouble(txtN1.getText()));
				miEstudiante.setN2(Double.parseDouble(txtN2.getText()));
				miEstudiante.setN3(Double.parseDouble(txtN3.getText()));

				misProcesos.calcularResultado(miEstudiante);
				
				String mensajeFinal = "<html>Estudiante: " + miEstudiante.getNombre() + "<br>" + "Promedio: "
	                    + String.format("%.2f", miEstudiante.getPromedio()) + "<br>" + "Estado: "
	                    + miEstudiante.getResultado() + "</html>";
	            ImpResultado.setText(mensajeFinal);
				
				misProcesos.registrar(miEstudiante);
				miData.guardarEstudiante(miEstudiante);
				miData.imprimirLista();
				

			} catch (Exception ex) {
				ImpResultado.setText("Error: Verifica que las notas sean números.");
				ImpResultado.setForeground(Color.RED);
			}
		}
		
		if (e.getSource() == miBotonLimpiar) {
	        txtNombre.setText("");
	        txtMateria.setText("");
	        txtN1.setText("");
	        txtN2.setText("");
	        txtN3.setText("");
	        ImpResultado.setText("Resultado:");
	        txtNombre.requestFocus(); 
	    }
		
	}

}
