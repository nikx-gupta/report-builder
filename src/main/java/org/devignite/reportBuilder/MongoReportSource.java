package org.devignite.reportBuilder;

import org.bson.BsonDocument;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

public class MongoReportSource {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Document> getData(ReportManifest reportManifest) {
        return mongoTemplate.findAll(Document.class, reportManifest.getCollectionName());
    }
}
