package org.devignite.reportBuilder.dataProvider.abstractions;

public interface IDataSource {
    IDataSet loadData();

    void initSettings(DataProviderSettings settings);
}
