package excel

import (
	"fmt"
	"io"
	"strings"
)
type StyleWorkbook struct {
	Fonts []StyleFont
	Fills []StyleFill
	CellStyles []CellStyleXfs
}

type StyleFont struct {
	Size   float32
	Color  int
	Name   string
	Family int
	Scheme string
}

type StyleFill struct {
	PatternType string
}

type CellStyleXfs struct {
	NumFmtId int
	FontId   int
	FillId   int
	BorderId int
}

type NumFormat struct {

}

func (sf *StyleFont) WriteXml() string {
	builder := strings.Builder{}
	builder.WriteString("<font>")
	if len(sf.Name) > 0 {
		builder.WriteString(fmt.Sprintf("<name val=\"%s\" />", sf.Name))
	}
	if sf.Size > 0 {
		builder.WriteString(fmt.Sprintf("<sz val=\"%.1f\" />", sf.Size))
	}
	if sf.Family > 0 {
		builder.WriteString(fmt.Sprintf("<family val=\"%d\" />", sf.Family))
	}
	if sf.Color > 0 {
		builder.WriteString(fmt.Sprintf("<color indexed=\"%d\" />", sf.Color))
	}
	if len(sf.Name) > 0 {
		builder.WriteString(fmt.Sprintf("<scheme val=\"%s\" />", sf.Scheme))
	}
	builder.WriteString("</font>")

	return builder.String()
}

func (sf *StyleFill) WriteXml() string {
	builder := strings.Builder{}
	builder.WriteString("<fill>")
	if len(sf.PatternType) > 0 {
		builder.WriteString(fmt.Sprintf("<patternFill patternType=\"%s\" />", sf.PatternType))
	}
	builder.WriteString("</fill>")

	return builder.String()
}

func (sf *CellStyleXfs) WriteXml() string {
	builder := strings.Builder{}
	builder.WriteString("<xf ")
	if sf.NumFmtId > 0 {
		builder.WriteString(fmt.Sprintf(" numFmtId=\"%d\"", sf.NumFmtId))
	}
	if sf.FillId > 0 {
		builder.WriteString(fmt.Sprintf(" fontId=\"%d\"", sf.FillId))
	}
	if sf.FontId > 0 {
		builder.WriteString(fmt.Sprintf(" fillId=\"%d\"", sf.FontId))
	}
	if sf.BorderId > 0 {
		builder.WriteString(fmt.Sprintf(" borderId=\"%d\"", sf.BorderId))
	}
	builder.WriteString(" />")

	return builder.String()
}

func WriteFonts(writer io.Writer, fonts []StyleFont) {
	writer.Write([]byte(fmt.Sprintf("<fonts count=\"%d\">", len(fonts))))
	for _, font := range fonts {
		writer.Write([]byte(font.WriteXml()))
	}
	writer.Write([]byte("</fonts>"))
}

func WritePatternFills(writer io.Writer, styleFills []StyleFill) {
	writer.Write([]byte(fmt.Sprintf("<fills count=\"%d\">", len(styleFills))))
	for _, fill := range styleFills {
		writer.Write([]byte(fill.WriteXml()))
	}
	writer.Write([]byte("</fills>"))
}
