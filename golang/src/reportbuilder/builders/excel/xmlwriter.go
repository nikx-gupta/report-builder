package excel

import (
	"encoding/xml"
	"log"
	"os"
)

func WriteEncodedXmlContent(dirPath string, fileName string, data interface{}) {
	os.MkdirAll(dirPath, 0777)
	xmlFile, err := os.Create(dirPath + "/" + fileName)
	if err != nil {
		log.Panic("Error Creating File: " + fileName, err)
	}
	defer xmlFile.Close()

	// Write XML Header
	_, err = xmlFile.Write([]byte(`<?xml version="1.0" encoding="UTF-8"?>`))
	_, err = xmlFile.WriteString("\n")

	xmlEncoder := xml.NewEncoder(xmlFile)
	xmlEncoder.Indent("  ", "    ")
	err = xmlEncoder.Encode(data)
	if err != nil {
		log.Fatal("Error writing Xml to file", err)
	}
}

func WriteXmlStringContent(dirPath string, fileName string, data string) {
	os.Mkdir(dirPath, 0777)
	xmlFile, err := os.Create(dirPath + "/" + fileName)
	if err != nil {
		log.Panic("Error Creating File: " + fileName, err)
	}
	defer xmlFile.Close()

	// Write XML Header
	_, err = xmlFile.Write([]byte(`<?xml version="1.0" encoding="UTF-8"?>`))
	_, err = xmlFile.WriteString("\n")
	// Write String data
	_, err = xmlFile.WriteString(data)
}
