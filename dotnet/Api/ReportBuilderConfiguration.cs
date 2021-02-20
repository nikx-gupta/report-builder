using Autofac;
using DevIgnite.ReportBuilderLibrary;
using DevIgnite.ReportBuilderLibrary.Configuration;
using DevIgnite.Services.Core.Configuration;
using Microsoft.Extensions.DependencyInjection;
using MongoDB.Driver;

namespace DevIgnite.ReportBuilder {
    public class ReportBuilderConfiguration : ContainerConfigurationBase {
        protected override void InjectServices() {
            _services.AddSingleton(_configuration.GetSettings<MongoConfiguration>());
            _services.AddSingleton(_configuration.GetSettings<ReportMetadataConfiguration>());
            _services.AddScoped<MongoClient>(sp => new MongoClient(sp.GetService<MongoConfiguration>().ConnectionString));

            _services.AddScoped<IReportMetadataService, ReportMetadataService>();
        }
    }
}