package main

import (
	"devignite/reportbuilder/controllers"
	"github.com/emicklei/go-restful"
	"log"
	"net/http"
)

func main() {

	serverContainer := restful.NewContainer()
	serverContainer.Router(restful.CurlyRouter{})

	manifestController := controllers.ReportManifestController{}
	manifestController.RegisterRoutes(serverContainer)

	reportController := controllers.ReportController{}
	reportController.RegisterRoutes(serverContainer)

	server := http.Server{
		Addr:    ":5000",
		Handler: serverContainer,
	}

	log.Println("Starting Listener")
	server.ListenAndServe()
}
