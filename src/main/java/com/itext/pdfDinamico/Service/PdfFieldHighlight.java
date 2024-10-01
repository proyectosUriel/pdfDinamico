package com.itext.pdfDinamico.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itext.pdfDinamico.Model.DateEvidencias;
import com.itext.pdfDinamico.Model.DateFirmados;
import com.itext.pdfDinamico.Model.DateIniciales;
import com.itext.pdfDinamico.Model.GeneralReceivingData;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.annot.PdfWidgetAnnotation;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

@Service
public class PdfFieldHighlight {

    @Autowired
    private TableDimensionCalculator tableDimensionCalculator;

    public void generatePdf(GeneralReceivingData data) {
        String outputPath = "/home/uriel_raigon/modify-PDF/modificado.pdf";
        String fieldId = data.getDocumentosIniciales(); // El campo que se necesita para las coordenadas

        try {
            // Decodificación del PDF en base64
            byte[] decodedPdf = Base64.getDecoder().decode(data.getDocpdf());
            InputStream inputStream = new ByteArrayInputStream(decodedPdf);

            // Abrir y preparar el PDF
            PdfDocument pdfDoc = new PdfDocument(new PdfReader(inputStream), new PdfWriter(outputPath));
            PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
            PdfFormField field = form.getField(fieldId);

            if (field != null) {
                try {
                    List<PdfWidgetAnnotation> widgets = field.getWidgets();
                    Document document = new Document(pdfDoc);

                    // *** PRIMERA TABLA: Datos Iniciales (Posicionada en coordenadas específicas)
                    // ***
                    for (PdfWidgetAnnotation widget : widgets) {
                        PdfPage page = widget.getPage();
                        Rectangle rect = widget.getRectangle().toRectangle();
                        int pageNumber = pdfDoc.getPageNumber(page);

                        // *** PRIMERA TABLA: Datos Iniciales ***
                        // Crear la primera tabla (tabla con coordenadas fijas)
                        Table tableIniciales = new Table(new float[] { 80, 50, 30 });
                        tableIniciales.setMarginBottom(20);

                        // Agregar filas a la tabla con los datos recibidos
                        for (DateIniciales dato : data.getDatosIniciales()) {
                            String name = dato.getName() != null ? dato.getName() : "";
                            String hash = dato.getHash() != null ? dato.getHash() : "";
                            String id = dato.getId() != null ? dato.getId() : "";

                            tableIniciales.addCell(new Cell().add(new Paragraph(name))
                                    .setTextAlignment(TextAlignment.LEFT)
                                    .setFontSize(8)
                                    .setPaddings(5, 5, 5, 5));
                            tableIniciales.addCell(new Cell().add(new Paragraph(hash))
                                    .setTextAlignment(TextAlignment.LEFT)
                                    .setFontSize(8)
                                    .setPaddings(5, 5, 5, 5));
                            tableIniciales.addCell(new Cell().add(new Paragraph(id))
                                    .setTextAlignment(TextAlignment.LEFT)
                                    .setFontSize(8)
                                    .setPaddings(5, 5, 5, 5));
                        }

                        // Ajustar dimensiones de la tabla y posicionarla en las coordenadas obtenidas
                        tableDimensionCalculator.adjustTableDimensions(tableIniciales, rect.getWidth());
                        float x = rect.getLeft();
                        float y = rect.getTop() - tableDimensionCalculator.calculateTableHeight(tableIniciales, 28, 8);
                        tableIniciales.setFixedPosition(pageNumber, x, y, rect.getWidth());

                        // Añadir la primera tabla al documento en una posición fija
                        document.add(tableIniciales);

                        // *** AGREGAR UN SALTO DE PÁGINA DESPUÉS DE LA PRIMERA TABLA ***
                        document.add(new AreaBreak()); // Salto de página forzado

                        // *** SEGUNDA TABLA: Documentos Firmados ***
                        document.add(new Paragraph(data.getDocumentosFirmados())
                                .setFontSize(12)
                                .setBold()
                                .setMarginTop(20)
                                .setMarginBottom(10)); // Título de la segunda tabla

                        Table tableFirmados = new Table(new float[] { 40, 100, 40 });
                        tableFirmados.setMarginBottom(20);

                        for (DateFirmados dato : data.getDatosFirmados()) {
                            tableFirmados.addCell(new Cell().add(new Paragraph(dato.getId())));
                            tableFirmados.addCell(new Cell().add(new Paragraph(dato.getName())));
                            tableFirmados.addCell(new Cell().add(new Paragraph(dato.getHash())));
                        }

                        // Añadir la segunda tabla en la nueva página
                        document.add(tableFirmados);

                        // *** TERCERA TABLA: Evidencias de Proceso ***
                        if (data.getEvidenciasProceso() != null) {
                            document.add(new Paragraph(data.getEvidenciasProceso())
                                    .setFontSize(12)
                                    .setBold()
                                    .setMarginTop(20)
                                    .setMarginBottom(10)); // Título de la tercera tabla
                        }

                        List<DateEvidencias> evidenciasList = data.getDatosEvidenciaProceso();
                        if (evidenciasList != null && !evidenciasList.isEmpty()) {
                            // *** Tabla principal con una sola columna ***
                            Table tableProceso = new Table(1); // Una columna en la tabla principal
                            tableProceso.setWidth(UnitValue.createPercentValue(100)); // Ocupar el 100% del ancho disponible
                            tableProceso.setMarginBottom(20);

                            // Para cada conjunto de datos en datosProceso, crear una subtabla
                            for (DateEvidencias dato : evidenciasList) {
                                // *** Subtabla con una sola columna para la información detallada ***
                                Table subTable = new Table(1); // Subtabla con una columna (una fila por dato)
                                subTable.setWidth(UnitValue.createPercentValue(100)); // Subtabla ocupará el 100% del espacio en la celda

                                // Añadir cada campo en una fila separada
                                subTable.addCell(new Cell().add(new Paragraph(dato.getName_tag())));
                                subTable.addCell(new Cell().add(new Paragraph(dato.getName())));
                                subTable.addCell(new Cell().add(new Paragraph(dato.getDate_tag())));
                                subTable.addCell(new Cell().add(new Paragraph(dato.getDate().toString())));
                                subTable.addCell(new Cell().add(new Paragraph(dato.getReceiver_tag())));
                                subTable.addCell(new Cell().add(new Paragraph(dato.getReceiver())));
                                subTable.addCell(new Cell().add(new Paragraph(dato.getDetail_label())));
                                subTable.addCell(new Cell().add(new Paragraph(dato.getDetail())));

                                // *** Añadir la subtabla como una celda en la tabla principal ***
                                Cell cellWithSubTable = new Cell().add(subTable);
                                cellWithSubTable.setPadding(10); // Ajustar el padding para mayor claridad
                                tableProceso.addCell(cellWithSubTable);
                            }

                            // Añadir la tabla principal al documento
                            document.add(tableProceso);
                        }
                    }

                    // Cerrar el documento después de todos los elementos
                    document.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error al calcular el layout de la tabla " + e.getMessage());
                }
            } else {
                System.out.println("Campo con ID " + fieldId + " no encontrado.");
            }

            // Cerrar el PDF
            pdfDoc.close();
            System.out.println("PDF modificado guardado en: " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
