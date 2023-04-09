using MongoDB.Bson.Serialization.Attributes;

namespace micro_service.Models
{
    public class Patient
    {
        [BsonElement("prenom")]
        public string? prenom { get; set; }

        [BsonElement("nom")]
        public string? nom { get; set; }

        [BsonElement("email")]
        public string? email { get; set; }

        [BsonElement("numSecu")]
        public string? numSecu { get; set; }

        [BsonElement("numTel")]
        public string? numTel { get; set; }

        [BsonElement("dateNaissance")]
        public string? dateNaissance { get; set; }

        [BsonElement("genre")]
        public string? genre { get; set; }

        [BsonElement("idMedecinTraitant")]
        public int idMedecinTraitant { get; set; }

        [BsonElement("antecedents")]
        public string? antecedents { get; set; }
    }
}
