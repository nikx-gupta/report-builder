package org.devignite.reportBuilder.builders;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.devignite.reportBuilder.dataProvider.abstractions.IDataRow;
import org.devignite.reportBuilder.model.ColumnFormat;
import org.devignite.reportBuilder.model.ReportMetadata;
import org.devignite.reportBuilder.dataProvider.abstractions.IDataSet;
import org.devignite.reportBuilder.builders.abstractions.IReportBuilder;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class ExcelReportBuilder implements IReportBuilder {

    private XSSFWorkbook _workbook;
    private Sheet _currentSheet;

    @Override
    public void generateReport(IDataSet dataSet, ReportMetadata reportMetadata, OutputStream outputStream) {
        Map<String, ColumnFormat> headerSet = reportMetadata.getColumns().stream()
                .collect(Collectors.toMap(x -> x.getSourceFieldName().isEmpty() || x.getSourceFieldName().isBlank() ? x.getOutputName() : x.getSourceFieldName(), x -> x, (ov, nv) -> nv, LinkedHashMap::new));
        AtomicInteger subIndex = new AtomicInteger(-1);
        headerSet.forEach((x, y) -> y.setOrder(subIndex.incrementAndGet()));

        initalizeWorkbook();

        try {
            writeHeader(headerSet, _currentSheet.createRow(0));

            for (int i = 0; i < dataSet.getRowCount(); i++) {
                writeRow(dataSet.getRow(i), _currentSheet.createRow(i + 1), headerSet);
            }

            _workbook.write(outputStream);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void initalizeWorkbook(){
        _workbook = new XSSFWorkbook();
        _currentSheet = _workbook.createSheet("Sheet");
    }

    private void writeHeader(Map<String, ColumnFormat> headerSet, Row rowHeader) {
        headerSet.forEach((x, y) -> {
            Cell cell = rowHeader.createCell(y.getOrder());
            cell.setCellValue(y.getOutputName());
        });
    }

    private void writeRow(IDataRow dataRow, Row row, Map<String, ColumnFormat> headerSet) {
        headerSet.forEach((fieldName, curColFormat) -> {
            if (curColFormat != null) {
                // To make order value from zero based index
                Cell cell = row.createCell(curColFormat.getOrder());
                if (curColFormat.getSourceFieldName().indexOf('.') >= 0) {

                }

                Object cellValue = dataRow.getCellValue(row.getRowNum(), fieldName);
                cell.setCellValue(cellValue != null ? cellValue.toString() : "");
            }
        });
    }
}

