using DevIgnite.Services.Core.Configuration;

namespace DevIgnite.ReportBuilderLibrary.Configuration {
    public class ReportMetadataConfiguration : IConfigSettings {
        public string MongoDb { get; set; }
        public string ConfigKey => nameof(ReportMetadataConfiguration);
    }
}