package datasource

import (
	"context"
	"fmt"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
	"log"
	"strings"
	"time"
)

type IDataRow interface {
	GetColumn(fieldName string) interface{}
}

type MongoDataRow struct {
	row bson.M
}

func CreateDataRow(d bson.D) *MongoDataRow {
	return &MongoDataRow{row: d.Map()}
}

func (t *MongoDataRow) GetColumn(fieldName string) interface{} {
	if strings.Index(fieldName, ".") == -1 {
		if t.row[fieldName] != nil {
			return fmt.Sprint(t.row[fieldName])
		}
	} else {
		fieldSplit := strings.Split(fieldName, ".")
		if t.row[fieldSplit[0]] != nil {
			fd := t.row[fieldSplit[0]].(bson.D).Map()
			if fd[fieldSplit[1]] != nil {
				return fmt.Sprint(fd[fieldSplit[1]])
			} else {
				return ""
			}
		}
	}

	return ""
}

func CreateMongoClient(databaseName string) *mongo.Database {
	client, err := mongo.NewClient(options.Client().ApplyURI("mongodb://192.168.29.69:27017"))
	if err != nil {
		log.Panicf("Error in client connection: %s", err)
	}

	ctx, _ := context.WithTimeout(context.Background(), 10*time.Second)
	err = client.Connect(ctx)
	if err != nil {
		log.Fatalf("Connection Error: %s", err)
	}

	return client.Database(databaseName)
}
