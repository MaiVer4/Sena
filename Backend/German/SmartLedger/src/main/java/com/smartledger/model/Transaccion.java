package com.smartledger.model;

public class Transaccion extends Registro {

    private Integer id;  // ID de la transacción
    private Integer usuarioId;  // ID del usuario (foreign key)
    private double monto;
    private String tipo;
    private String categoria;
    private String nombreUsuario;  // Mantener por compatibilidad

    // Constructor para nueva transacción (sin ID)
    public Transaccion(double monto, String tipo, String categoria, String fecha, String descripcion, String nombreUsuario) {
        super(fecha, descripcion);
        this.monto = monto;
        this.tipo = tipo;
        this.categoria = categoria;
        this.nombreUsuario = nombreUsuario;
    }

    // Constructor para transacción desde BD (con ID)
    public Transaccion(Integer id, Integer usuarioId, double monto, String tipo, String categoria, 
                       String fecha, String descripcion, String nombreUsuario) {
        super(fecha, descripcion);
        this.id = id;
        this.usuarioId = usuarioId;
        this.monto = monto;
        this.tipo = tipo;
        this.categoria = categoria;
        this.nombreUsuario = nombreUsuario;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public double getMonto() {
        return monto;
    }

    public String getTipo() {
        return tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }
}