package org.devignite.reportBuilder.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StyleFormat {
    private String textColor;
    private String backgroundColor;
    private String fontName;
}
