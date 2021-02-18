package org.devignite.reportBuilder.builders.abstractions;

import org.devignite.reportBuilder.dataProvider.abstractions.IDataSet;
import org.devignite.reportBuilder.model.ReportMetadata;

import javax.naming.OperationNotSupportedException;
import java.io.OutputStream;
import java.util.List;

public interface IReportBuilder {
    void generateReport(IDataSet dataset, ReportMetadata reportMetadata, OutputStream outputStream);

    default void generateMultiSheet(List<IDataSet> dataSet, ReportMetadata reportMetadata, OutputStream outputStream) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }
}
