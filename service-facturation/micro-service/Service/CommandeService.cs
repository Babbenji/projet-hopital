using micro_service.Models;
using micro_service.Repository;

namespace micro_service.Service
{
    public class CommandeService : ICommandeService
    {
        private readonly ICommendRepository commendRepository;

        public CommandeService(ICommendRepository commendRepository)
        {
            this.commendRepository = commendRepository;
        }

        public Commande Create(Commande entity)
        {
            return this.commendRepository.Create(entity);
        }

        public void Delete(int id)
        {
            this.commendRepository.Delete(id);
        }

        public List<Commande> GetAll()
        {
            return this.commendRepository.GetAll();
        }

        public Commande GetById(int id)
        {
           return this.GetById(id);
        }

        public void Update(int id, Commande entity)
        {
            this.commendRepository.Update(id, entity);
        }
    }
}
