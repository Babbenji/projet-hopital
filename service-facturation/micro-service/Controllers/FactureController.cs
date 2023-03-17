using micro_service.EventBus;
using micro_service.Models;
using micro_service.Service;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Options;

namespace micro_service.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class FactureController : ControllerBase
    {
        private readonly ILogger<FactureController> logger;
        private readonly IFactureService factureService;
        private readonly RabbitMQConfig rabbitMQConfig; 
        //private readonly IRabbitMQPublisher rabbitMQPublisher;
        
        public FactureController(ILogger<FactureController> logger, IFactureService factureService, IOptions<RabbitMQConfig> config /*, IRabbitMQPublisher rabbitMQPublisher*/) 
        {
           // this.context = context;
            this.logger = logger;
            this.factureService = factureService;
            this.rabbitMQConfig = config.Value;
            //this.rabbitMQPublisher = rabbitMQPublisher;
        }

        [HttpPost]
        public IActionResult CreationFacture([FromBody] Facture facture) 
        {
            Facture entity = this.factureService.Create(facture);

            return Ok(entity);
        }


        [HttpGet]
        public IActionResult GetFacture()
        {
            List<Facture> entities = this.factureService.GetAll();

            return Ok(entities);
        }

        [HttpGet("{id}")]
        public IActionResult GetFacture(string id)
        {
            Facture entity = this.factureService.GetById(id);

            return Ok(entity);
        }


        [HttpGet("rabbitmq/config")]
        public IActionResult GetConfig()
        {
            return Ok(this.rabbitMQConfig);
        }


    }
}
