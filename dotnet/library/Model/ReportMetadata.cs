using System.Collections.Generic;
using MongoDB.Bson.Serialization.Attributes;

namespace DevIgnite.ReportBuilderLibrary.Model {
    [BsonIgnoreExtraElements]
    public class ReportMetadata {
        [BsonId]
        [BsonElement("_id")]
        public string Title { get; set; }
        [BsonElement("outputFormat")]
        public string OutputFormat { get; set; }
        [BsonElement("columns")]
        public List<ColumnFormat> Columns { get; set; }
        [BsonElement("headerStyle")]
        public StyleFormat HeaderStyle { get; set; }
    }

    public class ColumnFormat {
        [BsonElement("sourceFieldName")]
        public string SourceFieldName { get; set; }
        [BsonElement("outputName")]
        public string OutputName { get; set; }
        [BsonElement("order")]
        public int Order { get; set; }
        [BsonElement("columnStyle")]
        public StyleFormat ColumnStyle { get; set; }
        [BsonElement("outputValueFormat")]
        public string OutputValueFormat { get; set; }
    }

    public class StyleFormat {
        [BsonElement("textColor")]
        public string TextColor { get; set; }
        [BsonElement("backgroundColor")]
        public string BackgroundColor { get; set; }
        [BsonElement("fontName")]
        public string FontName { get; set; }
    }
}