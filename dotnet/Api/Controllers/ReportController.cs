using System.Runtime.Versioning;
using System.Threading.Tasks;
using DevIgnite.ReportBuilderLibrary;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Primitives;
using Microsoft.Net.Http.Headers;

namespace DevIgnite.ReportBuilder.Controllers {
    [ApiController]
    [Route("api/reports")]
    public class ReportController : ControllerBase {
        private readonly ReportService _reportService;

        public ReportController(ReportService reportService) {
            _reportService = reportService;
        }

        [HttpGet("{title}")]
        public async Task Report(string title) {
            var response = await _reportService.generateReport(title);
            Response.Clear();
            Response.Headers.Clear();
            Response.Headers.Add("Content-Disposition",$"attachment; filename={response.FileName}");
            Response.Headers.Add("Content-Type", response.MimeType);
            Response.Body.WriteAsync(response.Content, 0, response.Content.Length);
        }
    }
}