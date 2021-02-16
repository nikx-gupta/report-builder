package org.devignite.reportBuilder;

import lombok.Data;

import java.util.List;

@Data
public class ReportManifest {
    private String collectionName;
    private String outputFormat;
//    private List<String> columns;
    public List<ColumnFormat> columns;
}

