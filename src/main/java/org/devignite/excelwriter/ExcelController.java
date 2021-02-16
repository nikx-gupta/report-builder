package org.devignite.excelwriter;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.devignite.reportBuilder.ColumnFormat;
import org.devignite.reportBuilder.ReportBuilderFactory;
import org.devignite.reportBuilder.ReportManifest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/excel")
@Slf4j
public class ExcelController {

    @Autowired
    ReportBuilderFactory reportBuilder;

    @GetMapping(value = "/create")
    public void createExcel() {
        try (OutputStream fileOut = new FileOutputStream("Javatpoint.xlsx")) {
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("Sheet");
            Row row = sheet.createRow(1);
            XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
            style.setFillBackgroundColor(new XSSFColor(Color.blue));
            style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            Cell cell = row.createCell(0);
            cell.setCellValue("Sample");
            cell.setCellStyle(style);

            wb.write(fileOut);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @GetMapping(value = "/report")
    public void createReport() throws FileNotFoundException {
        ReportManifest manifest = new ReportManifest();
        manifest.setCollectionName("movies");
        ArrayList<ColumnFormat> columns = new ArrayList<>();
        ColumnFormat fmt = new ColumnFormat();
        fmt.setAliasName("aliasTitle");
        fmt.setColumnName("title");
        fmt.setOrder(1);
        columns.add(fmt);
        ColumnFormat fmt2 = new ColumnFormat();
        fmt2.setColumnName("type");
        fmt2.setAliasName("typeAlias");
        fmt2.setOrder(2);
        columns.add(fmt2);
        manifest.setColumns(columns);

        reportBuilder.createReportBuilder(manifest).generateReport(manifest, new FileOutputStream("ReportData.xlsx"));
    }

}
