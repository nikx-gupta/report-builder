package org.devignite.reportBuilder.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ColumnFormat {
    private String sourceFieldName;
    private String outputName;
    private Integer order;
    private StyleFormat columnStyle;
    private String outputValueFormat;
}

