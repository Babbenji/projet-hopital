using MongoDB.Bson.Serialization.Attributes;
using MongoDB.Bson;

namespace micro_service.Models
{
    public class Facture
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        public string Id { get; set; } = string.Empty;

        [BsonElement("date")]
        public DateTime DateFature { get; set; }

        [BsonElement("type")]
        public string type { get; set; } = string.Empty;

        [BsonElement("patient")]
        public Patient patient { get; set; } = new Patient();

        [BsonElement("produits")]
        public Dictionary<string, int> listeProduits { get; set; } = new();

        [BsonElement("montant")]
        public double coutDuPatient { get; set; }



    }
}
