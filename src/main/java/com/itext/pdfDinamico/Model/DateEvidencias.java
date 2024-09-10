package com.itext.pdfDinamico.Model;

import java.sql.Date;

public class DateEvidencias {
    private String etiqueta_nombre;
    private String nombre;
    private String etiqueta_fecha;
    private Date fecha;
    private String etiqueta_recpetor;
    private String receptor;
    private String etiqueta_detalle;
    private String detalle;

    // Constructor por defecto
    public DateEvidencias() {
    }

    // Cosntructor
    public DateEvidencias(String etiqueta_nombre, String nombre, String etiqueta_fecha, Date fecha,
            String etiqueta_recpetor, String receptor, String etiqueta_detalle, String detalle) {
        this.etiqueta_nombre = etiqueta_nombre;
        this.nombre = nombre;
        this.etiqueta_fecha = etiqueta_fecha;
        this.fecha = fecha;
        this.etiqueta_recpetor = etiqueta_recpetor;
        this.receptor = receptor;
        this.etiqueta_detalle = etiqueta_detalle;
        this.detalle = detalle;
    }

    // Getters & Setters
    public String getEtiqueta_nombre() {
        return etiqueta_nombre;
    }

    public void setEtiqueta_nombre(String etiqueta_nombre) {
        this.etiqueta_nombre = etiqueta_nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEtiqueta_fecha() {
        return etiqueta_fecha;
    }

    public void setEtiqueta_fecha(String etiqueta_fecha) {
        this.etiqueta_fecha = etiqueta_fecha;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEtiqueta_recpetor() {
        return etiqueta_recpetor;
    }

    public void setEtiqueta_recpetor(String etiqueta_recpetor) {
        this.etiqueta_recpetor = etiqueta_recpetor;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public String getEtiqueta_detalle() {
        return etiqueta_detalle;
    }

    public void setEtiqueta_detalle(String etiqueta_detalle) {
        this.etiqueta_detalle = etiqueta_detalle;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
 
}
