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
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.renderer.TableRenderer;

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

        // Obtener el tamaño de la página
        Rectangle pageSize = pdfDoc.getPage(1).getPageSize();
        float pageHeight = pageSize.getHeight();
        float pageWidth = pageSize.getWidth();

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

        // Calcular la altura de la tabla
        TableRenderer tableRenderer = (TableRenderer) table.createRendererSubTree();
        tableRenderer.setParent(document.getRenderer());
        LayoutArea area = new LayoutArea(0, new Rectangle(0, 0, pageWidth, pageHeight));
        LayoutContext layoutContext = new LayoutContext(area);
        tableRenderer.layout(layoutContext);
        float tableHeight = tableRenderer.getOccupiedArea().getBBox().getHeight();

        // Verificar el espacio disponible en la página actual
        float marginTop = 30; // Margen superior en puntos
        float additionalMargin = 10; // Margen adicional entre el input y la tabla
        float spaceAvailable = pageHeight - inputY - marginTop; // Espacio disponible debajo del input

        // Añadir la tabla a la página actual o a una nueva página si no cabe
        float y;
        if (tableHeight > spaceAvailable) {
            // Si la tabla no cabe, crear una nueva página
            pdfDoc.addNewPage();
            document = new Document(pdfDoc); // Crear un nuevo Document para la nueva página
            pageSize = pdfDoc.getPage(pdfDoc.getNumberOfPages()).getPageSize(); // Obtener el tamaño de la nueva página
            pageHeight = pageSize.getHeight();
            pageWidth = pageSize.getWidth();
            y = pageHeight - tableHeight - marginTop; // Posición Y para la tabla en la nueva página
        } else {
            // Si cabe en la página actual
            y = inputY - marginTop - additionalMargin; // Ajustar posición Y
        }

        // Agregar la tabla
        document.add(table.setFixedPosition(pdfDoc.getNumberOfPages(), inputX, y, pageWidth - inputX - 10));

        // Si hubo una nueva página, añadir el contenido que sigue después de la tabla en esa nueva página
        if (tableHeight > spaceAvailable) {
            // Añadir otros contenidos en la nueva página
            // Aquí debes añadir el contenido adicional que debe ir después de la tabla
            // document.add(new Paragraph("Contenido adicional después de la tabla..."));
        }

        pdfDoc.close();
        pdfWriter.close();
        pdfReader.close();
        tempFile.delete(); // Eliminar el archivo temporal después de usarlo
    }
}
