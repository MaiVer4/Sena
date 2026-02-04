package com.smartledger.model;
public class Registro {
    protected String fecha;
    protected String descripcion;

    public Registro(String fecha, String descripcion){
        this.fecha = fecha;
        this.descripcion = descripcion;
    }

    public String getFecha(){
        return fecha;
    }

    public String getDescripcion(){
        return descripcion;
    }
}
