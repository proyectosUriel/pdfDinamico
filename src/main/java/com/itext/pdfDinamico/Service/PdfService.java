package com.itext.pdfDinamico.Service;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.stereotype.Service;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.annot.PdfWidgetAnnotation;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;


@Service
public class PdfService {

    public void highlightField(InputStream inputStream, OutputStream outputStream, String fieldName) throws IOException {
        // Crear PdfReader para leer el archivo PDF desde el InputStream
        PdfReader pdfReader = new PdfReader(inputStream);

        // Crear PdfWriter para escribir el archivo PDF modificado al OutputStream
        PdfWriter pdfWriter = new PdfWriter(outputStream);

        // Crear PdfDocument con PdfReader y PdfWriter
        PdfDocument pdfDocument = new PdfDocument(pdfReader, pdfWriter);

        // Obtener el formulario de campos
        PdfAcroForm acroForm = PdfAcroForm.getAcroForm(pdfDocument, true);

        // Iterar sobre todos los campos del formulario
        acroForm.getFormFields().forEach((key, field) -> {
            if (key.equals(fieldName)) {
                // Obtener el widget del campo de formulario
                PdfWidgetAnnotation widget = field.getWidgets().get(0);

                // Obtener el rectángulo del widget como PdfArray
                PdfArray pdfArray = widget.getRectangle();

                // Convertir PdfArray a Rectangle
                Rectangle rect = new Rectangle(
                        pdfArray.getAsNumber(0).floatValue(),
                        pdfArray.getAsNumber(1).floatValue(),
                        pdfArray.getAsNumber(2).floatValue() - pdfArray.getAsNumber(0).floatValue(),
                        pdfArray.getAsNumber(3).floatValue() - pdfArray.getAsNumber(1).floatValue()
                );

                // Obtener la página del widget
                PdfPage pdfPage = widget.getPage();

                // Añadir un borde verde fosforito alrededor del campo
                PdfCanvas pdfCanvas = new PdfCanvas(pdfPage.newContentStreamBefore(), pdfPage.getResources(), pdfDocument);
                pdfCanvas.setStrokeColor(new DeviceRgb(0, 255, 0)) // Color verde fosforito
                        .setLineWidth(2)
                        .rectangle(rect)
                        .stroke();
            }
        });

        // Cerrar el documento PDF
        pdfDocument.close();
    }
}
