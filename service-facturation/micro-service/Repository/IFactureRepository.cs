using micro_service.Models;

namespace micro_service.Repository
{
    public interface IFactureRepository
    {
        List<Facture> GetAll();

        Facture GetById(string id);

        Facture Create(Facture entity);

        void Delete(string id);

        void Update(string id, Facture entity);

    }
}
