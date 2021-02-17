package org.devignite.excelwriter;

import lombok.extern.slf4j.Slf4j;
import org.devignite.reportBuilder.model.ReportMetadata;
import org.devignite.reportBuilder.services.ReportMetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/metadata")
public class ReportMetadataController {

    @Autowired
    ReportMetadataService reportMetadataService;

    @GetMapping(value = "/")
    public ReportMetadata get(@RequestParam String title) {
        return reportMetadataService.get(title);
    }


    @PostMapping(value = "/")
    public ReportMetadata post(@RequestBody ReportMetadata reportMetadata) {
        return reportMetadataService.add(reportMetadata);
    }

    @PutMapping(value = "/")
    public ReportMetadata put(@RequestBody ReportMetadata reportMetadata) {
        return reportMetadataService.update(reportMetadata);
    }


    @DeleteMapping(value = "/")
    public ResponseEntity<String> delete(@RequestParam String title) {
        reportMetadataService.delete(title);
        return ResponseEntity.ok("OK");
    }
}
