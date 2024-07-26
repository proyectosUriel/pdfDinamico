package com.itext.pdfDinamico.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.List;

import com.itext.pdfDinamico.Model.Data;
import com.itext.pdfDinamico.Model.RequestData;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.kernel.pdf.canvas.parser.listener.LocationTextExtractionStrategy;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

public class PdfProcessor {
    public byte[] processPdf(byte[] pdfBytes, RequestData requestData) throws Exception {
        PdfReader reader = new PdfReader(new ByteArrayInputStream(pdfBytes));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDoc = new PdfDocument(reader, writer);
        Document document = new Document(pdfDoc);

        for (int i = 1; i <= pdfDoc.getNumberOfPages(); i++) {
            String pageContent = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(i), new LocationTextExtractionStrategy());

            // Aquí buscarías los textos y añadirías las tablas
            if (pageContent.contains(requestData.getApartadoDocumentosIniciales())) {
                // Añadir tabla para datosIniciales
                addTable(document, requestData.getDatosIniciales());
            }
            if (pageContent.contains(requestData.getApartadoDocumentosFirmados())) {
                // Añadir tabla para datosFirmados
                addTable(document, requestData.getDatosFirmados());
            }
            if (pageContent.contains(requestData.getApartadoEvidenciasProceso())) {
                // Añadir tabla para datosEvidencias
                addTable(document, requestData.getDatosEvidencias());
            }
        }

        document.close();
        pdfDoc.close();

        // Guardar el PDF en una ruta específica
        savePdfToFile(baos.toByteArray(), "/home/uriel_raigon/Downloads/mi_pdf_procesado.pdf");

        return baos.toByteArray();
    }

    private void addTable(Document document, List<Data> dataList) {
        Table table = new Table(new float[]{1, 2, 2});
        table.setWidth(500);  // Establece el ancho de la tabla en puntos

        // Añadir encabezados
        table.addHeaderCell(new Cell().add(new Paragraph("ID")));
        table.addHeaderCell(new Cell().add(new Paragraph("Nombre")));
        table.addHeaderCell(new Cell().add(new Paragraph("Valor")));

        // Añadir filas de datos
        for (Data data : dataList) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(data.getId()))));
            table.addCell(new Cell().add(new Paragraph(data.getNombre())));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(data.getValor()))));
        }

        document.add(table);
    }

    private void savePdfToFile(byte[] pdfBytes, String filePath) {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(pdfBytes);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error guardando el PDF en la ruta especificada: " + e.getMessage());
        }
    }
}
