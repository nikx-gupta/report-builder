namespace DevIgnite.ReportBuilderLibrary.DataProvider.Abstractions {
    public interface IDataset {
        int RowSize { get; }
        IDataRow GetRow(int rowIndex);
    }
}