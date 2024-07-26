package com.itext.pdfDinamico.Model;

import java.util.List;

public class RequestData {
    private String docpdf;
    private String apartadoDocumentosIniciales;
    private List<Data> datosIniciales;
    private String apartadoDocumentosFirmados;
    private List<Data> datosFirmados;
    private String apartadoEvidenciasProceso;
    private List<Data> datosEvidencias;

    // Constructor por defecto
    public RequestData() {
    }

    // Constructor
    public RequestData(String docpdf, String apartadoDocumentosIniciales, List<Data> datosIniciales,
            String apartadoDocumentosFirmados, List<Data> datosFirmados, String apartadoEvidenciasProceso,
            List<Data> datosEvidencias) {
        this.docpdf = docpdf;
        this.apartadoDocumentosIniciales = apartadoDocumentosIniciales;
        this.datosIniciales = datosIniciales;
        this.apartadoDocumentosFirmados = apartadoDocumentosFirmados;
        this.datosFirmados = datosFirmados;
        this.apartadoEvidenciasProceso = apartadoEvidenciasProceso;
        this.datosEvidencias = datosEvidencias;
    }

    // Getters & Setters
    public String getDocpdf() {
        return docpdf;
    }

    public void setDocpdf(String docpdf) {
        this.docpdf = docpdf;
    }

    public String getApartadoDocumentosIniciales() {
        return apartadoDocumentosIniciales;
    }

    public void setApartadoDocumentosIniciales(String apartadoDocumentosIniciales) {
        this.apartadoDocumentosIniciales = apartadoDocumentosIniciales;
    }

    public List<Data> getDatosIniciales() {
        return datosIniciales;
    }

    public void setDatosIniciales(List<Data> datosIniciales) {
        this.datosIniciales = datosIniciales;
    }

    public String getApartadoDocumentosFirmados() {
        return apartadoDocumentosFirmados;
    }

    public void setApartadoDocumentosFirmados(String apartadoDocumentosFirmados) {
        this.apartadoDocumentosFirmados = apartadoDocumentosFirmados;
    }

    public List<Data> getDatosFirmados() {
        return datosFirmados;
    }

    public void setDatosFirmados(List<Data> datosFirmados) {
        this.datosFirmados = datosFirmados;
    }

    public String getApartadoEvidenciasProceso() {
        return apartadoEvidenciasProceso;
    }

    public void setApartadoEvidenciasProceso(String apartadoEvidenciasProceso) {
        this.apartadoEvidenciasProceso = apartadoEvidenciasProceso;
    }

    public List<Data> getDatosEvidencias() {
        return datosEvidencias;
    }

    public void setDatosEvidencias(List<Data> datosEvidencias) {
        this.datosEvidencias = datosEvidencias;
    }

}



