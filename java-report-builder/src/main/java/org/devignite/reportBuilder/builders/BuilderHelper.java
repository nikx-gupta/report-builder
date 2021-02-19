package org.devignite.reportBuilder.builders;

import org.devignite.reportBuilder.ReportBuilderConstants;
import org.devignite.reportBuilder.model.ColumnFormat;
import org.devignite.reportBuilder.model.ReportMetadata;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class BuilderHelper {
    public Map<String, ColumnFormat> getHeaderSet(ReportMetadata reportMetadata) {
        Map<String, ColumnFormat> headerSet = reportMetadata.getColumns().stream()
                .collect(Collectors.toMap(x -> x.getSourceFieldName().isEmpty() || x.getSourceFieldName().isBlank() ? x.getOutputName() : x.getSourceFieldName(), x -> x, (ov, nv) -> nv, LinkedHashMap::new));

        AtomicInteger orderIndex = new AtomicInteger(0);
        headerSet.forEach((x, y) -> y.setOrder(orderIndex.getAndIncrement()));

        return headerSet;
    }

    public String getUniqueFileNameWithGuid(String fileExtension) {
        return String.format("tmp_%s.%s", UUID.randomUUID().toString().replace("-", ""), fileExtension);
    }

    public String getFileNameWithTimeStamp(String filePrefix, String fileExtension) {
        LocalDateTime ct = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ReportBuilderConstants.DEFAULT_FILE_TIMESTAMP);

        return String.format("%s_%s.%s", filePrefix, ct.format(formatter), fileExtension);
    }
}
