package com.itext.pdfDinamico.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.itext.pdfDinamico.Model.DatosRecibir;
import com.itextpdf.kernel.geom.Rectangle;
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

        // Construir la ruta completa al archivo PDF de salida
        String userHome = System.getProperty("user.home");
        String downloadPath = userHome + File.separator + "Downloads" + File.separator + "modified.pdf";
        
        PdfReader pdfReader = new PdfReader(tempFile.getAbsolutePath());
        PdfWriter pdfWriter = new PdfWriter(new FileOutputStream(downloadPath));
        PdfDocument pdfDoc = new PdfDocument(pdfReader, pdfWriter);

        Document document = new Document(pdfDoc);

        boolean encontrado = false;
        for (int i = 1; i <= pdfDoc.getNumberOfPages(); i++) {
            String pageContent = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(i));

            // Buscar el nombre del ID del input en lugar de un texto específico
            if (pageContent.contains(datosRecibir.getIdInputBuscar())) {
                encontrado = true;

                // Crear la tabla
                float[] columnWidths = {1, 3, 2}; // Anchos de las columnas
                Table table = new Table(columnWidths).useAllAvailableWidth();

                // Agregar encabezados de columna si es necesario
                table.addHeaderCell(new Cell().add(new Paragraph("ID")));
                table.addHeaderCell(new Cell().add(new Paragraph("Nombre")));
                table.addHeaderCell(new Cell().add(new Paragraph("Valor")));

                // Agregar filas a la tabla
                List<Map<String, Object>> datos = datosRecibir.getDatosInsertar();
                for (Map<String, Object> fila : datos) {
                    table.addCell(new Cell().add(new Paragraph(fila.get("id").toString())));
                    table.addCell(new Cell().add(new Paragraph(fila.get("nombre").toString())));
                    table.addCell(new Cell().add(new Paragraph(fila.get("valor").toString())));
                }

                // Agregar nueva página con la tabla
                pdfDoc.addNewPage();
                Rectangle pageSize = pdfDoc.getPage(1).getPageSize();
                document.add(table.setFixedPosition(1, 50, 100, pageSize.getWidth() - 100));

                break;
            }
        }

        pdfDoc.close();
        pdfWriter.close();
        pdfReader.close();
        tempFile.delete(); // Eliminar el archivo temporal después de usarlo

        if (!encontrado) {
            throw new IOException("Nombre del ID del input no encontrado en el PDF");
        }
    }
}
