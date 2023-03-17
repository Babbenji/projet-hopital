using micro_service.Models;
using MongoDB.Driver;

namespace micro_service.Repository
{
    public class FactureRepository : IFactureRepository
    {
        private readonly IMongoCollection<Facture> factures;

        public FactureRepository(IMongoClient mongoClient)
        {
            IMongoDatabase database = mongoClient.GetDatabase("facturation");
            this.factures = database.GetCollection<Facture>("factures");
        }

        public Facture Create(Facture entity)
        {
            this.factures.InsertOne(entity);
            return entity;
        }

        public void Delete(string id)
        {
            this.factures.DeleteOne(fac => fac.Id == id);
        }

        public List<Facture> GetAll()
        {
            return this.factures.Find(fac => true).ToList();
        }

        public Facture GetById(string id)
        {
            return this.factures.Find(fac => fac.Id == id).SingleOrDefault();
        }

        public void Update(string id, Facture entity)
        {
            this.factures.ReplaceOne(fac => fac.Id == id, entity);
        }
    }
}
