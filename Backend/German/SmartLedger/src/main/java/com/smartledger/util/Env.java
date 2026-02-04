package com.smartledger.util;
import java.io.*;

public class Env {

    private static java.util.HashMap<String, String> variables = new java.util.HashMap<>();

    static {
        try {
            File f = new File(".env");

            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;

            while ((line = br.readLine()) != null) {
                // Ignorar líneas vacías o comentarios (#)
                if (line.trim().isEmpty() || line.trim().startsWith("#")) {
                    continue; 
                }
                
                if (line.contains("=")) {
                    String[] parts = line.split("=", 2);
                    
                    String key = parts[0].trim();
                    String value = parts[1].trim(); 
                    
                   
                    if (value.startsWith("\"") && value.endsWith("\"")) {
                        value = value.substring(1, value.length() - 1);
                    } else if (value.startsWith("'") && value.endsWith("'")) {
                        value = value.substring(1, value.length() - 1);
                    }
                    // ----------------------------------------
                    
                    variables.put(key, value);
                }
            }

            br.close();
        } catch (Exception e) {
            System.out.println("WARNING: No se pudo cargar .env: " + e.getMessage());
        }
    }

    public static String get(String key) {
        return variables.getOrDefault(key, "");
    }
}