package org.devignite.reportBuilder.dataProvider.abstractions;

public interface IDataSet {
    int getRowCount();

    IDataRow getRow(Integer rowIndex);
}
