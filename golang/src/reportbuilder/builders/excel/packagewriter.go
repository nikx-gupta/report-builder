package excel

import (
	"bufio"
	"encoding/xml"
	"os"
)

type Relationships struct {
	XMLName      xml.Name `xml:"http://schemas.openxmlformats.org/package/2006/relationships Relationships"`
	Relationship []Relationship
}

type Relationship struct {
	XMLName xml.Name `xml:"Relationship"`
	Id      string   `xml:"Id,attr""`
	Type    string   `xml:"Type,attr"`
	Target  string   `xml:"Target,attr"`
}

type StyleSheet struct {
	XMLName xml.Name `xml:"http://schemas.openxmlformats.org/spreadsheetml/2006/main styleSheet"`
}

func WritePackage() {
	writer := bufio.NewWriter(os.Stdout)
	WriteFonts(writer, []StyleFont{
		{Size: 11.0, Color: 8, Name: "Calibri", Family: 2, Scheme: "minor"},
	})
	writer.Flush()

	//// Write Root Directories
	//WriteRootRelationship("workbook.xml")
	//// Write xl directory
	//WriteXlRelationship("sheet1.xml")
	//WriteSharedStringsXml()
}

func WriteSharedStringsXml() {
	WriteXmlStringContent("xl", "sharedStrings.xml", `<sst count="0" uniqueCount="0" xmlns="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>`)
}
