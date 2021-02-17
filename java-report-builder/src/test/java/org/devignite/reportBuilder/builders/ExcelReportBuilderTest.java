package org.devignite.reportBuilder.builders;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.assertj.core.api.Assertions;
import org.devignite.reportBuilder.ReportBuilderApplication;
import org.devignite.reportBuilder.ReportMetadataController;
import org.devignite.reportBuilder.model.ColumnFormat;
import org.devignite.reportBuilder.model.ReportMetadata;
import org.devignite.reportBuilder.repository.ReportManifestRepository;
import org.devignite.reportBuilder.services.ReportMetadataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ReportManifestRepository.class, ReportMetadataController.class,
        ReportBuilderApplication.class, ReportMetadataService.class})
public class ExcelReportBuilderTest {

    @Autowired
    MongoTemplate mongoTemplate;

//    @Mock
//    ReportMetadataService reportMetadataService;
//
//    @InjectMocks
//    ReportMetadataService metadataService;

    //    @InjectMocks
    @Autowired
    ReportMetadataController metadataController;

    @Test
    public void getTest() {
        ReportMetadata metadata = metadataController.get("testTitle");
        Assert.notNull(metadata);
    }

    @Test
    public void putTest() {
        ReportMetadata testMetadata = mongoTemplate.findById("testTitle", ReportMetadata.class);
//        when(metadataService.get(Mockito.matches("testTitle"))).thenReturn(testMetadata);
        testMetadata.setOutputFormat("csv");
        ReportMetadata metadata = metadataController.put(testMetadata);
        testMetadata = mongoTemplate.findById("testTitle", ReportMetadata.class);

        Assert.hasText(testMetadata.getOutputFormat(), "csv");
    }

    @BeforeEach
    public void addTestData() {
        addTestReportMetadata();
    }

    @Test
    public void test(@Autowired MongoTemplate mongoTemplate) {
        // given

        DBObject objectToSave = BasicDBObjectBuilder.start()
                .add("key", "value")
                .get();

        // when
        mongoTemplate.save(objectToSave, "collection");

        // then
        Assertions.assertThat(mongoTemplate.findAll(DBObject.class, "collection")).extracting("key")
                .containsOnly("value");
    }

    private void addTestReportMetadata() {
        ReportMetadata metadata = new ReportMetadata();
        List<ColumnFormat> columnFormats = new ArrayList<>();

        ColumnFormat colFormat = new ColumnFormat();
        colFormat.setOutputName("EMPL_NAME");
        colFormat.setSourceFieldName("employeeId");
        columnFormats.add(colFormat);

        metadata.setColumns(columnFormats);
        metadata.setOutputFormat("xlsx");
        metadata.setTitle("testTitle");

        mongoTemplate.save(metadata);
    }
}