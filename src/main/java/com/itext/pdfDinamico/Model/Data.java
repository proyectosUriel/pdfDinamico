package com.itext.pdfDinamico.Model;

public class Data {
    private int id;
    private String nombre;
    private int valor;


    // Constructor por defecto
    public Data() {
    }

    // Constructor
    public Data(int id, String nombre, int valor) {
        this.id = id;
        this.nombre = nombre;
        this.valor = valor;
    }


    // Setters & Getters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
 
}
