package org.devignite.reportBuilder;

import java.io.OutputStream;

public interface IReportBuilder {
    void generateReport(ReportManifest reportManifest, OutputStream outputStream);
}
