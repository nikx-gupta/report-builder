using DevIgnite.ReportBuilderLibrary.DataProvider.Abstractions;
using MongoDB.Bson;

namespace DevIgnite.ReportBuilderLibrary.DataProvider {
    public class MongoDataRow : IDataRow {
        private readonly BsonDocument _rowDocument;

        public MongoDataRow(BsonDocument rowDocument) {
            _rowDocument = rowDocument;
        }

        public int ColumnCount => _rowDocument.ElementCount;

        public object GetCellValue(string fieldName) {
            BsonValue dataValue = BsonNull.Value;
            if (fieldName.IndexOf('.') >= 0) {
                var fieldSplit = fieldName.Split(".");
                var childElement = _rowDocument;
                for (var i = 0; i < fieldSplit.Length - 1; i++)
                    if (childElement.TryGetElement(fieldSplit[i], out var el_1))
                        childElement = el_1.Value.AsBsonDocument;
                    else // If some nested element not found no point in continuing
                        return null;

                if (childElement.TryGetElement(fieldSplit[^1], out var element)) dataValue = element.Value;
            }
            else {
                if (_rowDocument.TryGetElement(fieldName, out var element)) dataValue = element.Value;
            }

            if (dataValue.BsonType == BsonType.String) return dataValue.AsString;

            if (dataValue.BsonType == BsonType.DateTime) return dataValue.ToUniversalTime();

            if (dataValue.BsonType == BsonType.Int32) return dataValue.AsInt32;

            return null;
        }
    }
}