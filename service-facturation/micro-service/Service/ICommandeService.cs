using micro_service.Models;

namespace micro_service.Service
{
    public interface ICommandeService
    {
        List<Commande> GetAll();

        Commande GetById(int id);

        Commande Create(Commande entity);

        void Delete(int id);

        void Update(int id, Commande entity);
    }
}
