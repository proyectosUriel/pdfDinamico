package com.itext.pdfDinamico.Service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.annot.PdfWidgetAnnotation;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;


@Service
public class PdfFieldHighlight {
    public void coor(){
         // Reemplaza con la ruta a tu archivo PDF y el ID del campo que deseas buscar
        String pdfPath = "/home/uriel_raigon/Downloads/PruebasPlantillaNueva2.pdf";
        String outputPath = "/home/uriel_raigon/modify-PDF/modificado.pdf";
        String fieldId = "Evidencias";

        try {
            // Abrir el documento PDF para lectura y escritura
            PdfDocument pdfDoc = new PdfDocument(new PdfReader(pdfPath), new PdfWriter(outputPath));
            
            // Obtener el formulario PDF
            PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
            
            // Obtener el campo del formulario por su nombre (ID)
            PdfFormField field = form.getField(fieldId);

            if (field != null) {
                // Obtener los widgets asociados al campo de formulario
                List<PdfWidgetAnnotation> widgets = field.getWidgets();
        
                for (PdfWidgetAnnotation widget : widgets) {
                    // Obtener la página en la que está el widget
                    PdfPage page = widget.getPage();
                    int pageNumber = pdfDoc.getPageNumber(page);
                    
                    // Obtener las coordenadas del widget
                    Rectangle rect = widget.getRectangle().toRectangle();
                    
                    // Dibujar el rectángulo en el PDF
                    PdfCanvas pdfCanvas = new PdfCanvas(pdfDoc.getPage(pageNumber));
                    pdfCanvas.setStrokeColor(ColorConstants.GREEN)
                             .setLineWidth(2)
                             .rectangle(rect)
                             .stroke();
                }
            } else {
                System.out.println("Campo con ID " + fieldId + " no encontrado.");
            }

            // Cerrar el documento
            pdfDoc.close();
            System.out.println("PDF modificado guardado en: " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
