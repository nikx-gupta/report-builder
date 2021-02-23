package org.devignite.reportBuilder.dataProvider.mongodb;

import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.devignite.reportBuilder.dataProvider.abstractions.IDataRow;

import java.util.Iterator;

public class MongoIterator implements Iterator<IDataRow> {
    private MongoCursor<Document> _documents;

    public MongoIterator(MongoCursor<Document> documentCursor) {
        _documents = documentCursor;
    }

    @Override
    public boolean hasNext() {
        return _documents.hasNext();
    }

    @Override
    public IDataRow next() {
        return new MongoDataRow(_documents.next());
    }
}
