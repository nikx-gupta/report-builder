package org.devignite.reportBuilder.builders.excel;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import org.devignite.reportBuilder.builders.BuilderHelper;
import org.devignite.reportBuilder.builders.FormatConstants;
import org.devignite.reportBuilder.builders.abstractions.IReportBuilder;
import org.devignite.reportBuilder.dataProvider.abstractions.IDataRow;
import org.devignite.reportBuilder.dataProvider.abstractions.IDataSet;
import org.devignite.reportBuilder.model.ColumnFormat;
import org.devignite.reportBuilder.model.ReportMetadata;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

@Slf4j
@Service
public class ExcelUsingRawXmlBuilder implements IReportBuilder {
    private String xmlFileName = "";
    private final String tmpFileName = null;

    private StyleWorkbook styleWorkbook;

    private final BuilderHelper helper = new BuilderHelper();
    Map<String, ColumnFormat> headerSet = null;

    public ExcelUsingRawXmlBuilder() {
        styleWorkbook = new StyleWorkbook();
        xmlFileName = helper.getUniqueFileNameWithGuid("xml");
    }

    public void generateReport(IDataSet dataSet, ReportMetadata reportMetadata, OutputStream outputStream) {
        try {
            headerSet = new BuilderHelper().getHeaderSet(reportMetadata);

            writeXmlDataFile(dataSet, reportMetadata);

            styleWorkbook.save();
            substitute(styleWorkbook.getSavedFile(), new File(xmlFileName), styleWorkbook.getStyleSheetRef().substring(1), outputStream);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    @SneakyThrows
    private void writeXmlDataFile(IDataSet dataSet, ReportMetadata reportMetadata) {
        File tmpFile = new File(xmlFileName);
        Writer fw = new FileWriter(tmpFile, StandardCharsets.UTF_8);
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
            logRow(dataRow, i);
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
            return;
        }

        if (cellValue instanceof Date) {
            formatDateValue(writer, cellIndex, (Date) cellValue, columnFormat.getOutputValueFormat());
//            writer.createCell(cellIndex, (Date) cellValue, sheetStyles.get("date").getIndex());
        } else {

            writer.createCell(cellIndex, StringEscapeUtils.escapeXml11(cellValue.toString()));
        }
    }

    private void formatDateValue(SpreadsheetWriter writer, Integer cellIndex, Date cellValue, String outputValueFormat) {
        LocalDateTime dt = LocalDateTime.ofInstant(Instant.ofEpochMilli(cellValue.getTime()), ZoneId.of("UTC"));
        Date utcDate = Date.from(dt.atZone(ZoneId.systemDefault()).toInstant());

        if (outputValueFormat == null) {
            // apply default format
            writer.createCell(cellIndex, utcDate, styleWorkbook.getStyleIndex(FormatConstants.FORMAT_DEFAULT_DATE_KEY));
        } else if (styleWorkbook.contains(outputValueFormat)) {
            writer.createCell(cellIndex, utcDate, styleWorkbook.getStyleIndex(outputValueFormat));
        } else {
            if (outputValueFormat.startsWith(FormatConstants.FORMAT_STR)) {
                // Convert DateTime to String
                String pattern = outputValueFormat.substring(FormatConstants.FORMAT_STR.length());
                SimpleDateFormat format = new SimpleDateFormat(pattern);
                writer.createCell(cellIndex, format.format(utcDate));
            } else if (outputValueFormat.startsWith(FormatConstants.FORMAT_DATE_TIME)) {
                // Change Output Date Pattern
                String pattern = outputValueFormat.substring(FormatConstants.FORMAT_STR.length());
                writer.createCell(cellIndex, utcDate, styleWorkbook.createDateCellStyle(outputValueFormat, pattern));
            }
        }
    }


    /**
     * @param zipFile the excel template file
     * @param tmpFile the XML file with the sheet data
     * @param entry   the name of the sheet entry to substitute, e.g. xl/worksheets/sheet1.xml
     * @param out     the stream to write the result to
     */
    @SneakyThrows
    private void substitute(File zipFile, File tmpFile, String entry, OutputStream out) {
        ZipFile zip = new ZipFile(zipFile);

        ZipOutputStream zos = new ZipOutputStream(out);

//        @SuppressWarnings("unchecked")
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

    private void copyStream(InputStream in, OutputStream out) throws IOException {
        byte[] chunk = new byte[1024];
        int count;
        while ((count = in.read(chunk)) >= 0) {
            out.write(chunk, 0, count);
        }
    }

    private void logRow(IDataRow row, Integer rowIndex) {
        StringBuilder builder = new StringBuilder();
        row.foreach((x, y) -> {
            builder.append(",");
            builder.append(row.getCellValue(x));
        });
        log.info("Index: {}, Data: {}", rowIndex, builder.toString());
    }
}


