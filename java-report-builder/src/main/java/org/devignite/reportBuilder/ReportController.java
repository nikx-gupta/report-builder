package org.devignite.reportBuilder;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.devignite.reportBuilder.services.ReportResponse;
import org.devignite.reportBuilder.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

@RestController
@RequestMapping("/api/report")
@Slf4j
public class ReportController {

    @Autowired
    ReportService reportService;

    @GetMapping(value = "/create")
    public void createExcel() {
        try (OutputStream fileOut = new FileOutputStream("Javatpoint.xlsx")) {
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("Sheet");
            Row row = sheet.createRow(1);

            XSSFFont font = (XSSFFont) wb.createFont();
            font.setFontName("Verdana");
            XSSFCellStyle style1 = (XSSFCellStyle) wb.createCellStyle();
            Color color = new java.awt.Color(Color.GREEN.getRGB());
//            style1.setFillForegroundColor(new XSSFColor(new java.awt.Color(128, 0, 128)));
            style1.setFillForegroundColor(new XSSFColor(color));
            style1.setFillPattern(CellStyle.SOLID_FOREGROUND);
            style1.setFont(font);
            Cell cell = row.createCell(0);
            cell.setCellValue("Sample");
            cell.setCellStyle(style1);

            wb.write(fileOut);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @GetMapping(value = "/movie")
    public ResponseEntity generateMovieReport(@RequestParam String metadataTitle) {
        ReportResponse response = reportService.generateReport(metadataTitle);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", ReportBuilderConstants.MIME_EXCEL);
        headers.setContentDisposition(ContentDisposition.attachment().filename(response.getFileName()).build());
        return new ResponseEntity(response.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping(value = "/read")
    public void readExcel() {
        try (InputStream fileOut = new FileInputStream("C:\\Users\\Nikhil Gupta\\Desktop\\Book1.xlsx")) {
            Workbook wb = WorkbookFactory.create(fileOut);
            Sheet sheet = wb.getSheetAt(0);
            Row row = sheet.getRow(0);
            Cell cell = row.getCell(0);
            CellStyle style = cell.getCellStyle();
            short cellColor = style.getFillBackgroundColor();
            org.apache.poi.ss.usermodel.Color cb = style.getFillBackgroundColorColor();
            org.apache.poi.ss.usermodel.Color cf = style.getFillForegroundColorColor();

//            Sheet sheet = wb.createSheet("Sheet");
//            Row row = sheet.createRow(1);
//            XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
//            style.setFillBackgroundColor(new XSSFColor(Color.blue));
//            style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
//            Cell cell = row.createCell(0);
//            cell.setCellValue("Sample");
//            cell.setCellStyle(style);
//
//            wb.write(fileOut);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
