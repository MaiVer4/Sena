package practicaVacaciones.veterinaria;

import java.util.HashMap;
import java.util.Scanner;

import clinica.Clinica;

public class veterinaria {
    HashMap<Integer, Cita> citas;

    public veterinaria() {
        this.citas = new HashMap<>();
        cargarCitasEjemplo();
    }

    public void cargarCitasEjemplo() {
        citas.put(12345, new Cita(1234, "Maicol Vera", "05/01/2026", "11:35", "Tom, Gato"));
        citas.put(12345, new Cita(1432, "Luis Sanchez", "11/01/2026", "09:35", "Lukas, Perro"));
    }

    public void iniciarInterfaz() {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Bienvenido al sistema de citas de la veterinaria ===");

        while (true) {

            System.out.print("\nIngrese su documento (0 para salir): ");
            int documento;

            try {
                documento = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Documento inválido. Intente nuevamente.");
                continue;
            }

            if (documento == 0) {
                System.out.println("Saliendo del sistema. ¡Hasta luego!");
                break;
            }

            Cita cita = citas.get(documento);

            if (cita == null) {
                System.out.println("No se encontró ninguna cita para el documento: " + documento);
                continue;
            }

            // Mostrar información de la cita
            System.out.println("\n--- Información de su cita ---");
            cita.mostrarInfo();

            // Preguntar si desea modificar
            System.out.print("\n¿Desea modificar la fecha y hora de su cita? (s/n): ");
            String opcion = sc.nextLine().trim().toLowerCase();

            if (opcion.equals("s") || opcion.equals("si")) {
                boolean editando = true;

                while (editando) {
                    System.out.println("\n¿Qué desea hacer?");
                    System.out.println("1 - Cambiar fecha y hora");
                    System.out.println("2 - Mostrar información actual");
                    System.out.println("0 - Terminar cambios");
                    System.out.print("Opción: ");

                    String opt = sc.nextLine().trim();

                    switch (opt) {
                        case "1":
                            System.out.print("Nueva fecha (ej: 15/12/2025): ");
                            String nuevaFecha = sc.nextLine().trim();

                            System.out.print("Nueva hora (ej: 09:30 AM): ");
                            String nuevaHora = sc.nextLine().trim();

                            cita.cambiarFechaHora(nuevaFecha, nuevaHora);
                            break;

                        case "2":
                            System.out.println("\n--- Información actualizada ---");
                            cita.mostrarInfo();
                            break;

                        case "0":
                            editando = false;
                            System.out.println("Cambios finalizados.");
                            break;

                        default:
                            System.out.println("Opción inválida.");
                    }
                }
            }

            // Mostrar estado final
            System.out.println("\n--- Estado final de la cita ---");
            cita.mostrarInfo();
        }

        sc.close();
    }

    public static void main(String[] args) {
        Clinica app = new Clinica();
        app.iniciarInterfaz();
    }
}
