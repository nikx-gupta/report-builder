using DevIgnite.Services.Core.Configuration;

namespace DevIgnite.ReportBuilder {
    public class MongoConfiguration : IConfigSettings {
        public string ConnectionString { get; set; }
        public string ConfigKey => nameof(MongoConfiguration);
    }
}