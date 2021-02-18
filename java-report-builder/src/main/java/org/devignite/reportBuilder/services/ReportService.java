package org.devignite.reportBuilder.services;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.devignite.reportBuilder.ReportBuilderConstants;
import org.devignite.reportBuilder.ReportComponentFactory;
import org.devignite.reportBuilder.dataProvider.DatasourceType;
import org.devignite.reportBuilder.dataProvider.abstractions.IDataSource;
import org.devignite.reportBuilder.dataProvider.mongodb.MongoDataProviderSettings;
import org.devignite.reportBuilder.model.ReportMetadata;
import org.devignite.reportBuilder.repository.ReportManifestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;

@Slf4j
@Service
public class ReportService {

    @Autowired
    ReportComponentFactory componentFactory;

    @Autowired
    ReportManifestRepository reportManifestRepository;

    @SneakyThrows
    public ReportResponse generateReport(String manifestTitle) {
        ReportMetadata reportMetadata = reportManifestRepository.findById(manifestTitle).get();

        MongoDataProviderSettings settings = new MongoDataProviderSettings();
        settings.setSourceCollectionName("movies");
        IDataSource dataSource = componentFactory.createDataProvider(DatasourceType.MONGO, settings);

        String fileName = "ReportData.xlsx";
        File outputFile = new File(fileName);
        outputFile.createNewFile();
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(outputFile);
            componentFactory.createReportBuilder(reportMetadata)
                    .generateReport(dataSource.loadData(), reportMetadata, outputStream);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        } finally {
            outputStream.close();
        }

        ReportResponse reportResponse = new ReportResponse();
        reportResponse.setContent(Files.readAllBytes(outputFile.toPath()));
        reportResponse.setMimeType(ReportBuilderConstants.MIME_EXCEL);
        reportResponse.setFileName(fileName);

        outputFile.delete();

        return reportResponse;
    }
}

