using System;
using DevIgnite.ReportBuilderLibrary.Builders;
using DevIgnite.ReportBuilderLibrary.DataProvider;
using DevIgnite.ReportBuilderLibrary.DataProvider.Abstractions;
using Microsoft.Extensions.DependencyInjection;
using MongoDB.Bson;
using MongoDB.Driver;

namespace DevIgnite.ReportBuilderLibrary {
    public class ReportBuilderFactory {
        private readonly IServiceProvider _serviceProvider;

        public ReportBuilderFactory(IServiceProvider serviceProvider) {
            _serviceProvider = serviceProvider;
        }

        public IDatasource CreateDatasource(DataProviderType dataProviderType, IDataProviderSettings settings) {
            if (dataProviderType == DataProviderType.Mongo) {
                return CreateMongoDataSource(settings as MongoDataProviderSettings);
            }

            throw new FormatException($"DataProvider \"{dataProviderType}\" not supported");
        }
        
        public IReportBuilder CreateReportBuilder(ReportBuilderType reportBuilderType) {
            if (reportBuilderType == ReportBuilderType.XLSX) {
                return new ExcelUsingRawXmlBuilder();
            }

            throw new FormatException($"ReportBuilderType \"{reportBuilderType}\" not supported");
        }

        private MongoDatasource CreateMongoDataSource(MongoDataProviderSettings settings) {
            return new (_serviceProvider.GetService<MongoClient>().GetDatabase(settings.DatabaseName).GetCollection<BsonDocument>(settings.CollectionName));
        }
    }

    public enum DataProviderType {
        Mongo
    }

    public enum ReportBuilderType {
        XLSX
    }
}