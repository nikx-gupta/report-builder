using System.Threading.Tasks;

namespace DevIgnite.ReportBuilderLibrary.DataProvider.Abstractions {
    public interface IDatasource {
        Task<IDataset> Load();
    }
}