using System;

namespace DevIgnite.ReportBuilderLibrary {
    public static class ReportBuilderHelper {
        public static string UnigueFileNameWithTimestamp(string prefix, string extension) {
            return $"{prefix}_{DateTime.Now:dd_MMM_yyyy_HH_mm_ss}.{extension}";
        }

        public static string UnigueFileNameWithGuid(string prefix, string extension) {
            return $"{prefix}_{Guid.NewGuid().ToString().Replace("-", "")}.{extension}";
        }
    }
}