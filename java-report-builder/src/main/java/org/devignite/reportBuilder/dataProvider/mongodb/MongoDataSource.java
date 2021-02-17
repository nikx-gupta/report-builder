package org.devignite.reportBuilder.dataProvider.mongodb;

import org.bson.Document;
import org.devignite.reportBuilder.dataProvider.abstractions.DataProviderSettings;
import org.devignite.reportBuilder.dataProvider.abstractions.IDataSet;
import org.devignite.reportBuilder.dataProvider.abstractions.IDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class MongoDataSource implements IDataSource {

    @Autowired
    MongoTemplate mongoTemplate;

    MongoDataProviderSettings _settings;

    public MongoDataSource() {

    }

    @Override
    public IDataSet loadData() {
        if (_settings == null)
            throw new NullPointerException("DataSource Settings not provided");

        return new MongoDataSet(mongoTemplate.findAll(Document.class, _settings.getSourceCollectionName()).subList(0, 10));
    }

    @Override
    public void initSettings(DataProviderSettings settings) {
        _settings = (MongoDataProviderSettings) settings;
    }
}




