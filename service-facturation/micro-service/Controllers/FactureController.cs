using micro_service.Models;
using micro_service.Service;
using micro_service.Service.Exceptions;
using Microsoft.AspNetCore.Mvc;

namespace micro_service.Controllers
{
    [Route("api/v1/facture")]
    [ApiController]
    public class FactureController : ControllerBase
    {
        private readonly ILogger<FactureController> logger;
        private readonly IFactureService factureService;
        private readonly ICommandeService commandeService;

        public FactureController(ILogger<FactureController> logger, IFactureService factureService, ICommandeService commandeService)
        {
            this.logger = logger;
            this.factureService = factureService;
            this.commandeService = commandeService;
        }

        [HttpPost]
        public IActionResult CreationFacture([FromBody] Facture facture)
        {
            Facture entity = this.factureService.Create(facture);
            return CreatedAtAction(nameof(GetFacture), new { id = entity.Id }, entity);
        }


        [HttpGet]
        public IActionResult GetFactures()
        {
            List<Facture> entities = this.factureService.GetAll();

            return Ok(entities);
        }

        [HttpGet("cmds")]
        public IActionResult GetCmd()
        {
            List<Commande> entities = this.commandeService.GetAll();

            return Ok(entities);
        }



        [HttpGet("{id}")]
        public IActionResult GetFacture(string id)
        {
            try
            {
                Facture entity = this.factureService.GetById(id);

                return Ok(entity);
            }
            catch (FactureNotFoundException ex)
            {
                return NotFound(ex.Message);
            }
        }

        
        [HttpGet("patients/{id}")]
        public IActionResult GetFacturesPatient(string id)
        {
            List<Facture> entities = this.factureService.GetAllFacturePatient(id);
            return Ok(entities);
        }


        [HttpGet("send/patients")]
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
