using micro_service.Models;

namespace micro_service.Service
{
    public interface IFactureService
    {
        List<Facture> GetAll();

        List<Facture> GetAllFacturePatient(string username);

        Facture? GetFacturePatient(string idfacture, string username);

        void SendFactureEmail(string idfacture, string username);

        Facture GetById(string id);

        Facture Create(Facture entity);

        void Delete(string id);

        void Update(string id, Facture entity);
    }
}
