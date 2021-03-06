package controllers

import (
	"context"
	"devignite/reportbuilder/builders/csv"
	"devignite/reportbuilder/datasource"
	"devignite/reportbuilder/repository"
	"github.com/emicklei/go-restful"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/mongo"
	"log"
)

type ReportController struct {
}

func (t *ReportController) RegisterRoutes(container *restful.Container) {
	ws := new(restful.WebService)
	ws.Path("/api/report").
		Produces(restful.MIME_JSON)

	db := datasource.CreateMongoClient("sample_mflix")
	repo := repository.CreateReportManifestRepository(db)
	ws.Route(ws.GET("/{title}").To(func(req *restful.Request, resp *restful.Response) {
		//writer := bufio.NewWriter(os.Stdout)
		manifest := repo.Get(req.PathParameter("title"))
		csvWriter := csv.NewCsvWriter(resp.ResponseWriter, manifest)
		csvWriter.WriteHeader()
		docChannel := make(chan datasource.IDataRow)
		go Data(db, docChannel)
		csvWriter.WriteRows(docChannel)
		//resp.Write([]byte("written"))
		//resp.WriteEntity(repo.Get(req.PathParameter("title")))
	}))
	//.Param(ws.PathParameter("title", "Manifest Title").DataType("string")).Writes(models.ReportManifest{}))

	container.Add(ws)
}

func Data(db *mongo.Database, docChannel chan datasource.IDataRow) {
	cur, err := db.Collection("movies").Find(context.Background(), bson.D{})
	if err != nil {
		log.Panic("Not able to get data: ", err)
	}

	defer close(docChannel)
	defer cur.Close(context.Background())
	for cur.Next(context.Background()) {
		var result bson.D
		err := cur.Decode(&result)
		if err != nil {
			log.Panic("Unable to decode: ", err)
		}

		docChannel <- datasource.CreateDataRow(result)
	}
}
