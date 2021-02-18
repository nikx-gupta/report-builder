package org.devignite.reportBuilder.dataProvider.abstractions;

import javax.naming.OperationNotSupportedException;

public interface IDataRow {
    int getColumnSize(Integer rowIndex);

    Object getCellValue(Integer cellIndex) throws OperationNotSupportedException;

    Object getCellValue(String fieldName);
}
