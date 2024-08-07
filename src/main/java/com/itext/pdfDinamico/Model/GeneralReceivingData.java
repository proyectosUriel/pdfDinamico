package com.itext.pdfDinamico.Model;

import java.util.List;

public class GeneralReceivingData {
    private String docpdf;
    private String DocumentosIniciales;
    private List<DateIniciales> datosIniciales;
    private String DocumentosFirmados;
    private List<DateFirmados> datosFirmados;
    private String EvidenciasProceso;
    private List<DateEvidencias> datosEvidenciaProceso;
   
    // Constructor por defecto
    public GeneralReceivingData() {
    }

    // Constructor
    public GeneralReceivingData(String docpdf, String documentosIniciales, List<DateIniciales> datosIniciales,
            String documentosFirmados, List<DateFirmados> datosFirmados, String evidenciasProceso,
            List<DateEvidencias> datosEvidenciaProceso) {
        this.docpdf = docpdf;
        DocumentosIniciales = documentosIniciales;
        this.datosIniciales = datosIniciales;
        DocumentosFirmados = documentosFirmados;
        this.datosFirmados = datosFirmados;
        EvidenciasProceso = evidenciasProceso;
        this.datosEvidenciaProceso = datosEvidenciaProceso;
    }

    // Getters & Setters
    public String getDocpdf() {
        return docpdf;
    }

    public void setDocpdf(String docpdf) {
        this.docpdf = docpdf;
    }

    public String getDocumentosIniciales() {
        return DocumentosIniciales;
    }

    public void setDocumentosIniciales(String documentosIniciales) {
        DocumentosIniciales = documentosIniciales;
    }

    public List<DateIniciales> getDatosIniciales() {
        return datosIniciales;
    }

    public void setDatosIniciales(List<DateIniciales> datosIniciales) {
        this.datosIniciales = datosIniciales;
    }

    public String getDocumentosFirmados() {
        return DocumentosFirmados;
    }

    public void setDocumentosFirmados(String documentosFirmados) {
        DocumentosFirmados = documentosFirmados;
    }

    public List<DateFirmados> getDatosFirmados() {
        return datosFirmados;
    }

    public void setDatosFirmados(List<DateFirmados> datosFirmados) {
        this.datosFirmados = datosFirmados;
    }

    public String getEvidenciasProceso() {
        return EvidenciasProceso;
    }

    public void setEvidenciasProceso(String evidenciasProceso) {
        EvidenciasProceso = evidenciasProceso;
    }

    public List<DateEvidencias> getDatosEvidenciaProceso() {
        return datosEvidenciaProceso;
    }

    public void setDatosEvidenciaProceso(List<DateEvidencias> datosEvidenciaProceso) {
        this.datosEvidenciaProceso = datosEvidenciaProceso;
    }

}



