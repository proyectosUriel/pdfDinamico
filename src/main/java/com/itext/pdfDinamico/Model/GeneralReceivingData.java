package com.itext.pdfDinamico.Model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeneralReceivingData {
    @JsonProperty("docpdf")
    private String docpdf;

    @JsonProperty("DocumentosIniciales")
    private String DocumentosIniciales;

    @JsonProperty("datosIniciales")
    private List<DateIniciales> datosIniciales;

    @JsonProperty("DocumentosFirmados")
    private String DocumentosFirmados;

    @JsonProperty("datosFirmados")
    private List<DateFirmados> datosFirmados;

    @JsonProperty("EvidenciasProceso")
    private String EvidenciasProceso;

    @JsonProperty("datosProceso")
    private List<DateEvidencias> datosEvidenciaProceso;
   
    // Constructor por defecto
    public GeneralReceivingData() {
    }

    // Constructor
    public GeneralReceivingData(String docpdf, String documentosIniciales, List<DateIniciales> datosIniciales,
            String documentosFirmados, List<DateFirmados> datosFirmados, String evidenciasProceso,
            List<DateEvidencias> datosEvidenciaProceso) {
        this.docpdf = docpdf;
        this.DocumentosIniciales = documentosIniciales;
        this.datosIniciales = datosIniciales;
        this.DocumentosFirmados = documentosFirmados;
        this.datosFirmados = datosFirmados;
        this.EvidenciasProceso = evidenciasProceso;
        this.datosEvidenciaProceso = datosEvidenciaProceso;
    }

    // Getters & Setters
    public String getDocpdf() {
        return this.docpdf;
    }

    public void setDocpdf(String docpdf) {
        this.docpdf = docpdf;
    }

    public String getDocumentosIniciales() {
        return this.DocumentosIniciales;
    }

    public void setDocumentosIniciales(String documentosIniciales) {
        this.DocumentosIniciales = documentosIniciales;
    }

    public List<DateIniciales> getDatosIniciales() {
        return this.datosIniciales;
    }

    public void setDatosIniciales(List<DateIniciales> datosIniciales) {
        this.datosIniciales = datosIniciales;
    }

    public String getDocumentosFirmados() {
        return this.DocumentosFirmados;
    }

    public void setDocumentosFirmados(String documentosFirmados) {
        this.DocumentosFirmados = documentosFirmados;
    }

    public List<DateFirmados> getDatosFirmados() {
        return this.datosFirmados;
    }

    public void setDatosFirmados(List<DateFirmados> datosFirmados) {
        this.datosFirmados = datosFirmados;
    }

    public String getEvidenciasProceso() {
        return this.EvidenciasProceso;
    }

    public void setEvidenciasProceso(String evidenciasProceso) {
        this.EvidenciasProceso = evidenciasProceso;
    }

    public List<DateEvidencias> getDatosEvidenciaProceso() {
        return this.datosEvidenciaProceso;
    }

    public void setDatosEvidenciaProceso(List<DateEvidencias> datosEvidenciaProceso) {
        this.datosEvidenciaProceso = datosEvidenciaProceso;
    }

}



