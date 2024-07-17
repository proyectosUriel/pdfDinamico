package com.itext.pdfDinamico.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;


public class PdfService {
    public void modifyPdf(String pdfContent, String textoBuscar, String datosInsertar, String outputPath) throws IOException {
        // Convertir el contenido del PDF a un byte array stream para ser manipulado por iText
        File tempFile = File.createTempFile("tempPdf", ".pdf");
        Files.write(tempFile.toPath(), pdfContent.getBytes());

        PdfReader pdfReader = new PdfReader(tempFile.getAbsolutePath());
        PdfWriter pdfWriter = new PdfWriter(new FileOutputStream(outputPath));
        PdfDocument pdfDoc = new PdfDocument(pdfReader, pdfWriter);

        boolean encontrado = false;
        for (int i = 1; i <= pdfDoc.getNumberOfPages(); i++) {
            String pageContent = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(i));
            if (pageContent.contains(textoBuscar)) {
                encontrado = true;
                // Crear un nuevo documento basado en el existente
                Document document = new Document(pdfDoc);
                float[] columnWidths = {1, 5}; // Definir los anchos de las columnas
                Table table = new Table(columnWidths);

                // Asumiendo que datosInsertar está en un formato separable, por ejemplo, CSV
                String[] filas = datosInsertar.split("\n");
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
