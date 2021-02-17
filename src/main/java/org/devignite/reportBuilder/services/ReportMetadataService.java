package org.devignite.reportBuilder.services;

import org.devignite.reportBuilder.model.ReportMetadata;
import org.devignite.reportBuilder.repository.ReportManifestRepository;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportMetadataService {

    @Autowired
    ReportManifestRepository reportManifestRepository;

    public ReportMetadata get(String title) {
        return reportManifestRepository.findById(title).get();
    }

    public ReportMetadata add(ReportMetadata reportMetadata) {
        if (reportManifestRepository.existsById(reportMetadata.getTitle()))
            throw new InvalidPropertyException(ReportMetadata.class, "title", "This title already exists");

        return reportManifestRepository.insert(reportMetadata);
    }

    public ReportMetadata update(ReportMetadata reportMetadata) {
        if (!reportManifestRepository.existsById(reportMetadata.getTitle()))
            throw new InvalidPropertyException(ReportMetadata.class, "title", "This title already exists");

        return reportManifestRepository.save(reportMetadata);
    }

    public void delete(String title) {
        if (!reportManifestRepository.existsById(title))
            throw new IllegalArgumentException("This title does not exists");

        reportManifestRepository.deleteById(title);
    }
}
