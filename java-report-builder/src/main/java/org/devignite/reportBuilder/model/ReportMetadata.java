package org.devignite.reportBuilder.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("ReportMetadata")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportMetadata {
    @Id
    private String title;
    private String outputFormat;
    private List<ColumnFormat> columns;
    private StyleFormat headerStyle;
}

