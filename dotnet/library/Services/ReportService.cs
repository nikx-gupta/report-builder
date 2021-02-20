using System;
using System.IO;
using System.Threading.Tasks;
using DevIgnite.ReportBuilderLibrary.Configuration;
using DevIgnite.ReportBuilderLibrary.DataProvider;
using DevIgnite.ReportBuilderLibrary.Model;
using MongoDB.Driver;

namespace DevIgnite.ReportBuilderLibrary {
    public class ReportService {
        private readonly ReportBuilderFactory _reportBuilderFactory;
        private readonly IMongoCollection<ReportMetadata> _reportCollection;

        public ReportService(MongoClient mongoClient, ReportMetadataConfiguration metadataConfiguration, ReportBuilderFactory reportBuilderFactory) {
            _reportBuilderFactory = reportBuilderFactory;
            _reportCollection = mongoClient.GetDatabase(metadataConfiguration.MongoDb).GetCollection<ReportMetadata>(nameof(ReportMetadata));
        }

        public async Task<ReportResponse> generateReport(string reportMetadataTitle) {
            var reportMetadata = _reportCollection.Find(x => x.Title == reportMetadataTitle).FirstOrDefault();

            var reportBuilder = _reportBuilderFactory.CreateReportBuilder(Enum.Parse<ReportBuilderType>(reportMetadata.OutputFormat, true));
            var datasource = _reportBuilderFactory.CreateDatasource(DataProviderType.Mongo, new MongoDataProviderSettings("sample_mflix", "movies"));

            var outputFileName = ReportBuilderHelper.UnigueFileNameWithTimestamp(reportMetadataTitle, reportMetadata.OutputFormat);
            await using (var outputStream = File.Create(outputFileName)) {
                reportBuilder.Generate(await datasource.Load(), reportMetadata, outputStream);
            }

            ReportResponse response = new (File.ReadAllBytes(outputFileName), ReportLibraryConstants.MIME[reportMetadata.OutputFormat], outputFileName);
            
            File.Delete(outputFileName);

            return response;
        }
    }
}