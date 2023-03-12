using micro_service.Models;
using Microsoft.EntityFrameworkCore;

namespace micro_service.Repository
{
    public class FinanceDbContext : DbContext
    {
        public FinanceDbContext(DbContextOptions<FinanceDbContext> options) : base(options) { }

        public DbSet<Produit> Produit { get; set; }

        public DbSet<Facture> Facture { get; set; }
    }
}
