package org.devignite.reportBuilder;

public class ReportBuilderException extends RuntimeException {
    public ReportBuilderException(String message) {
        super(String.format("ReportBuilder: %s", message));
    }
}
