package com.itext.pdfDinamico.Model;

public class DatosRecibir {

    private String docpdf;
    private String datosInsertar;
    private String textoBuscar;

    // Constructor por defecto
    public DatosRecibir() {
    }
    
    // Constructor
    public DatosRecibir(String docpdf, String datos, String textoBuscar) {
        this.docpdf = docpdf;
        this.datosInsertar = datos;
        this.textoBuscar = textoBuscar;
    }

    // Getters & Setters
    public String getDocpdf() {
        return docpdf;
    }

    public void setDocpdf(String docpdf) {
        this.docpdf = docpdf;
    }

    public String getDatosInsertar() {
        return datosInsertar;
    }

    public void setDatosInsertar(String datos) {
        this.datosInsertar = datos;
    }

    public String getTextoBuscar() {
        return textoBuscar;
    }

    public void setTextoBuscar(String textoBuscar) {
        this.textoBuscar = textoBuscar;
    }
}
