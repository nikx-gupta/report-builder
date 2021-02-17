package org.devignite.reportBuilder.dataProvider.mongodb;

import org.bson.Document;
import org.devignite.reportBuilder.dataProvider.abstractions.IDataRow;

public class MongoDataRow implements IDataRow {

    private final Document _rowDoc;

    public MongoDataRow(Document rowDoc) {

        _rowDoc = rowDoc;
    }

    @Override
    public int getColumnSize(Integer rowIndex) {
        return _rowDoc.size();
    }

    @Override
    public Object getCellValue(Integer rowIndex, String fieldName) {
        return _rowDoc.get(fieldName);
    }
}
