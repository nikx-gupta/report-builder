using System;
using System.Collections.Generic;

namespace DevIgnite.ReportBuilderLibrary {
    public static class ReportLibraryConstants {
        public static Dictionary<string, string> MIME = new(StringComparer.OrdinalIgnoreCase) {
            { ReportBuilderType.XLSX.ToString(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" }
        };

        public const string FORMAT_DEFAULT_DATE = "MMM-dd-yy";
        
        public const string FORMAT_STR = "STR:";
        public const string FORMAT_DATE = "DT:";

    }
}