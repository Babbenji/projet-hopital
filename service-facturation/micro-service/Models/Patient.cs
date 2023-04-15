using MongoDB.Bson.Serialization.Attributes;

namespace micro_service.Models
{
    public class Patient
    {
        [BsonElement("prenom")]
        public string prenom { get; set; } = string.Empty;

        [BsonElement("nom")]
        public string nom { get; set; } = string.Empty;

        [BsonElement("email")]
        public string email { get; set; } = string.Empty;

        [BsonElement("numSecu")]
        public string numSecu { get; set; } = string.Empty;

        [BsonElement("numTel")]
        public string numTel { get; set; } = string.Empty;

        [BsonElement("dateNaissance")]
        public string dateNaissance { get; set; } = string.Empty;

        [BsonElement("genre")]
        public string genre { get; set; } = string.Empty;

        [BsonElement("idMedecinTraitant")]
        public int idMedecinTraitant { get; set; }

        [BsonElement("antecedents")]
        public string antecedents { get; set; } = string.Empty;
    }
}
