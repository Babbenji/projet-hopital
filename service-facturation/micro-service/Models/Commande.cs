using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace micro_service.Models
{
    public class Commande
    {
        [BsonId]
        [BsonRepresentation(BsonType.Int32)]
        public int idCommande { get; set; }

        [BsonElement("date")]
        public DateTime dateCommande { get; set; }

        [BsonElement("prix")]
        public double prixCommande { get; set; }

        [BsonElement("produits")]
        public Dictionary<string, int> produitsCommande { get; set; } = new();
        
    }
}
