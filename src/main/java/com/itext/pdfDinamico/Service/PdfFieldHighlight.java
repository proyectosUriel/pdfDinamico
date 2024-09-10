package com.itext.pdfDinamico.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itext.pdfDinamico.Model.DateIniciales;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.annot.PdfWidgetAnnotation;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;


@Service
public class PdfFieldHighlight {

    @Autowired
    private TableDimensionCalculator tableDimensionCalculator;

    public void coor(String docpdf,String fieldName,List<DateIniciales> datos){
         String outputPath = "/home/uriel_raigon/modify-PDF/modificado.pdf";
         String fieldId = fieldName;

        try {
            byte[] decodedPdf = Base64.getDecoder().decode(docpdf);
            InputStream inputStream = new ByteArrayInputStream(decodedPdf);

            PdfDocument pdfDoc = new PdfDocument(new PdfReader(inputStream), new PdfWriter(outputPath));
            PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
            PdfFormField field = form.getField(fieldId);

            if (field != null) {
                try{
                List<PdfWidgetAnnotation> widgets = field.getWidgets();
                Document document = new Document(pdfDoc);

                for (PdfWidgetAnnotation widget : widgets) {
                    PdfPage page = widget.getPage();
                    Rectangle rect = widget.getRectangle().toRectangle();
                    int pageNumber = pdfDoc.getPageNumber(page);

                    // Crear la tabla con los anchos calculados
                    Table table = new Table(new float[]{80, 50, 30});
                    table.setMarginBottom(20);

                    // Agregar filas a la tabla con los datos recibidos
                    for (DateIniciales dato : datos) {
                        String name = dato.getName() != null ? dato.getName() : "";
                        String hash = dato.getHash() != null ? dato.getHash() : "";
                        String id = dato.getId() != null ? dato.getId() : "";

                        table.addCell(new Cell().add(new Paragraph(name))
                                .setTextAlignment(TextAlignment.LEFT)
                                .setFontSize(8)
                                .setBorder(null)
                                .setPaddings(5, 5, 5, 5)
                        );
                        table.addCell(new Cell().add(new Paragraph(hash))
                                .setTextAlignment(TextAlignment.LEFT)
                                .setFontSize(8)
                                .setBorder(null)
                                .setPaddings(5, 5, 5, 5)
                        );
                        table.addCell(new Cell().add(new Paragraph(id))
                                .setTextAlignment(TextAlignment.LEFT)
                                .setFontSize(8)
                                .setBorder(null)
                                .setPaddings(5, 5, 5, 5)
                        );
                    }

                    // Ajustar dimensiones de la tabla
                    tableDimensionCalculator.adjustTableDimensions(table, rect.getWidth());
 
                    // Establecer la posición fija para la tabla en el documento
                    // Float tableHeight = tableDimensionCalculator.calculateTableHeight(table, pageNumber, 2);
                    float x = rect.getLeft();
                    float y = rect.getTop() - tableDimensionCalculator.calculateTableHeight(table, 28, 8); // Ajustar la altura de la tabla
                    table.setFixedPosition(pageNumber, x, y, rect.getWidth());
                    // Añadir la tabla al documento
                    document.add(table);
                }

                document.close();
            }catch(Exception e){
                e.printStackTrace();
                System.out.println("Error al calcular el layout de la tabla "+e.getMessage());
            }
            } else {
                System.out.println("Campo con ID " + fieldId + " no encontrado.");
            }

            pdfDoc.close();
            System.out.println("PDF modificado guardado en: " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
