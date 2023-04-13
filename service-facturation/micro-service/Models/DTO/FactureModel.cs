
namespace micro_service.Models.DTO
{
    public class FactureModel
    {
        public PatientModel patient { get; set; } = new ();
        public string type { get; set; } = string.Empty;
        public Dictionary<string, int> listeProduits { get; set; } = new();
        public double coutDuPatient { get; set; }
    }
}
