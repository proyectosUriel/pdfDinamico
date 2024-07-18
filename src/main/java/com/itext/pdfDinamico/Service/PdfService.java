package com.itext.pdfDinamico.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.stereotype.Service;

import com.itext.pdfDinamico.Model.DatosRecibir;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

@Service
public class PdfService {
     public void modifyPdf(DatosRecibir datosRecibir) throws IOException {
        // Convertir el contenido del PDF a un byte array stream para ser manipulado por iText
        byte[] pdfBytes = java.util.Base64.getDecoder().decode(datosRecibir.getDocpdf());
        File tempFile = File.createTempFile("tempPdf", ".pdf");
        Files.write(tempFile.toPath(), pdfBytes);

        PdfReader pdfReader = new PdfReader(tempFile.getAbsolutePath());
        PdfWriter pdfWriter = new PdfWriter(new FileOutputStream("~/Downloads"));
        PdfDocument pdfDoc = new PdfDocument(pdfReader, pdfWriter);

        boolean encontrado = false;
        for (int i = 1; i <= pdfDoc.getNumberOfPages(); i++) {
            String pageContent = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(i));
            if (pageContent.contains(datosRecibir.getTextoBuscar())) {
                encontrado = true;
                // Crear un nuevo documento basado en el existente
                Document document = new Document(pdfDoc);
                float[] columnWidths = { 1, 5 }; // Definir los anchos de las columnas
                Table table = new Table(columnWidths);

                // Asumiendo que datosInsertar está en un formato separable, por ejemplo, CSV
                String[] filas = datosRecibir.getDatosInsertar().split("\n");
                for (String fila : filas) {
                    String[] columnas = fila.split(",");
                    for (String columna : columnas) {
                        table.addCell(new Cell().add(new Paragraph(columna)));
                    }
                }
                // Insertar la tabla después de encontrar el texto
                pdfDoc.addNewPage(i + 1);
                document.add(table);
                break;
            }
        }

        pdfDoc.close();
        pdfWriter.close();
        pdfReader.close();
        tempFile.delete();

        if (!encontrado) {
            throw new IOException("Texto a buscar no encontrado en el PDF");
        }
    }
}
