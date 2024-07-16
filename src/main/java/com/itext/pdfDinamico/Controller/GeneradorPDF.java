package com.itext.pdfDinamico.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itext.pdfDinamico.Model.DatosRecibir;

@RestController
@RequestMapping("/api/pdf")
public class GeneradorPDF {

    @PostMapping("/generate")
    public ResponseEntity<String> generatePDF(@RequestBody DatosRecibir datos){

        String docpdf = datos.getDocpdf();
        String datosInsertar= datos.getDatosInsertar();
        String textoBuscar= datos.getTextoBuscar();

        String respuesta="PDF "+docpdf+"\n Datos insertar "+datosInsertar+"\n Texto a buscar "+textoBuscar;

        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
