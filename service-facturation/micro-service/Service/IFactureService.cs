using micro_service.Models;
using micro_service.Models.DTO;

namespace micro_service.Service
{
    public interface IFactureService
    {
        List<Facture> GetAll();

        List<Facture> GetAllFacturePatient(string username);

        Facture? GetFacturePatient(string idfacture, string username);

        void SendFactureEmailToPatient(string idfacture, string username, string chemain);

        void ConfirmationFactureGenere(Facture facture, string secretionLogin, string chemain);

        List<ChiffreAffaireAnnuelleModel> GetChiffreAffaireModel();

        ChiffreAffaireDetailsModel GetAllChiffireAffaireByMonthOfYear(int year);

        Facture GetById(string id);

        Facture Create(Facture entity);

        void Delete(string id);

        void Update(string id, Facture entity);
    }
}
