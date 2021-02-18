package org.devignite.reportBuilder.dataProvider.mongodb;

import org.bson.Document;
import org.devignite.reportBuilder.dataProvider.abstractions.IDataRow;
import org.springframework.util.CollectionUtils;

import javax.naming.OperationNotSupportedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MongoDataRow implements IDataRow {

    private final Document _rowDoc;
    private final Map<String, List<String>> _cacheNestedFields;

    public MongoDataRow(Document rowDoc) {
        _rowDoc = rowDoc;
        _cacheNestedFields = new HashMap<>();
    }

    @Override
    public int getColumnSize(Integer rowIndex) {
        return _rowDoc.size();
    }

    @Override
    public Object getCellValue(Integer cellIndex) throws OperationNotSupportedException {
        throw new OperationNotSupportedException("Operation Not Supported");
    }

    @Override
    public Object getCellValue(String fieldName) {
        if (fieldName.indexOf(".") > 0) {
            if (_cacheNestedFields.containsKey(fieldName)) {
                return _rowDoc.getEmbedded(_cacheNestedFields.get(fieldName), "");
            }

            String[] fieldSplit = fieldName.split("\\.");
            Object val = _rowDoc.getEmbedded(CollectionUtils.arrayToList(fieldSplit), "");
            return val;
        }

        return _rowDoc.get(fieldName);
    }
}
