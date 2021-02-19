package org.devignite.reportBuilder.builders.excel;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.devignite.reportBuilder.builders.BuilderHelper;
import org.devignite.reportBuilder.builders.abstractions.IReportBuilder;
import org.devignite.reportBuilder.dataProvider.abstractions.IDataRow;
import org.devignite.reportBuilder.dataProvider.abstractions.IDataSet;
import org.devignite.reportBuilder.model.ColumnFormat;
import org.devignite.reportBuilder.model.ReportMetadata;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

@Slf4j
@Service
public class ExcelUsingRawXmlBuilder implements IReportBuilder {

    private static final String styleSheetName = "styles";

    private String styleWorbookFileName = null;
    private String xmlFileName = "";
    private final String tmpFileName = null;

    private final BuilderHelper helper = new BuilderHelper();
    private Map<String, XSSFCellStyle> sheetStyles = null;

    public ExcelUsingRawXmlBuilder() {
        styleWorbookFileName = helper.getUniqueFileNameWithGuid("xlsx");
        xmlFileName = helper.getUniqueFileNameWithGuid("xml");
    }

    /**
     * @param zipFile the excel template file
     * @param tmpFile the XML file with the sheet data
     * @param entry   the name of the sheet entry to substitute, e.g. xl/worksheets/sheet1.xml
     * @param out     the stream to write the result to
     */
    @SneakyThrows
    private static void substitute(File zipFile, File tmpFile, String entry, OutputStream out) {
        ZipFile zip = new ZipFile(zipFile);

        ZipOutputStream zos = new ZipOutputStream(out);

        @SuppressWarnings("unchecked")
        Enumeration<ZipEntry> en = (Enumeration<ZipEntry>) zip.entries();
        while (en.hasMoreElements()) {
            ZipEntry ze = en.nextElement();
            if (!ze.getName().equals(entry)) {
                zos.putNextEntry(new ZipEntry(ze.getName()));
                InputStream is = zip.getInputStream(ze);
                copyStream(is, zos);
                is.close();
            }
        }
        zos.putNextEntry(new ZipEntry(entry));
        InputStream is = new FileInputStream(tmpFile);
        copyStream(is, zos);
        is.close();

        zos.close();
        zip.close();
        zipFile.delete();
        tmpFile.delete();
    }

    private static void copyStream(InputStream in, OutputStream out) throws IOException {
        byte[] chunk = new byte[1024];
        int count;
        while ((count = in.read(chunk)) >= 0) {
            out.write(chunk, 0, count);
        }
    }

    public void generateReport(IDataSet dataSet, ReportMetadata reportMetadata, OutputStream outputStream) {
        try {
            Map<String, ColumnFormat> headerSet = new BuilderHelper().getHeaderSet(reportMetadata);
            String styleSheetRef = writeStyleTemplate();

            writeStyleTemplate();

            writeXmlDataFile(dataSet, reportMetadata);

            substitute(new File(styleWorbookFileName), new File(xmlFileName), styleSheetRef.substring(1), outputStream);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    @SneakyThrows
    private String writeStyleTemplate() {
        XSSFWorkbook _tempWorkbook = new XSSFWorkbook();
        XSSFSheet styleSheet = _tempWorkbook.createSheet(styleSheetName);
        sheetStyles = mapFormatStyles(_tempWorkbook);
        FileOutputStream styleStream = new FileOutputStream(styleWorbookFileName);
        _tempWorkbook.write(styleStream);
        styleStream.close();
        return styleSheet.getPackagePart().getPartName().getName();
    }

    @SneakyThrows
    private void writeXmlDataFile(IDataSet dataSet, ReportMetadata reportMetadata) {
        File tmpFile = new File(xmlFileName);
        Writer fw = new FileWriter(tmpFile);
        SpreadsheetWriter writer = new SpreadsheetWriter(fw);
        writeSheet(writer, dataSet, reportMetadata);
        fw.close();
    }

    private void writeSheet(SpreadsheetWriter writer, IDataSet dataSet, ReportMetadata reportMetadata) throws IOException {
        writer.beginSheet();
        writeSheetXml(dataSet, reportMetadata, writer);
        writer.endSheet();
    }

    private void writeSheetXml(IDataSet dataSet, ReportMetadata reportMetadata, SpreadsheetWriter writer) throws IOException {
        Map<String, ColumnFormat> headerSet = new BuilderHelper().getHeaderSet(reportMetadata);

        // Write Header
        writer.insertRow(0);
        AtomicInteger colIndex = new AtomicInteger(0);
        headerSet.forEach((x, y) -> {
            writer.createCell(colIndex.getAndIncrement(), y.getOutputName());
        });
        writer.endRow();

        // Write Rows
        Integer rowCount = dataSet.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            IDataRow dataRow = dataSet.getRow(i);
            writer.insertRow(i + 1);
            AtomicInteger dataCellIndex = new AtomicInteger(0);
            headerSet.forEach((fieldName, curColFormat) -> {
                if (curColFormat != null) {
                    Object cellValue = dataRow.getCellValue(fieldName);
                    writeCellValue(writer, dataCellIndex.getAndIncrement(), cellValue, curColFormat);
                }
            });
            writer.endRow();
        }
    }

    private void writeCellValue(SpreadsheetWriter writer, Integer cellIndex, Object cellValue, ColumnFormat columnFormat) {
        if (cellValue == null) {
            writer.createCell(cellIndex, "");
        }

        if (cellValue instanceof Date) {
            writer.createCell(cellIndex, (Date) cellValue, sheetStyles.get("date").getIndex());
        } else {
            writer.createCell(cellIndex, cellValue.toString());
        }
    }

    private void formatDateValue(Date sourceValue, String outputValueFormat) {
        if (outputValueFormat == null) {
            // apply default format

        }
    }

    /**
     * Create a library of cell styles.
     */
    private Map<String, XSSFCellStyle> mapFormatStyles(XSSFWorkbook wb) {
        Map<String, XSSFCellStyle> styles = new HashMap<>();
        XSSFDataFormat fmt = wb.createDataFormat();

//        XSSFCellStyle style1 = wb.createCellStyle();
//        style1.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
//        style1.setDataFormat(fmt.getFormat("0.0%"));
//        styles.put("percent", style1);

//        XSSFCellStyle style2 = wb.createCellStyle();
//        style2.setAlignment(XSSFCellStyle.ALIGN_CENTER);
//        style2.setDataFormat(fmt.getFormat("0.0X"));
//        styles.put("coeff", style2);

//        XSSFCellStyle style3 = wb.createCellStyle();
//        style3.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
//        style3.setDataFormat(fmt.getFormat("$#,##0.00"));
//        styles.put("currency", style3);

        XSSFCellStyle styleDate = wb.createCellStyle();
        styleDate.setDataFormat(fmt.getFormat("dd-MMM-yyyy"));
        styles.put("date", styleDate);

//        XSSFCellStyle style5 = wb.createCellStyle();
//        XSSFFont headerFont = wb.createFont();
//        headerFont.setBold(true);
//        style5.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
//        style5.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
//        style5.setFont(headerFont);
//        styles.put("header", style5);


        return styles;
    }
}
