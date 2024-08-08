package com.itext.pdfDinamico.Controller;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itext.pdfDinamico.Model.GeneralReceivingData;
import com.itext.pdfDinamico.Service.PdfFieldHighlight;
import com.itext.pdfDinamico.Service.PdfService;


@RestController
@RequestMapping("/pdf")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @Autowired
    private PdfFieldHighlight pdfFieldHighlight;

    @PostMapping("/generate")
    public ResponseEntity<String> highlightField(@RequestBody GeneralReceivingData data) {

         try {
            // Decodificar el archivo PDF desde Base64
            byte[] decodedPdf = Base64.getDecoder().decode(data.getDocpdf());

            // Crear ByteArrayInputStream para el archivo PDF decodificado
            ByteArrayInputStream inputStream = new ByteArrayInputStream(decodedPdf);

            // Crear ByteArrayOutputStream para capturar el archivo PDF modificado
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            // Llamar al servicio para procesar el PDF
            pdfService.highlightField(inputStream, outputStream, data.getDocumentosIniciales());

            // Hacer prueba
            pdfFieldHighlight.coor();

            // Guardar el PDF modificado en un archivo en el sistema de archivos
            String outputPath = "/home/uriel_raigon/Downloads/modified.pdf"; // Actualiza la ruta según sea necesario
            Files.write(Paths.get(outputPath), outputStream.toByteArray());

            // Retornar una respuesta con la ruta del archivo modificado
            return ResponseEntity.ok("PDF modificado guardado en: " + outputPath);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar el PDF");
        }
    }
}










