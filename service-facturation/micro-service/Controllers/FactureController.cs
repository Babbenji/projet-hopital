using micro_service.Models;
using micro_service.Models.DTO;
using micro_service.Service;
using micro_service.Service.Exceptions;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace micro_service.Controllers
{
    [Route("api/v1/facture")]
    [ApiController]
    [Authorize(Roles = "COMPTABLE")]
    public class FactureController : ControllerBase
    { 
        private readonly IFactureService factureService;
        private readonly IWebHostEnvironment env;

        public FactureController(IFactureService factureService, IWebHostEnvironment env)
        {
            this.factureService = factureService;
            this.env = env;
        }

        [HttpGet]
        public IActionResult GetFactures()
        {
            List<Facture> entities = this.factureService.GetAll();

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
                return BadRequest(ex.Message);
            }
        }

        [HttpGet("chiffre-affaire-annuel")]
        public IActionResult GetChiffreAffaireModel()
        {
            try
            {
                List<ChiffreAffaireAnnuelleModel> chiffreAffaires = this.factureService.GetChiffreAffaireModel();
                return Ok(chiffreAffaires);
            }
            catch (FactureNotFoundException)
            {
                return NoContent();
            }
        }

        [HttpGet("chiffre-affaire-mensuel")]
        public IActionResult GetAllChiffireAffaireByMonthOfYear([FromQuery] int annee)
        {
            try
            {
                ChiffreAffaireDetailsModel chiffreAffaires = this.factureService.GetAllChiffireAffaireByMonthOfYear(annee);
                return Ok(chiffreAffaires);
            }
            catch (FactureNotFoundException ex)
            {
                return BadRequest(ex.Message);
            }
        }



        [HttpGet("patients/{id}")]
        public IActionResult GetFacturesPatient(string id)
        {
            List<Facture> entities = this.factureService.GetAllFacturePatient(id);
            if(entities.Count > 0)
                return Ok(entities);
            else
                return BadRequest("mauvais id patient");
        }


        [HttpGet("send/patients")]
        public IActionResult SendFactureToPatient([FromQuery] string idFacture, [FromQuery] string idPatients)
        {
            try
            {
                this.factureService.SendFactureEmailToPatient(idFacture, idPatients, Path.Combine(env.ContentRootPath, "pdfFile-bill"));
                return Ok("Facure envoyé");
            }
            catch(FactureNotFoundException ex)
            {
                return BadRequest(ex.Message);
            }
   
        }

        [AllowAnonymous]
        [HttpGet("pdf")]
        public IActionResult GetPdf([FromQuery] string idfacture)
        {
            try
            {
                FileStream fileStream = new FileStream(Path.Combine(env.ContentRootPath, "pdfFile-bill", "Facture-" + idfacture + ".pdf"), FileMode.Open, FileAccess.Read);
                return File(fileStream, "application/pdf");
            }
            catch(FileNotFoundException ex) 
            {
                return BadRequest("la facture n'existe plus");
            }
            
        }



    }
}
