package com.itext.pdfDinamico.Model;

import java.util.List;
import java.util.Map;

public class DatosRecibir {

    private String docpdf;
    private String idInputBuscar;
    private List<Map<String, Object>> datosInsertar;
   

    // Constructor
    public DatosRecibir(String docpdf, String idInputBuscar, List<Map<String, Object>> datosInsertar) {
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

    public List<Map<String, Object>> getDatosInsertar() {
        return datosInsertar;
    }

    public void setDatosInsertar(List<Map<String, Object>> datosInsertar) {
        this.datosInsertar = datosInsertar;
    }

    public String getIdInputBuscar() {
        return idInputBuscar;
    }

    public void setIdInputBuscar(String idInputBuscar) {
        this.idInputBuscar = idInputBuscar;
    }

}
