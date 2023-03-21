using micro_service.Models;
using micro_service.Service;
using micro_service.Service.Exceptions;
using Microsoft.AspNetCore.Mvc;

namespace micro_service.Controllers
{
    [Route("api/facture")]
    [ApiController]
    public class FactureController : ControllerBase
    {
        private readonly ILogger<FactureController> logger;
        private readonly IFactureService factureService;
        
        public FactureController(ILogger<FactureController> logger, IFactureService factureService) 
        {
            this.logger = logger;
            this.factureService = factureService;
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

        [HttpGet("v1/patients/{id}")]
        public IActionResult GetFacturesPatient(string id)
        {
            List<Facture> entities = this.factureService.GetAllFacturePatient(id);
            return Ok(entities);
        }


        [HttpGet("v1/send/patients")]
       public IActionResult SendFactureToPatient([FromQuery] string idFacture, [FromQuery] string idPatients)
       {
            try
            {
                this.factureService.SendFactureEmail(idFacture, idPatients);
                return Ok("Facture envoyé");
            }
            catch(FactureNotFoundException ex)
            {
                return NotFound(ex.Message);
            }

           
                
       }
    }
}
