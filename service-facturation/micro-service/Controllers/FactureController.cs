using micro_service.EventBus;
using micro_service.Models;
using micro_service.Repository;
using micro_service.Security;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore.ChangeTracking;
using Microsoft.Extensions.Configuration;
using Microsoft.IdentityModel.Tokens;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;

namespace micro_service.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class FactureController : ControllerBase
    {
        private readonly ILogger<FactureController> logger;
        private readonly FinanceDbContext context;
        
        public FactureController(FinanceDbContext context, ILogger<FactureController> logger) 
        {
            this.context = context;
            this.logger = logger;
        }


        [HttpGet("products/{id:int}")]
        public IActionResult GetOneProduct(int id)
        {
            Produit? produit = this.context.Produit.SingleOrDefault(p => p.Id == id);
            return Ok(produit);
        }

        [HttpPost("products")]
        public IActionResult NewFacture(Produit produit)
        {
            EntityEntry<Produit> entityEntry =  this.context.Produit.Add(produit);
            this.context.SaveChanges();
            return CreatedAtAction(nameof(GetOneProduct),new {id = entityEntry.Entity.Id}, produit);
        }

       
    }
}
