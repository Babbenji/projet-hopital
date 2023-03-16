using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace micro_service.Models
{
    public class Produit
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int Id { get; set; }

        [Required]
        public string? Nom { get; set; }

        [Required]
        public string? Marque { get; set; }

        public ICollection<Facture>? Factures { get; set; }
    }
}