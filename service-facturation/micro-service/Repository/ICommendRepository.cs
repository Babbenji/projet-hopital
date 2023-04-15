using micro_service.Models;

namespace micro_service.Repository
{
    public interface ICommendRepository
    {
        List<Commande> GetAll();

        Commande GetById(int id);

        Commande Create(Commande entity);

        void Delete(int id);

        void Update(int id, Commande entity);
    }
}
