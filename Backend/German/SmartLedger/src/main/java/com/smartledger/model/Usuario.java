package com.smartledger.model;

import java.time.LocalDateTime;

public class Usuario extends Persona {
    
    private Integer id;  // Puede ser null si no está guardado aún
    private String nombreUsuario;    
    private String passwordHash;  // Cambiado de contraseña a passwordHash
    private LocalDateTime fechaRegistro;

    // Constructor para nuevo usuario (sin ID)
    public Usuario(String nombre, String nombreUsuario, String passwordHash) {
        super(nombre);
        this.nombreUsuario = nombreUsuario;
        this.passwordHash = passwordHash;
        this.fechaRegistro = LocalDateTime.now();
    }

    // Constructor para usuario desde BD (con ID)
    public Usuario(Integer id, String nombre, String nombreUsuario, String passwordHash, LocalDateTime fechaRegistro) {
        super(nombre);
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.passwordHash = passwordHash;
        this.fechaRegistro = fechaRegistro;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public boolean verificarContraseña(String contraseña) {
        // Aquí después usaremos BCrypt
        return this.passwordHash.equals(contraseña);
    }
}