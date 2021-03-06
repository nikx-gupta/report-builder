package csv

import (
	"devignite/reportbuilder/datasource"
	"devignite/reportbuilder/models"
	"fmt"
	"go.mongodb.org/mongo-driver/x/mongo/driver/uuid"
	"io"
)

type CsvWriter struct {
	writer   io.Writer
	manifest models.ReportManifest
	fileName string
}

func NewCsvWriter(writer io.Writer, reportManifest models.ReportManifest) *CsvWriter {
	uuid, _ := uuid.New()
	filename := fmt.Sprintf("%x", uuid)

	return &CsvWriter{writer: writer, manifest: reportManifest, fileName: filename}
}

func (t *CsvWriter) WriteHeader() {
	for index, col := range t.manifest.Columns {
		t.writer.Write([]byte(col.OutputName))
		if index < len(t.manifest.Columns)-1 {
			t.writer.Write([]byte(","))
		}
	}
	t.writer.Write([]byte("\n"))
}

func (t *CsvWriter) WriteRows(docChannel chan datasource.IDataRow) {
	for {
		doc, ok := <-docChannel
		if !ok {
			return
		}
		for index, col := range t.manifest.Columns {
			t.writer.Write([]byte(fmt.Sprint(doc.GetColumn(col.SourceFieldName))))
			if index < len(t.manifest.Columns)-1 {
				t.writer.Write([]byte(","))
			}
		}

		t.writer.Write([]byte("\n"))
	}
}
