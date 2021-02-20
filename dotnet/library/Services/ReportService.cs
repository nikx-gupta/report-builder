using System;
using System.IO;
using System.Threading.Tasks;
using DevIgnite.ReportBuilderLibrary.Builders;
using DevIgnite.ReportBuilderLibrary.Configuration;
using DevIgnite.ReportBuilderLibrary.DataProvider;
using DevIgnite.ReportBuilderLibrary.DataProvider.Abstractions;
using DevIgnite.ReportBuilderLibrary.Model;
using MongoDB.Driver;

namespace DevIgnite.ReportBuilderLibrary {
    public class ReportService {
        private readonly ReportBuilderFactory _reportBuilderFactory;
        private IMongoCollection<ReportMetadata> _reportCollection;

        public ReportService(MongoClient mongoClient, ReportMetadataConfiguration metadataConfiguration, ReportBuilderFactory reportBuilderFactory) {
            _reportBuilderFactory = reportBuilderFactory;
            _reportCollection = mongoClient.GetDatabase(metadataConfiguration.MongoDb).GetCollection<ReportMetadata>(nameof(ReportMetadata));
        }

        public async Task<ReportResponse> generateReport(string reportMetadataTitle) {
            ReportMetadata reportMetadata = _reportCollection.Find(x => x.Title == reportMetadataTitle).FirstOrDefault();

            IReportBuilder reportBuilder = _reportBuilderFactory.CreateReportBuilder(Enum.Parse<ReportBuilderType>(reportMetadata.OutputFormat, true));
            IDatasource datasource = _reportBuilderFactory.CreateDatasource(DataProviderType.Mongo, new MongoDataProviderSettings("sample_mflix", "movies"));

            string outputFileName = ReportBuilderHelper.UnigueFileNameWithTimestamp(reportMetadataTitle, reportMetadata.OutputFormat);
            await using (var outputStream = File.Create(outputFileName)) {
                reportBuilder.Generate(await datasource.Load(), reportMetadata, outputStream);
            }

            return new ReportResponse(File.ReadAllBytes(outputFileName), ReportLibraryConstants.MIME[reportMetadata.OutputFormat], outputFileName);
        }
    }
}