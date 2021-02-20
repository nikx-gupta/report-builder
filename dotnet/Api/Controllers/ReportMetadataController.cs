using System.Threading.Tasks;
using DevIgnite.ReportBuilderLibrary;
using DevIgnite.ReportBuilderLibrary.Model;
using Microsoft.AspNetCore.Mvc;

namespace DevIgnite.ReportBuilder.Controllers {
    [ApiController]
    [Route("api/reportMetadata")]
    public class ReportMetadataController : ControllerBase {
        private readonly IReportMetadataService _reportMetadataService;

        public ReportMetadataController(IReportMetadataService reportMetadataService) {
            _reportMetadataService = reportMetadataService;
        }

        [HttpGet("{title}")]
        public async Task<ReportMetadata> get(string title) {
            return await _reportMetadataService.Get(title);
        }

        [HttpPut]
        public async Task<ReportMetadata> put(ReportMetadata reportMetadata) {
            return await _reportMetadataService.Put(reportMetadata);
        }
    }
}