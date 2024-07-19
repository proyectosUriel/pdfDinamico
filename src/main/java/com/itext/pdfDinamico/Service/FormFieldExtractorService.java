package com.itext.pdfDinamico.Service;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;


@Service
public class FormFieldExtractorService {
    public Rectangle getLastFieldPosition(String pdfPath) throws IOException {
        // Abrir el documento PDF
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(pdfPath));
        PdfAcroForm acroForm = PdfAcroForm.getAcroForm(pdfDoc, true);

        if (acroForm != null) {
            // Obtener los campos del formulario
            Map<String, PdfFormField> fields = acroForm.getFormFields();
            
            if (fields.isEmpty()) {
                pdfDoc.close();
                throw new IOException("No form fields found in the PDF.");
            }

            // Obtener el último campo de formulario
            String lastFieldKey = (String) fields.keySet().toArray()[fields.size() - 1];
            PdfFormField lastField = fields.get(lastFieldKey);

            // Obtener la posición del último campo de formulario
            Rectangle fieldRect = lastField.getWidgets().get(0).getRectangle();

            pdfDoc.close();
            return fieldRect;
        } else {
            pdfDoc.close();
            throw new IOException("No AcroForm found in the PDF.");
        }
    }
}


