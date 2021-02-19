package org.devignite.reportBuilder.builders.excel;

import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.CellReference;

import java.io.IOException;
import java.io.Writer;
import java.util.Calendar;
import java.util.Date;

public class SpreadsheetWriter {
    private final Writer _out;
    private int _rownum;

    public SpreadsheetWriter(Writer out) {
        _out = out;
    }

    public void beginSheet() throws IOException {
        _out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<worksheet xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\">");
        _out.write("<sheetData>\n");
    }

    public void endSheet() throws IOException {
        _out.write("</sheetData>");
        _out.write("</worksheet>");
    }

    /**
     * Insert a new row
     *
     * @param rownum 0-based row number
     */
    public void insertRow(int rownum) throws IOException {
        _out.write("<row r=\"" + (rownum + 1) + "\">\n");
        this._rownum = rownum;
    }

    /**
     * Insert row end marker
     */
    public void endRow() throws IOException {
        _out.write("</row>\n");
    }

    public void createCell(int columnIndex, String value, int styleIndex) throws IOException {
        String ref = new CellReference(_rownum, columnIndex).formatAsString();
        _out.write("<c r=\"" + ref + "\" t=\"inlineStr\"");
        if (styleIndex != -1) _out.write(" s=\"" + styleIndex + "\"");
        _out.write(">");
        _out.write("<is><t>" + value + "</t></is>");
        _out.write("</c>");
    }

    @SneakyThrows
    public void createCell(int columnIndex, String value) {
        createCell(columnIndex, value, -1);
    }

    @SneakyThrows
    public void createCell(int columnIndex, double value, int styleIndex) {
        String ref = new CellReference(_rownum, columnIndex).formatAsString();
        _out.write("<c r=\"" + ref + "\" t=\"n\"");
        if (styleIndex != -1) _out.write(" s=\"" + styleIndex + "\"");
        _out.write(">");
        _out.write("<v>" + value + "</v>");
        _out.write("</c>");
    }

    @SneakyThrows
    public void createCell(int columnIndex, double value) {
        createCell(columnIndex, value, -1);
    }

    @SneakyThrows
    public void createCell(int columnIndex, Calendar value, int styleIndex) {
        createCell(columnIndex, DateUtil.getExcelDate(value, false), styleIndex);
    }

    public void createCell(int columnIndex, Date value, int styleIndex) {
        createCell(columnIndex, DateUtil.getExcelDate(value, false), styleIndex);
    }
}
