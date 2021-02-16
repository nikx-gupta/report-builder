package org.devignite.reportBuilder;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class ExcelReportBuilder implements IReportBuilder {

    @Autowired
    MongoReportSource reportSource;

    @Override
    public void generateReport(ReportManifest reportManifest, OutputStream outputStream) {
        List<Document> data = reportSource.getData(reportManifest);
        Map<String, ColumnFormat> headerSet = reportManifest.getColumns().stream()
                .collect(Collectors.toMap(x -> x.getColumnName(), x -> x, (ov, nv) -> ov, LinkedHashMap::new));
        AtomicInteger index = new AtomicInteger(-1);
        headerSet.forEach((x, y) -> y.setOrder(index.incrementAndGet()));

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("Sheet");
        StylesTable styles = wb.getStylesSource();
        try {
            writeHeader(headerSet, sheet.createRow(0));

            for (int i = 0; i < data.size(); i++) {
                Document curDoc = data.get(i);
                writeRow(curDoc, sheet.createRow(i + 1), headerSet, styles);
            }

            wb.write(outputStream);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void writeHeader(Map<String, ColumnFormat> headerSet, Row rowHeader) {
        headerSet.forEach((x, y) -> {
            Cell cell = rowHeader.createCell(y.getOrder());
            cell.setCellValue(y.getAliasName());
        });
    }

    private void writeRow(Document curDoc, Row row, Map<String, ColumnFormat> headerSet, StylesTable styles) {
        for (Map.Entry<String, Object> col : curDoc.entrySet()) {
            ColumnFormat curColFormat = headerSet.get(col.getKey());
            if (curColFormat != null) {
                // To make order value from zero based index
                Cell cell = row.createCell(curColFormat.getOrder());
                Object cellValue = col.getValue();
                cell.setCellValue(cellValue != null ? cellValue.toString() : "");
            }
        }
    }
}

