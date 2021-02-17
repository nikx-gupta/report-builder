package org.devignite.reportBuilder.dataProvider;

import org.bson.Document;
import org.devignite.reportBuilder.dataProvider.abstractions.IDataRow;
import org.devignite.reportBuilder.dataProvider.abstractions.IDataSet;

import java.util.List;

public class MongoDataSet implements IDataSet {
    private final List<Document> _data;

    public MongoDataSet(List<Document> documents) {
        _data = documents;
    }

    @Override
    public int getRowCount() {
        return _data.size();
    }

    @Override
    public IDataRow getRow(Integer rowIndex) {
        return new MongoDataRow(_data.get(rowIndex));
    }
}

