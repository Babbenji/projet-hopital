using micro_service.Models;
using MongoDB.Driver;

namespace micro_service.Repository
{
    public class CommendRepository : ICommendRepository
    {
        private readonly IMongoCollection<Commande> commandes;

        public CommendRepository(IMongoClient mongoClient)
        {
            IMongoDatabase database = mongoClient.GetDatabase("facturation");
            this.commandes = database.GetCollection<Commande>("commande");
        }

        public Commande Create(Commande entity)
        {
            this.commandes.InsertOne(entity);
            return entity;
        }

        public void Delete(int id)
        {
            this.commandes.DeleteOne(cmd => cmd.idCommande == id);
        }

        public List<Commande> GetAll()
        {
            return this.commandes.Find(cmd => true).ToList();
        }

        public Commande GetById(int id)
        {
            return this.commandes.Find(cmd => cmd.idCommande == id).SingleOrDefault();
        }

        public void Update(int id, Commande entity)
        {
            this.commandes.ReplaceOne(cmd => cmd.idCommande == id, entity);
        }

       
    }
}
