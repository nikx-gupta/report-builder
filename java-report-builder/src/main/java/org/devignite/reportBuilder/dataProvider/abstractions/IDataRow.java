package org.devignite.reportBuilder.dataProvider.abstractions;

import javax.naming.OperationNotSupportedException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface IDataRow {
    int getColumnSize();

    Object getCellValue(Integer cellIndex) throws OperationNotSupportedException;

    Object getCellValue(String fieldName);

    void foreach(BiConsumer<String, Object> action);
}
