package com.itext.pdfDinamico.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itext.pdfDinamico.Model.GeneralReceivingData;
import com.itext.pdfDinamico.Service.PdfFieldHighlight;


@RestController
@RequestMapping("/pdf")
public class PdfController {

    @Autowired
    private PdfFieldHighlight pdfFieldHighlight;

    @PostMapping("/generate")
    public ResponseEntity<String> generatePdf(@RequestBody GeneralReceivingData data) {
        try {
            // Llama al servicio que genera el PDF con los datos recibidos
            pdfFieldHighlight.generatePdf(data);
            return ResponseEntity.ok("PDF generado correctamente y guardado.");
        } catch (Exception e) {
            // Manejo de errores en caso de excepción
            e.printStackTrace();
            return ResponseEntity.status(500).body("Ocurrió un error al generar el PDF: " + e.getMessage());
        }
    }
}










