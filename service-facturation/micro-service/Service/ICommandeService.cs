using micro_service.Models;
using micro_service.Models.DTO;

namespace micro_service.Service
{
    public interface ICommandeService
    {
        List<Commande> GetAll();

        Commande GetById(int id);

        Commande Create(Commande entity);

        void Delete(int id);

        void Update(int id, Commande entity);

        List<ChargeAnnueModel> GetAllChargeCommandeByYear();

        ChargeAnnuelDetailModel GetAllChargeCommandeByMonthOfYear(int year);
    }
}
