
namespace micro_service.Models.DTO
{
    public class PatientModel
    {
        
        public string prenom { get; set; } = string.Empty;

        public string nom { get; set; } = string.Empty;

        public string email { get; set; } = string.Empty;

        public string numSecu { get; set; } = string.Empty;

        public string numTel { get; set; } = string.Empty;

        public string dateNaissance { get; set; } = string.Empty;

        public string genre { get; set; } = string.Empty;

        public int idMedecinTraitant { get; set; } 

        public string antecedents { get; set; } = string.Empty;
    }
}
