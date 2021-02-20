using DevIgnite.Services.Shared;
using Microsoft.Extensions.Hosting;

namespace DevIgnite.ReportBuilder {
    public class Program {
        public static void Main(string[] args) {
            Host.CreateDefaultBuilder(args).EnableMicroservice<Startup, ReportBuilderConfiguration>().Build().Run();
        }
    }
}