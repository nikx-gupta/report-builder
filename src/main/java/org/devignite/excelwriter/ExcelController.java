package org.devignite.excelwriter;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.OutputStream;

@RestController
@RequestMapping("/api/excel")
@Slf4j
public class ExcelController {

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
}
