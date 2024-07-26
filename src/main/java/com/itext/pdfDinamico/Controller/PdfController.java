package com.itext.pdfDinamico.Controller;

import org.springframework.web.bind.annotation.*;

import com.itext.pdfDinamico.Model.RequestData;
import com.itext.pdfDinamico.Service.PdfProcessor;

import org.apache.commons.codec.binary.Base64;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    @PostMapping("/process")
    public String processPdf(@RequestBody RequestData requestData) {
        try {
            // Decodificar el PDF de base64
            byte[] pdfBytes = Base64.decodeBase64(requestData.getDocpdf());

            // Procesar el PDF
            PdfProcessor processor = new PdfProcessor();
            byte[] processedPdfBytes = processor.processPdf(pdfBytes, requestData);

            // Aquí podrías devolver el PDF procesado o guardarlo en algún lugar
            return "PDF procesado con éxito";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error procesando el PDF: " + e.getMessage();
        }
    }
}
