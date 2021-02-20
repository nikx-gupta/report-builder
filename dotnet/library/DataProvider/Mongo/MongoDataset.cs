using System.Collections.Generic;
using DevIgnite.ReportBuilderLibrary.DataProvider.Abstractions;
using MongoDB.Bson;

namespace DevIgnite.ReportBuilderLibrary.DataProvider {
    public class MongoDataset : IDataset {
        private readonly List<BsonDocument> _data;

        public MongoDataset(List<BsonDocument> data) {
            _data = data;
        }

        public int RowSize => _data.Count;
        public IDataRow GetRow(int rowIndex) => new MongoDataRow(_data[rowIndex]);
    }
}