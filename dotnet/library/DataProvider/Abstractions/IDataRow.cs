namespace DevIgnite.ReportBuilderLibrary.DataProvider.Abstractions {
    public interface IDataRow {
        int ColumnCount { get; }
        object GetCellValue(string fieldName);
    }
}