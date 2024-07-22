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
    // Convertir el contenido del PDF a un byte array stream para ser manipulado por
    // iText
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
    float pageHeight = pdfDoc.getPage(1).getPageSize().getHeight();

    // Ajustar la posición de la tabla
    float marginTop = 30; // Margen superior en puntos
    float x = inputX; // Coordenada X en puntos
    float y = inputY - marginTop; // Coordenada Y en puntos, ajustada con margen

    // Crear la tabla
    float[] columnWidths = { 1, 3, 2 }; // Anchos de las columnas
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

    // Verificar coordenadas de la tabla y la página
    System.out.println("inputX: " + inputX);
    System.out.println("inputY: " + inputY);
    System.out.println("pageHeight: " + pageHeight);
    System.out.println("x: " + x);
    System.out.println("y: " + y);

    // Calcular la altura de la tabla
    float tableHeight = table.createRendererSubTree()
        .setParent(document.getRenderer())
        .layout(new com.itextpdf.layout.layout.LayoutContext(
            new com.itextpdf.layout.layout.LayoutArea(0, new com.itextpdf.kernel.geom.Rectangle(0, 0, 100, 100))))
        .getOccupiedArea()
        .getBBox()
        .getHeight();

    // Verificar si la tabla cabe en la página actual
    if (y - tableHeight < 0) {
      // Si no cabe, agregar una nueva página
      pdfDoc.addNewPage();
      y = pageHeight - marginTop - tableHeight; // Reiniciar la posición y en la nueva página
    }

    // Agregar la tabla a la página actual o nueva página
    document.add(
        table.setFixedPosition(pdfDoc.getNumberOfPages(), x, y, pdfDoc.getPage(1).getPageSize().getWidth() - x - 10));

    pdfDoc.close();
    pdfWriter.close();
    pdfReader.close();
    tempFile.delete(); // Eliminar el archivo temporal después de usarlo
  }
}
