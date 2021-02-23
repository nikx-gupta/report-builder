package org.devignite.reportBuilder.dataProvider.abstractions;

import java.util.Iterator;

public interface IDataSource {
    IDataSet loadData();

    Iterator<IDataRow> loadDataIterator();

    void initSettings(DataProviderSettings settings);
}
