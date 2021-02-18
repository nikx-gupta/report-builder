package org.devignite.reportBuilder.builders;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.devignite.reportBuilder.builders.abstractions.IReportBuilder;
import org.devignite.reportBuilder.dataProvider.abstractions.IDataRow;
import org.devignite.reportBuilder.dataProvider.abstractions.IDataSet;
import org.devignite.reportBuilder.model.ColumnFormat;
import org.devignite.reportBuilder.model.ReportMetadata;
import org.devignite.reportBuilder.model.StyleFormat;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class ExcelReportBuilder implements IReportBuilder {

    private XSSFWorkbook _workbook;
    private Sheet _currentSheet;

    @Override
    public void generateReport(IDataSet dataSet, ReportMetadata reportMetadata, OutputStream outputStream) {
        Map<String, ColumnFormat> headerSet =  getHeaderSet(reportMetadata);

        _workbook = new XSSFWorkbook();
        _currentSheet = _workbook.createSheet("Sheet");

        try {
            writeHeader(headerSet, _currentSheet.createRow(0), reportMetadata.getHeaderStyle());
            Integer rowCount = dataSet.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                writeRow(dataSet.getRow(i), _currentSheet.createRow(i + 1), headerSet);
            }

            _workbook.write(outputStream);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void generateMultiSheet(List<IDataSet> dataSet, ReportMetadata reportMetadata, OutputStream outputStream) {
        Map<String, ColumnFormat> headerSet =  getHeaderSet(reportMetadata);

        _workbook = new XSSFWorkbook();

        try {
            for (int dataSetIndex = 0; dataSetIndex < dataSet.size(); dataSetIndex++) {
                _currentSheet = _workbook.createSheet("Sheet" + dataSetIndex);
                writeHeader(headerSet, _currentSheet.createRow(0), reportMetadata.getHeaderStyle());
                Integer rowCount = dataSet.get(dataSetIndex).getRowCount();
                for (int i = 0; i < rowCount; i++) {
                    writeRow(dataSet.get(dataSetIndex).getRow(i), _currentSheet.createRow(i + 1), headerSet);
                }
            }

            _workbook.write(outputStream);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private Map<String,ColumnFormat> getHeaderSet(ReportMetadata reportMetadata) {
       return reportMetadata.getColumns().stream()
                .collect(Collectors.toMap(x -> x.getSourceFieldName().isEmpty() || x.getSourceFieldName().isBlank() ? x.getOutputName() : x.getSourceFieldName(), x -> x, (ov, nv) -> nv, LinkedHashMap::new));
    }

    private void writeHeader(Map<String, ColumnFormat> headerSet, Row rowHeader, StyleFormat headerStyle) {
        AtomicInteger colIndex = new AtomicInteger(0);
        headerSet.forEach((x, y) -> {
            Cell cell = rowHeader.createCell(colIndex.getAndIncrement());
            cell.setCellValue(y.getOutputName());
            applyCellStyle(cell, headerStyle);
        });
    }

    private void writeRow(IDataRow dataRow, Row row, Map<String, ColumnFormat> headerSet) {
        AtomicInteger colIndex = new AtomicInteger(0);
        headerSet.forEach((fieldName, curColFormat) -> {
            if (curColFormat != null) {
                Cell cell = row.createCell(colIndex.getAndIncrement());
                Object cellValue = dataRow.getCellValue(fieldName);
                cell.setCellValue(cellValue != null ? cellValue.toString() : "");
                applyCellStyle(cell, curColFormat.getColumnStyle());
            }
        });
    }

    private void applyCellStyle(Cell currentCell, StyleFormat styleFormat) {
        if (styleFormat == null)
            return;

        XSSFCellStyle cellStyle = _workbook.createCellStyle();
        if (!styleFormat.getFontName().isEmpty()) {
            XSSFFont font = _workbook.createFont();
            font.setFontName(styleFormat.getFontName());
            cellStyle.setFont(font);
        }

        if (!styleFormat.getBackgroundColor().isEmpty()) {
            String[] colorSplit = styleFormat.getBackgroundColor().split("[,/.]");
            cellStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(Integer.parseInt(colorSplit[0]), Integer.parseInt(colorSplit[1]), Integer.parseInt(colorSplit[2]))));
            cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        }

        currentCell.setCellStyle(cellStyle);
    }
}

