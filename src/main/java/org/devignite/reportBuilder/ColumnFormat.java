package org.devignite.reportBuilder;

import lombok.Data;

@Data
public class ColumnFormat implements Comparable<ColumnFormat> {
    private String columnName;
    private String aliasName;
    private Integer order;


    @Override
    public int compareTo(ColumnFormat o) {
        return this.order.compareTo(o.order);
    }
}
