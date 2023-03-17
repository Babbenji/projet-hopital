using MongoDB.Bson.Serialization.Attributes;
using MongoDB.Bson;

namespace micro_service.Models
{
    public class Facture
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        public string? Id { get; set; }

        [BsonElement("date")]
        public DateTime? DateFature { get; set; }

        [BsonElement("patient")]
        public string? NomPatient { get; set; }

        [BsonElement("produits")]
        public ICollection<Produit>? Produits { get; set; }
    }
}
