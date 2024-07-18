package com.itext.pdfDinamico.Model;

public class DatosRecibir {

    private String docpdf;
    private String textoBuscar;
    private String datosInsertar;
   

    // Constructor
    public DatosRecibir(String docpdf, String textoBuscar, String datosInsertar) {
        this.docpdf = docpdf;
        this.textoBuscar = textoBuscar;
        this.datosInsertar = datosInsertar;
    }

    // Getters & Setters
    public String getDocpdf() {
        return docpdf;
    }

    public void setDocpdf(String docpdf) {
        this.docpdf = docpdf;
    }

    public String getTextoBuscar() {
        return textoBuscar;
    }

    public void setTextoBuscar(String textoBuscar) {
        this.textoBuscar = textoBuscar;
    }

    public String getDatosInsertar() {
        return datosInsertar;
    }

    public void setDatosInsertar(String datosInsertar) {
        this.datosInsertar = datosInsertar;
    }

}
