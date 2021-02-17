package org.devignite.reportBuilder.services;

import lombok.Data;

@Data
public class ReportResponse {
    private byte[] content;
    private String fileName;
    private String mimeType;
}
