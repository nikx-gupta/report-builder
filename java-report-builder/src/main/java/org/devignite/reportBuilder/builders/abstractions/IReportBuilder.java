package org.devignite.reportBuilder.builders.abstractions;

import org.devignite.reportBuilder.dataProvider.abstractions.IDataSet;
import org.devignite.reportBuilder.model.ReportMetadata;

import java.io.OutputStream;

public interface IReportBuilder {
    void generateReport(IDataSet dataset, ReportMetadata reportMetadata, OutputStream outputStream);
}
