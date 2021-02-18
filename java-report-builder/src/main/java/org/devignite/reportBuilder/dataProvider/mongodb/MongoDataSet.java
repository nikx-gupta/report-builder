package org.devignite.reportBuilder.dataProvider.mongodb;

import org.bson.Document;
import org.devignite.reportBuilder.dataProvider.abstractions.IDataRow;
import org.devignite.reportBuilder.dataProvider.abstractions.IDataSet;

import java.util.List;
import java.util.function.Consumer;

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
    public void loadParallel(Consumer<? super IDataRow> action) {
        _data.parallelStream().forEach(x -> action.accept(new MongoDataRow(x)));
    }

    @Override
    public IDataRow getRow(Integer rowIndex) {
        return new MongoDataRow(_data.get(rowIndex));
    }
}

