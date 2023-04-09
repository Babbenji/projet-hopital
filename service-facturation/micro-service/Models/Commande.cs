

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
        public Dictionary<string, int>? produitsCommande { get; set; }

        public override string ToString()
        {
            string tmp = "{";

            for(int i = 0;  i < produitsCommande.Count; i++)
            {
                tmp += produitsCommande.Keys.ToList()[i] + ": " + produitsCommande.Values.ToList()[i] + ", \n"; 
            }
            tmp += "}"; 
            return "{ _id : " + idCommande + ", date: " + dateCommande + ", prix: " + prixCommande + ", produits: " + tmp + "}";
        }
    }
}
