package vista;

import javax.swing.*;
import java.awt.*;
import controlador.Coordinador;

public class VentanaAcercaDe extends JFrame {

    private Coordinador miCoordinador;

    public VentanaAcercaDe() {
        setTitle("Acerca de");
        setSize(300, 190);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1));

        JLabel lblDesarrollador = new JLabel("Desarrollador: Maicol Vera - ADSO 3231660", SwingConstants.CENTER);
        lblDesarrollador.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel lblAñoVigente = new JLabel("Fecha: 13/04/2026", SwingConstants.CENTER);

        JLabel lblUbicacion = new JLabel("Ubicación: Armenia, Quindío, Colombia", SwingConstants.CENTER);

        JLabel lblApp = new JLabel("Sistema de Cálculo IMC", SwingConstants.CENTER);

        add(lblDesarrollador);
        add(lblAñoVigente);
        add(lblUbicacion);
        add(lblApp);
    }

    public void setCoordinador(Coordinador miCoordinador) {
        this.miCoordinador = miCoordinador;
    }
}