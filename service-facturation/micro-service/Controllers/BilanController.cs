using micro_service.Models.DTO;
using micro_service.Service;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace micro_service.Controllers
{
    [Route("api/v1/bilan")]
    [ApiController]
    [Authorize(Roles = "COMPTABLE")]
    public class BilanController : ControllerBase
    {
        private readonly IBilanService bilanService;

        public BilanController(IBilanService bilanService) 
        {
            this.bilanService = bilanService;
        }

        [HttpGet("recette")]
        public IActionResult GetRecette() 
        {
            List<BilanAnnuelModel> bilans = this.bilanService.GetBilanAnnuelModels();
            if (bilans.Count > 0)
                return Ok(bilans);
            else
                return NoContent();
        }

    }
}
