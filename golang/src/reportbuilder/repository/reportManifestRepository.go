package repository

import (
	"context"
	"devignite/reportbuilder/models"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/mongo"
	"log"
)

const CollectionName = "ReportMetadata"

type ReportManifestRepository struct {
	db *mongo.Database
}

func CreateReportManifestRepository(db *mongo.Database) *ReportManifestRepository {
	return &ReportManifestRepository{db: db}
}

func (t *ReportManifestRepository) Add(manifest models.ReportManifest) models.ReportManifest {
	res, err := t.db.Collection(CollectionName).InsertOne(context.Background(), manifest)
	if err != nil {
		log.Panic("Error in Inserting Document: ", err)
	}

	if res.InsertedID == nil {
		log.Panic("Error in Inserting Document: ", manifest)
	}

	return manifest
}

func (t *ReportManifestRepository) Get(title string) models.ReportManifest {
	res := t.db.Collection(CollectionName).FindOne(context.Background(), bson.D{{"_id", title}})

	manifest := &models.ReportManifest{}
	err := res.Decode(manifest)
	if err != nil {
		log.Panic("Error in serializing document")
	}

	return *manifest
}

func (t *ReportManifestRepository) Put(manifest models.ReportManifest) models.ReportManifest {
	res, err := t.db.Collection(CollectionName).UpdateOne(context.Background(), bson.D{{"_id", manifest.Title}}, manifest)
	if err != nil {
		log.Panic("Error in Updating Document: ", err)
	}

	if res.MatchedCount == 0 {
		log.Panic("No Document found to update with title: ", manifest.Title)
	}

	return manifest
}
