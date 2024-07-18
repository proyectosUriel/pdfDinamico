package com.itext.pdfDinamico.Model;

public class DatosRecibir {

    private String docpdf;
    private String idInputBuscar;
    private String datosInsertar;
   

    // Constructor
    public DatosRecibir(String docpdf, String idInputBuscar, String datosInsertar) {
        this.docpdf = docpdf;
        this.idInputBuscar = idInputBuscar;
        this.datosInsertar = datosInsertar;
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

    public void setDatosInsertar(String datosInsertar) {
        this.datosInsertar = datosInsertar;
    }

    public String getIdInputBuscar() {
        return idInputBuscar;
    }

    public void setIdInputBuscar(String idInputBuscar) {
        this.idInputBuscar = idInputBuscar;
    }

}
