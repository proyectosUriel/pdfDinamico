package com.itext.pdfDinamico.Service;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;


@Service
public class FormFieldExtractorService {
    public Rectangle getLastFieldPosition(String pdfPath) throws IOException {
        PdfDocument pdfDoc = null;

        try {
            // Abrir el documento PDF
            pdfDoc = new PdfDocument(new PdfReader(pdfPath));
            PdfAcroForm acroForm = PdfAcroForm.getAcroForm(pdfDoc, true);

            if (acroForm == null) {
                throw new IOException("No AcroForm found in the PDF.");
            }

            // Obtener los campos del formulario
            Map<String, PdfFormField> fields = acroForm.getAllFormFields();

            if (fields.isEmpty()) {
                throw new IOException("No form fields found in the PDF.");
            }

            // Obtener el último campo de formulario
            String lastFieldKey = (String) fields.keySet().toArray()[fields.size() - 1];
            PdfFormField lastField = fields.get(lastFieldKey);

            // Obtener la posición del último campo de formulario
            PdfArray widgetRectArray = lastField.getWidgets().get(0).getRectangle();

            // Convertir el PdfArray a Rectangle
            float x = widgetRectArray.getAsNumber(0).floatValue();
            float y = widgetRectArray.getAsNumber(1).floatValue();
            float width = widgetRectArray.getAsNumber(2).floatValue() - x;
            float height = widgetRectArray.getAsNumber(3).floatValue() - y;

            return new Rectangle(x, y, width, height);
        } catch (IOException e) {
            // Manejar la excepción y re-lanzarla
            throw new IOException("Error processing PDF document", e);
        } finally {
            if (pdfDoc != null) {
                pdfDoc.close();
            }
        }
    }
}



