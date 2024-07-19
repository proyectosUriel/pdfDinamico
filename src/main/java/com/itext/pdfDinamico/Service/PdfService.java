package com.itext.pdfDinamico.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itext.pdfDinamico.Model.DatosRecibir;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;


@Service
public class PdfService {

    @Autowired
    private FormFieldExtractorService formFieldExtractorService;

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

        // Obtener la posición del último campo
        Rectangle lastFieldRect = formFieldExtractorService.getLastFieldPosition(tempFile.getAbsolutePath());

        // Coordenadas del último campo
        float inputX = lastFieldRect.getX();
        float inputY = lastFieldRect.getY();

        // Ajustar la posición de la tabla
        float marginTop = 50; // Margen superior en puntos
        float x = inputX; // Coordenada X en puntos
        float y = inputY - marginTop; // Coordenada Y en puntos, ajustada con margen

        // Crear la tabla
        float[] columnWidths = {1, 3, 2}; // Anchos de las columnas
        Table table = new Table(columnWidths).useAllAvailableWidth();

        // Agregar encabezados de columna
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
        document.add(table.setFixedPosition(1, x, y, pdfDoc.getPage(1).getPageSize().getWidth() - 100));

        pdfDoc.close();
        pdfWriter.close();
        pdfReader.close();
        tempFile.delete(); // Eliminar el archivo temporal después de usarlo
    }
}

