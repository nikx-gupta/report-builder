namespace DevIgnite.ReportBuilderLibrary.DataProvider {
    public interface IDataProviderSettings { }

    public class MongoDataProviderSettings : IDataProviderSettings {
        public MongoDataProviderSettings(string databaseName, string collectionName) {
            DatabaseName = databaseName;
            CollectionName = collectionName;
        }

        public string DatabaseName { get; }
        public string CollectionName { get; }
    }
}