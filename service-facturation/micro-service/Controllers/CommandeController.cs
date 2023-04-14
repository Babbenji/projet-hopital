using micro_service.Models;
using micro_service.Models.DTO;
using micro_service.Service;
using micro_service.Service.Exceptions;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace micro_service.Controllers
{
    [Route("api/v1/commande")]
    [ApiController]
    [Authorize(Roles = "COMPTABLE")]
    public class CommandeController : ControllerBase
    {

        private readonly ICommandeService commandeService;

        public CommandeController(ICommandeService commandeService)
        {
            this.commandeService = commandeService;
        }


        [HttpGet]
        public IActionResult GetCommandes()
        {
            List<Commande> entities = this.commandeService.GetAll();

            return Ok(entities);
        }

        [HttpGet("{id}")]
        public IActionResult GetCommandeById(int id)
        {
            try
            {
                Commande commande = this.commandeService.GetById(id);
                return Ok(commande);
            }
            catch (CommandeNotFoundException ex)
            {
                return BadRequest(ex.Message);
            }

        }
    

        [HttpGet("charge-mensuelle")]
        public IActionResult GetChargeTotalAnnuel([FromQuery] int annee)
        {
            try
            {
                ChargeAnnuelDetailModel charge = this.commandeService.GetAllChargeCommandeByMonthOfYear(annee);
                return Ok(charge);
            }
            catch (CommandeNotFoundException ex) 
            {
                return BadRequest(ex.Message);
            }
        }

        [HttpGet("charge-annuelle")]
        public IActionResult GetAllChargeAnnuel()
        {
            try
            {
                List<ChargeAnnueModel> charge = this.commandeService.GetAllChargeCommandeByYear();
                return Ok(charge);
            }
            catch (CommandeNotFoundException)
            {
                return NoContent();
            }
        }
    }
}
