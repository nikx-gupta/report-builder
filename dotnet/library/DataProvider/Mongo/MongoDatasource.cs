using System.Threading.Tasks;
using DevIgnite.ReportBuilderLibrary.DataProvider.Abstractions;
using MongoDB.Bson;
using MongoDB.Driver;

namespace DevIgnite.ReportBuilderLibrary.DataProvider {
    public class MongoDatasource : IDatasource {
        private readonly IMongoCollection<BsonDocument> _dataCollection;

        public MongoDatasource(IMongoCollection<BsonDocument> dataCollection) {
            _dataCollection = dataCollection;
        }

        public async Task<IDataset> Load() {
            return new MongoDataset(await (await _dataCollection.FindAsync(FilterDefinition<BsonDocument>.Empty)).ToListAsync());
        }
    }
}