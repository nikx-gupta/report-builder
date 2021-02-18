package org.devignite.reportBuilder.dataProvider.abstractions;

import java.util.function.Consumer;

public interface IDataSet {
    int getRowCount();

    void loadParallel(Consumer<? super IDataRow> action);

    IDataRow getRow(Integer rowIndex);
}
