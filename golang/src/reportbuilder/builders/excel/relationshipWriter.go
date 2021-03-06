package excel

func WriteXlRelationship(sheetName string) {

	rs := &Relationships{
		Relationship: []Relationship{},
	}

	rs.Relationship = append(rs.Relationship, Relationship{
		Id:     "rId1",
		Type:   "http://schemas.openxmlformats.org/officeDocument/2006/relationships/sharedStrings",
		Target: "sharedStrings.xml"})

	rs.Relationship = append(rs.Relationship, Relationship{
		Id:     "rId2",
		Type:   "http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles",
		Target: "styles.xml"})

	rs.Relationship = append(rs.Relationship, Relationship{
		Id:     "rId3",
		Type:   "http://schemas.openxmlformats.org/officeDocument/2006/relationships/worksheet",
		Target: "worksheets/" + sheetName})

	WriteEncodedXmlContent("xl/_rels","workbook.xml.rels", rs)
}

func WriteRootRelationship(workbookName string) {

	rs := &Relationships{
		Relationship: []Relationship{},
	}

	rs.Relationship = append(rs.Relationship, Relationship{
		Id:     "rId1",
		Type:   "http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument",
		Target: "xl/" + workbookName})

	rs.Relationship = append(rs.Relationship, Relationship{
		Id:     "rId2",
		Type:   "http://schemas.openxmlformats.org/officeDocument/2006/relationships/extended-properties",
		Target: "docProps/app.xml"})

	rs.Relationship = append(rs.Relationship, Relationship{
		Id:     "rId3",
		Type:   "http://schemas.openxmlformats.org/package/2006/relationships/metadata/core-properties",
		Target: "docProps/core.xml"})

	WriteEncodedXmlContent("_rels", ".rels", rs)
	WriteDocumentProperties()
	WriteCoreXMl()
}

func WriteDocumentProperties() {
	WriteXmlStringContent("docProps", "app.xml",
		"<Properties xmlns=\"http://schemas.openxmlformats.org/officeDocument/2006/extended-properties\"><Application>DevIgnite</Application></Properties>")
}

func WriteCoreXMl() {
	data := `
		<coreProperties xmlns="http://schemas.openxmlformats.org/package/2006/metadata/core-properties" xmlns:cp="http://schemas.openxmlformats.org/package/2006/metadata/core-properties" xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:dcterms="http://purl.org/dc/terms/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
		  <dcterms:created xsi:type="dcterms:W3CDTF">2021-03-05T16:17:15Z</dcterms:created>
		  <dc:creator>DevIgnite</dc:creator>
		</coreProperties>
		`
	WriteXmlStringContent("docProps", "core.xml", data)
}
