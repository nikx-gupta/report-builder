using System;

namespace DevIgnite.ReportBuilderLibrary.DataProvider.Abstractions {
    public interface IDataRow {
        int ColumnCount { get; }
        Object GetCellValue(string fieldName);
    }
}