package models

type ReportManifest struct {
	Title        string `bson:"_id"`
	OutputFormat string `bson:"outputFormat"`
	Columns	[]Column
}

type Column struct {
	SourceFieldName string `bson:"sourceFieldName"`
	OutputName string `bson:"outputName"`
	Order int	`bson:"order"`
	OutputValueFormat string `json:"outputValueFormat"`
}
