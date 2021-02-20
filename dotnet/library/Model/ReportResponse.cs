namespace DevIgnite.ReportBuilderLibrary {
    public class ReportResponse {
        public ReportResponse(byte[] content, string mimeType, string fileName) {
            Content = content;
            MimeType = mimeType;
            FileName = fileName;
        }

        public byte[] Content { get; set; }
        public string MimeType { get; set; }
        public string FileName { get; set; }
    }
}