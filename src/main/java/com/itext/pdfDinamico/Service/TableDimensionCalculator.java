package com.itext.pdfDinamico.Service;

import org.springframework.stereotype.Service;

import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

@Service
public class TableDimensionCalculator {

    public float calculateTableHeight(Table table, float cellHeight, float padding) {
        // Número de filas en la tabla
        int numberOfRows = table.getNumberOfRows();

        // Calcular la altura total de la tabla
        float totalHeight = numberOfRows * (cellHeight + 2 * padding);
        return totalHeight;
    }

    public float calculateTableWidth(Table table) {
        float maxWidth = 0;

        // Iterar sobre las celdas de la tabla para calcular el ancho total
        for (int i = 0; i < table.getNumberOfColumns(); i++) {
            float columnWidth = 0;
            for (int j = 0; j < table.getNumberOfRows(); j++) {
                Cell cell = table.getCell(j, i);
                if (cell != null) {
                    UnitValue cellWidth = cell.getWidth();
                    if (cellWidth != null) {
                        columnWidth += cellWidth.getValue();
                    }
                }
            }
            maxWidth = Math.max(maxWidth, columnWidth);
        }

        return maxWidth;
    }

    public void adjustTableDimensions(Table table, float maxTableWidth) {
        // Configurar la tabla para que ocupe el ancho máximo permitido
        table.setWidth(UnitValue.createPointValue(maxTableWidth));

        // Obtener el número de columnas en la tabla
        int numberOfColumns = table.getNumberOfColumns();

        // Calcular el ancho de cada columna
        float minColumnWidth= 50f;
        float columnWidth = Math.max(minColumnWidth, maxTableWidth/numberOfColumns);

        // Ajustar el ancho de las celdas en la tabla
        for (int i = 0; i < table.getNumberOfRows(); i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                Cell cell = table.getCell(i, j);
                if (cell != null) {
                    cell.setWidth(UnitValue.createPointValue(columnWidth));
                }
            }
        }

        // Establecer el layout fijo para la tabla
        table.setFixedLayout();
    }

}
