package com.itext.pdfDinamico.Controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itext.pdfDinamico.Service.PdfService;
import com.itextpdf.io.exceptions.IOException;


@RestController
@RequestMapping("/pdf")
public class PdfController {

     @Autowired
    private PdfService pdfService;

    @PostMapping("/generate")
    public ResponseEntity<InputStreamResource> highlightField(
            @RequestParam("base64pdf") String base64pdf,
            @RequestParam("fieldName") String fieldName) throws java.io.IOException {

        try {
            // Decodificar el archivo PDF desde Base64
            byte[] decodedPdf = Base64.getDecoder().decode(base64pdf);
            
            // Crear ByteArrayInputStream para el archivo PDF decodificado
            ByteArrayInputStream inputStream = new ByteArrayInputStream(decodedPdf);

            // Crear ByteArrayOutputStream para capturar el archivo PDF modificado
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            
            // Procesar el PDF usando el servicio
            pdfService.highlightField(inputStream, outputStream, fieldName);
            
            // Crear InputStreamResource para enviar el archivo modificado
            ByteArrayInputStream modifiedInputStream = new ByteArrayInputStream(outputStream.toByteArray());
            InputStreamResource resource = new InputStreamResource(modifiedInputStream);
            
            // Configurar los encabezados para la respuesta
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=modified.pdf");
            
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
            
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}










