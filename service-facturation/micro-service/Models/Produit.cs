using MongoDB.Bson.Serialization.Attributes;

namespace micro_service.Models
{
    public class Produit
    {

        [BsonElement("nomProduit")]
        public string? Nom { get; set; }


        [BsonElement("nomMarque")]
        public string? Marque { get; set; }


        [BsonElement("quantite")]
        public int Quantite { get; set; }
    }
}