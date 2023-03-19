using micro_service.ConsulConfig;
using micro_service.EventBus;
using micro_service.Models;
using micro_service.Service;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore.ChangeTracking;
using Microsoft.Extensions.Options;

namespace micro_service.Controllers
{
    [Route("api/facture")]
    [ApiController]
    public class FactureController : ControllerBase
    {
        private readonly ILogger<FactureController> logger;
        private readonly IFactureService factureService;
        //private readonly IRabbitMQPublisher rabbitMQPublisher;
        
        public FactureController(ILogger<FactureController> logger, IFactureService factureService) 
        {
            this.logger = logger;
            this.factureService = factureService;
            //this.rabbitMQPublisher = rabbitMQPublisher;
        }

        [HttpPost("v1/new-bill")]
        public IActionResult CreationFacture([FromBody] Facture facture) 
        {
            Facture entity = this.factureService.Create(facture);
            return CreatedAtAction(nameof(GetFacture), new { id = entity.Id }, entity);
        }


        [HttpGet("v1/bill")]
        public IActionResult GetFactures()
        {
            List<Facture> entities = this.factureService.GetAll();

            return Ok(entities);
        }

        [HttpGet("v1/bill/{id}")]
        public IActionResult GetFactureWithVersion(string id)
        {
            Facture entity = this.factureService.GetById(id);

            return Ok(entity);
        }

        [HttpGet("bill/{id}")]
        public IActionResult GetFacture(string id)
        {
            Facture entity = this.factureService.GetById(id);

            return Ok(entity);
        }

    }
}
