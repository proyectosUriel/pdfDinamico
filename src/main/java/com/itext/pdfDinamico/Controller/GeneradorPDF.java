package com.itext.pdfDinamico.Controller;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itext.pdfDinamico.Model.DatosRecibir;
import com.itext.pdfDinamico.Service.PdfService;

@RestController
@RequestMapping("/pdf")
public class GeneradorPDF {

    @Autowired
    private PdfService pdfService;

    @PostMapping("/generate")
    public ResponseEntity<String> generatePDF(@RequestBody DatosRecibir datos){

        try {
            // Asumimos que el docpdf es un Base64 String del contenido del PDF
            byte[] pdfBytes = Base64.getDecoder().decode(datos.getDocpdf());
            String textoBuscar = datos.getTextoBuscar();
            String datosInsertar = datos.getDatosInsertar();

            // Ruta de salida del PDF modificado
            String outputPath = "~/Downloads/modificado.pdf";
            pdfService.modifyPdf(new String(pdfBytes), textoBuscar, datosInsertar, outputPath);

            return new ResponseEntity<>("PDF generado en: " + outputPath, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}



// El pdf suponenmos que vendra en Base64
// Los datos a insertar asumimos en vendran en CSV (valores separados por comas)