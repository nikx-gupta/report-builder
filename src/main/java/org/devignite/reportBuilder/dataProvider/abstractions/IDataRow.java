package org.devignite.reportBuilder.dataProvider.abstractions;

public interface IDataRow {
    int getColumnSize(Integer rowIndex);

    Object getCellValue(Integer rowIndex, String fieldName);
}
