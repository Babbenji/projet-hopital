using micro_service.Models;
using micro_service.Repository;

namespace micro_service.Service
{
    public class FactureService : IFactureService
    {
        private readonly IFactureRepository factureRepository;
        public FactureService(IFactureRepository factureRepository) 
        {
            this.factureRepository = factureRepository;
        }
        public Facture Create(Facture entity)
        {
            return this.factureRepository.Create(entity);
        }

        public void Delete(string id)
        {
            this.factureRepository.Delete(id);
        }

        public List<Facture> GetAll()
        {
            return this.factureRepository.GetAll();
        }

        public Facture GetById(string id)
        {
            return this.factureRepository.GetById(id);
        }

        public void Update(string id, Facture entity)
        {
            this.factureRepository.Update(id, entity);
        }
    }
}
