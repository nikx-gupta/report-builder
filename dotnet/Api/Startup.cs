using DevIgnite.Services.Shared;
using Microsoft.Extensions.Configuration;

namespace DevIgnite.ReportBuilder {
    public class Startup : ServiceStartupBase {
        public Startup(IConfiguration configuration) : base(configuration) { }
    }
}