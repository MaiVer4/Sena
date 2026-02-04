package com.smartledger;

import com.smartledger.model.*;
import com.smartledger.util.*;
import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Scanner;
import java.net.URL;

public class SmartLedger {

    private static Transaccion historial[] = new Transaccion[100];
    private static int contador = 0;
    private static HashMap<Integer, String> categorias = new HashMap<>();
    private static HashMap<String, Usuario> usuarios = new HashMap<>();
    private static Usuario usuarioActual = null;


    static void menuAutenticacion(Scanner sc) {
        int opcionLogin = 0;

        while (opcionLogin != 3 && usuarioActual == null) {
            System.out.println("\n=== AUTENTICACIÓN SMARTLEDGER ===");
            System.out.println("1 - Registrarse");
            System.out.println("2 - Iniciar Sesión");
            System.out.println("3 - Salir");
            System.out.print("Seleccione una opción: ");

            if (sc.hasNextInt()) {
                opcionLogin = sc.nextInt();
                sc.nextLine();
            } else {
                System.out.println("Entrada inválida. Intente de nuevo.");
                sc.nextLine();
                continue;
            }

            switch (opcionLogin) {
                case 1:
                    registrarUsuario(sc);
                    break;
                case 2:
                    if (iniciarSesion(sc)) {
                        System.out
                                .println("Inicio de sesión exitoso. Bienvenido, " + usuarioActual.getNombre() + "!\n");
                        return;
                    } else {
                        System.out.println("Usuario o contraseña incorrectos.");
                    }
                    break;
                case 3:
                    System.out.println("Saliendo...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    static void registrarUsuario(Scanner sc) {
        System.out.println("\n--- REGISTRO DE USUARIO ---");
        System.out.print("Ingrese nombre de usuario (será su clave de acceso): ");
        String nombreUsuario = sc.nextLine();

        if (usuarios.containsKey(nombreUsuario)) {
            System.out.println("El nombre de usuario ya existe.");
            return;
        }

        System.out.print("Ingrese su nombre: ");
        String nombre = sc.nextLine();

        Console console = System.console();
        String contraseña;

        if (console != null) {
            char[] passArray = console.readPassword("Ingrese su contraseña: ");
            contraseña = new String(passArray);
        } else {
          
            System.out.print("Ingrese su contraseña: ");
            contraseña = sc.nextLine();
        }

        Usuario nuevoUsuario = new Usuario(nombre, nombreUsuario, contraseña);
        usuarios.put(nombreUsuario, nuevoUsuario);
        System.out.println("Usuario registrado con éxito!");
    }

    static boolean iniciarSesion(Scanner sc) {
        System.out.println("\n--- INICIO DE SESIÓN ---");
        System.out.print("Ingrese nombre de usuario: ");
        String nombreUsuario = sc.nextLine();
        Console console = System.console();
        String contraseña;

        if (console != null) {
            char[] passArray = console.readPassword("Ingrese contraseña: ");
            contraseña = new String(passArray);
        } else {
            System.out.print("Ingrese contraseña: ");
            contraseña = sc.nextLine();
        }

        Usuario u = usuarios.get(nombreUsuario);

        if (u != null && u.verificarContraseña(contraseña)) {
            usuarioActual = u; // Asignar el usuario logueado
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        categorias.put(1, "Ventas");
        categorias.put(2, "Servicios");
        categorias.put(3, "Trasporte");
        categorias.put(4, "Marketing");
        categorias.put(5, "Otros");

        menuAutenticacion(sc);

        int opcion = 0;

        while (opcion != 4) {
            System.out.println("\n=== SMARTLEDGER - Menú Principal ===");
            System.out.println("    ---- " + usuarioActual.getNombre() + " Puedes: ----");
            System.out.println("1 - Agregar transaccion");
            System.out.println("2 - Ver historial");
            System.out.println("3 - Ver resumen del dia");
            System.out.println("4 - Salir");
            System.out.print("Seleecione una opcion: ");

            // Manejo de errores para la entrada del menú principal
            if (sc.hasNextInt()) {
                opcion = sc.nextInt();
                sc.nextLine();
            } else {
                System.out.println("Entrada inválida. Intente de nuevo.");
                sc.nextLine();
                continue;
            }

            switch (opcion) {
                case 1:
                    agregarTransaccion(sc);
                    break;
                case 2:
                    verHistorial();
                    break;
                case 3:
                    verResumen();
                    break;
                case 4:
                    System.out.println("Saliendo del sistema. ¡Adiós!");
                    break;
                default:
                    System.out.println("Opcion Invalida, Intenta nuevamente!");
                    break;
            }
        }
    }

    static void agregarTransaccion(Scanner sc) {

        if (contador >= historial.length) {
            System.out.println("No puede agregar transaciones (límite alcanzado)");
            return;
        }

        System.out.println("\n--- NUEVA TRANSACCION ---");
        System.out.print("Ingresa el monto: ");

        double monto;
        while (!sc.hasNextDouble()) {
            System.out.println("Monto inválido. Intenta de nuevo: ");
            sc.nextLine();
        }
        monto = sc.nextDouble();
        sc.nextLine();

        System.out.println("--------------------------");
        String tipo = "";
        while (true) {
            System.out.print("Tipo (Ingreso/Gasto): ");
            tipo = sc.nextLine().trim().toLowerCase();

            if (tipo.equals("ingreso") || tipo.equals("gasto")) {
                break;
            } else {
                System.out.println("Tipo inválido. Intenta nuevamente!");
            }
        }

        System.out.println("\nCategorias disponibles:");
        for (int key : categorias.keySet()) {
            System.out.println(key + " - " + categorias.get(key));
        }

        System.out.print("Seleccione una categoria(numero): ");

        int opcionCategoria;
        while (!sc.hasNextInt()) {
            System.out.println("Opción de categoría inválida. Intaenta de nuevo: ");
            sc.nextLine();
        }
        opcionCategoria = sc.nextInt();
        sc.nextLine();

        String categoria = categorias.getOrDefault(opcionCategoria, "Otros");

        System.out.print("Ingresa la fecha (DD/MM/AA): ");
        String fecha = sc.nextLine();

        System.out.print("Descripcion: ");
        String descripcion = sc.nextLine();

        Transaccion nueva = new Transaccion(monto, tipo, categoria, fecha, descripcion,
                usuarioActual.getNombreUsuario());
        historial[contador] = nueva;
        contador++;

        System.out.println("Transacion realizada con éxito");
    }

    static void verHistorial() {
        System.out
                .println("\n--- HISTORIAL DE TRANSACCIONES DE " + usuarioActual.getNombre().toUpperCase() + " ----");

        boolean hayTransacciones = false;
        for (int i = 0; i < contador; i++) {
            Transaccion t = historial[i];

            if (t.getNombreUsuario().equals(usuarioActual.getNombreUsuario())) {
                System.out.println("--------------------------------------");
                System.out.println("=== TRANSACCIÓN " + (i + 1) + " ===");
                System.out.println("Monto: " + t.getMonto());
                System.out.println("Tipo: " + t.getTipo());
                System.out.println("Categoria: " + t.getCategoria());
                System.out.println("Fecha: " + t.getFecha());
                System.out.println("Descripcion: " + t.getDescripcion());
                hayTransacciones = true;
            }
        }

        if (!hayTransacciones) {
            System.out.println("No hay transacciones registradas para este usuario.");
        }
    }

    static String generarResumenIA(String resumenTexto) {
        String resp = "";

        try {
            URL url = new URL("https://openrouter.ai/api/v1/chat/completions");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");

            // Cargar API Key
            String apiKey = Env.get("OPENROUTER_API_KEY");
            if (apiKey == null || apiKey.isEmpty()) {
                return "Error: API Key no encontrada en .env";
            }
            con.setRequestProperty("Authorization", "Bearer " + apiKey);
            con.setDoOutput(true);

            
            String prompt = "Actúa como un asesor financiero cercano y amigable. " +
                    "Explica el estado financiero de forma sencilla, positiva y fácil de entender. " +
                    "NO uses fórmulas matemáticas, porcentajes técnicos ni lenguaje contable. " +
                    "Habla directamente al usuario, como si le dieras un consejo personal. " +
                    "Usa párrafos cortos y un tono motivador.\n\n" +
                    "Datos del día:\n" + resumenTexto;

            String promptSeguro = prompt
                    .replace("\\", "\\\\")
                    .replace("\"", "\\\"") 
                    .replace("\n", "\\n") 
                    .replace("\r", "");

            String jsonInput = "{\n" +
                    "  \"model\": \"deepseek/deepseek-chat\",\n" +
                    "  \"messages\": [\n" +
                    "    { \"role\": \"system\", \"content\": \"Eres una IA experta financiera.\" },\n" +
                    "    { \"role\": \"user\", \"content\": \"" + promptSeguro + "\" }\n" +
                    "  ]\n" +
                    "}";

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInput.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            
            int status = con.getResponseCode();
            InputStream stream;

           
            
            if (status > 299) {
                stream = con.getErrorStream();
            } else {
                stream = con.getInputStream();
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(stream, "utf-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            resp = response.toString();

            if (status > 299) {
                return "Error de API (" + status + "): " + resp;
            }

            String finalText = "";
            String searchKey = "\"content\":\"";
            int contentIndex = resp.indexOf(searchKey);

            if (contentIndex != -1) {
                contentIndex += searchKey.length();
        
                int end = resp.lastIndexOf("\""); 

                
                boolean isEscaped = false;
                for (int i = contentIndex; i < resp.length(); i++) {
                    char c = resp.charAt(i);
                    if (c == '\\') {
                        isEscaped = !isEscaped;
                    } else if (c == '"' && !isEscaped) {
                        finalText = resp.substring(contentIndex, i);
                        break;
                    } else {
                        isEscaped = false;
                    }
                }
            }

            if (finalText.isEmpty()) {
                System.out.println("DEBUG JSON: " + resp);
                return "Error al leer el contenido JSON.";
            }

            return finalText.replace("\\n", "\n").replace("\\\"", "\"");

        } catch (Exception e) {
            e.printStackTrace(); 
            return "Error de conexión: " + e.getMessage();
        }
    }

    static void verResumen() {
        System.out.println("\n--- RESUMEN DEL DÍA DE " + usuarioActual.getNombre().toUpperCase() + " ----");

        double ingresos = 0;
        double gastos = 0;

        for (int i = 0; i < contador; i++) {
            Transaccion t = historial[i];
            if (t.getNombreUsuario().equals(usuarioActual.getNombreUsuario())) {
                if (t.getTipo().equalsIgnoreCase("ingreso")) {
                    ingresos += t.getMonto();
                } else if (t.getTipo().equalsIgnoreCase("gasto")) {
                    gastos += t.getMonto();
                }
            }
        }

        System.out.println("Total de Ingresos: " + ingresos);
        System.out.println("Total de Gastos: " + gastos);
        System.out.println("Saldo Neto: " + (ingresos - gastos));

        String datos = "Ingresos: " + ingresos + ", Gastos: " + gastos + ", Saldo: " + (ingresos - gastos);

        System.out.println("\n--- ANÁLISIS IA ---");

        String analisisIA = generarResumenIA(datos);

        analisisIA = analisisIA
                .replace("\\n", "\n")
                .replace("\\\\n", "\n")
                .replace("###", "")
                .replace("####", "")
                .replace("**", "")
                .replace("\\[", "")
                .replace("\\]", "")
                .replace("\\", "");

        System.out.println(analisisIA);

    }
}