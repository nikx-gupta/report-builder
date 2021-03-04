package org.devignite.reportBuilder.dataProvider.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.BsonValue;
import org.bson.Document;
import org.devignite.reportBuilder.dataProvider.abstractions.DataProviderSettings;
import org.devignite.reportBuilder.dataProvider.abstractions.IDataRow;
import org.devignite.reportBuilder.dataProvider.abstractions.IDataSet;
import org.devignite.reportBuilder.dataProvider.abstractions.IDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

import static com.mongodb.client.model.Filters.gte;
import static org.springframework.boot.autoconfigure.condition.ConditionOutcome.match;

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

        return new MongoDataSet(mongoTemplate.findAll(Document.class, _settings.getSourceCollectionName()));
    }

    @Override
    public Iterator<IDataRow> loadDataIterator() {
        MongoCollection<Document> coll = mongoTemplate.getCollection(_settings.getSourceCollectionName());
        long count = coll.countDocuments();
        return new MongoIterator(coll.find(Document.class).iterator());
    }

    @Override
    public void initSettings(DataProviderSettings settings) {
        _settings = (MongoDataProviderSettings) settings;
    }
}




