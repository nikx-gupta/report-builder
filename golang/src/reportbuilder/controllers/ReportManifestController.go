package controllers

import (
	"devignite/reportbuilder/datasource"
	"devignite/reportbuilder/repository"
	"github.com/emicklei/go-restful"
)

type ReportManifestController struct {
}

func (t *ReportManifestController) RegisterRoutes(container *restful.Container) {
	ws := new(restful.WebService)
	ws.Path("/api/reportMetadata").
		Produces(restful.MIME_JSON)

	repo := repository.CreateReportManifestRepository(datasource.CreateMongoClient("sample_mflix"))
	ws.Route(ws.GET("/{title}").To(func(req *restful.Request, resp *restful.Response) {
		resp.WriteEntity(repo.Get(req.PathParameter("title")))
	}))
	//.Param(ws.PathParameter("title", "Manifest Title").DataType("string")).Writes(models.ReportManifest{}))

	container.Add(ws)
}
