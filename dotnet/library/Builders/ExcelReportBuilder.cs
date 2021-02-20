using System;
using System.IO;
using ClosedXML.Excel;
using DevIgnite.ReportBuilderLibrary.DataProvider.Abstractions;
using DevIgnite.ReportBuilderLibrary.Model;

namespace DevIgnite.ReportBuilderLibrary.Builders {
    public interface IReportBuilder {
        void Generate(IDataset dataset, ReportMetadata reportMetadata, Stream outputStream);
    }

    public class ExcelUsingRawXmlBuilder : IReportBuilder {
        public void Generate(IDataset dataset, ReportMetadata reportMetadata, Stream outputStream) {
            using var workbook = new XLWorkbook(XLEventTracking.Disabled);
            var worksheet = workbook.Worksheets.Add(reportMetadata.Title);

            // Write Header
            var row = worksheet.Row(1);
            for (var i = 0; i < reportMetadata.Columns.Count; i++) row.Cell(i + 1).SetValue(reportMetadata.Columns[i].OutputName);

            for (var rowIndex = 0; rowIndex < dataset.RowSize; rowIndex++) {
                row = worksheet.Row(rowIndex + 2);
                var dataRow = dataset.GetRow(rowIndex);
                for (var cellIndex = 0; cellIndex < reportMetadata.Columns.Count; cellIndex++) {
                    var cellValue = dataRow.GetCellValue(reportMetadata.Columns[cellIndex].SourceFieldName);

                    WriteCellValue(row.Cell(cellIndex + 1), cellValue, reportMetadata.Columns[cellIndex].OutputValueFormat);
                }
            }

            workbook.SaveAs(outputStream);
        }

        private void WriteCellValue(IXLCell cell, object cellValue, string outputValueFormat) {
            if (cellValue == null) {
                cell.SetValue("");
                return;
            }

            if (cellValue is DateTime dt)
                formatDateValue(cell, dt, outputValueFormat);
            else
                cell.SetValue(cellValue);
        }

        private void formatDateValue(IXLCell cell, DateTime cellValue, string outputValueFormat) {
            // LocalDateTime dt = LocalDateTime.ofInstant(Instant.ofEpochMilli(cellValue.getTime()), ZoneId.of("UTC"));
            // Date utcDate = Date.from(dt.atZone(ZoneId.systemDefault()).toInstant());
            //
            if (string.IsNullOrEmpty(outputValueFormat)) {
                // apply default format
                cell.Style.DateFormat.SetFormat(ReportLibraryConstants.FORMAT_DEFAULT_DATE);
                cell.SetValue(cellValue);
            }
            else {
                if (outputValueFormat.StartsWith(ReportLibraryConstants.FORMAT_STR)) {
                    // Convert DateTime to String
                    cell.SetValue(cellValue.ToString(outputValueFormat.Substring(ReportLibraryConstants.FORMAT_STR.Length)));
                }
                else if (outputValueFormat.StartsWith(ReportLibraryConstants.FORMAT_DATE)) {
                    // Change Output Date Pattern
                    cell.Style.DateFormat.SetFormat(outputValueFormat.Substring(ReportLibraryConstants.FORMAT_DATE.Length));
                    cell.SetValue(cellValue);
                }
            }
        }
    }
}