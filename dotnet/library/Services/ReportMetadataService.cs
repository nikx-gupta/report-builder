using System.Linq;
using System.Threading.Tasks;
using DevIgnite.ReportBuilderLibrary.Configuration;
using DevIgnite.ReportBuilderLibrary.Model;
using DevIgnite.Services.Core.Exceptions;
using MongoDB.Driver;

namespace DevIgnite.ReportBuilderLibrary {
    public interface IReportMetadataService {
        ReportMetadata Add(ReportMetadata reportMetadata);
        Task<ReportMetadata> Get(string title);
        Task<ReportMetadata> Put(ReportMetadata reportMetadata);
    }

    public class ReportMetadataService : IReportMetadataService {
        private IMongoCollection<ReportMetadata> _reportCollection;

        public ReportMetadataService(MongoClient mongoClient, ReportMetadataConfiguration metadataConfiguration) {
            _reportCollection = mongoClient.GetDatabase(metadataConfiguration.MongoDb).GetCollection<ReportMetadata>(nameof(ReportMetadata));
        }

        public async Task<ReportMetadata> Get(string title) {
            var result = (await _reportCollection.FindAsync(x => x.Title == title)).ToList().FirstOrDefault();
            return result;
        }

        public async Task<ReportMetadata> Put(ReportMetadata reportMetadata) {
            return await _reportCollection.FindOneAndUpdateAsync(x => x.Title == reportMetadata.Title, new ObjectUpdateDefinition<ReportMetadata>(reportMetadata));
        }

        public ReportMetadata Add(ReportMetadata reportMetadata) {
            if (_reportCollection.Find(x => x.Title == reportMetadata.Title).FirstOrDefault() != null) { }

            return new ReportMetadata();
        }
    }
}