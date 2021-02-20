package org.devignite.reportBuilder.builders.excel;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.devignite.reportBuilder.ReportBuilderException;
import org.devignite.reportBuilder.builders.BuilderHelper;
import org.devignite.reportBuilder.builders.FormatConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class StyleWorkbook {
    private static final String styleSheetName = "styles";
    private String workbookName = null;

    private XSSFWorkbook styleWorkbook;
    XSSFSheet styleSheet;

    private Map<String, XSSFCellStyle> sheetStyles = new HashMap<>();

    private final BuilderHelper helper = new BuilderHelper();

    private boolean isSaved = false;

    /**
     * Creates StyleWorkbook containing StyleCache to be merged with main workbook
     */
    public StyleWorkbook() {
        workbookName = helper.getUniqueFileNameWithGuid("xlsx");
        styleWorkbook = new XSSFWorkbook();
        styleSheet = styleWorkbook.createSheet(styleSheetName);
        createDefaultStyles();
    }

    /**
     * Gets reference to the Stylesheet.
     */
    public String getStyleSheetRef() {
        return styleSheet.getPackagePart().getPartName().getName();
    }

    /**
     * Gets the Saved Workbook. File is saved before returning reference to file
     */
    public File getSavedFile() {
        if (!isSaved) {
            save();
        }

        return new File(workbookName);
    }

    /**
     * Saves the StyleWorkbook to file.
     */
    public void save() {
        try (FileOutputStream styleStream = new FileOutputStream(workbookName)) {
            styleWorkbook.write(styleStream);
            isSaved = true;
        } catch (Exception ex) {
            throw new ReportBuilderException("StyleWorkbook: Error writing StyleWorkbook to file");
        }
    }

    /**
     * Create a iniital library of default cell styles.
     */
    private void createDefaultStyles() {
        createDateCellStyle(FormatConstants.FORMAT_DEFAULT_DATE_KEY, FormatConstants.FORMAT_DEFAULT_DATE_PATTERN);
    }

    /**
     * Gets the style index with given StyleKey.
     */
    public short getStyleIndex(String styleKey) {
        return sheetStyles.get(styleKey).getIndex();
    }

    /**
     * If StyleCache contains Style respective to styleKey
     */
    public boolean contains(String styleKey) {
        return sheetStyles.containsKey(styleKey);
    }

    /**
     * Create CellStyle for Date format
     * @param styleKey : The Key for saving to StyleCache
     * @param pattern   : Should be valid DateFormat pattern
     * @return  Excel Style Index
     */
    public short createDateCellStyle(String styleKey, String pattern) {
        XSSFDataFormat fmt = styleWorkbook.createDataFormat();
        XSSFCellStyle styleDate = styleWorkbook.createCellStyle();
        styleDate.setDataFormat(fmt.getFormat(pattern));
        sheetStyles.put(styleKey, styleDate);
        return styleDate.getIndex();
    }
}
