using micro_service.Models;
using micro_service.Models.DTO;
using micro_service.Repository;
using micro_service.Service.Exceptions;

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
            return this.commendRepository.GetById(id) ?? throw new CommandeNotFoundException("la commande " + id + " n'existe pas dans la base de données");
        }

        public void Update(int id, Commande entity)
        {
            this.commendRepository.Update(id, entity);
        }

        public List<ChargeAnnueModel> GetAllChargeCommandeByYear()
        {
            if (this.GetAll().Count > 0)
                return this.GetAll().GroupBy(c => c.dateCommande.Year).Select(g => new ChargeAnnueModel { anne = g.Key, charge = g.ToList().Sum(c => c.prixCommande) }).ToList();
            else
                throw new CommandeNotFoundException("Aucune commande dans la base de données");
        }

        public ChargeAnnuelDetailModel GetAllChargeCommandeByMonthOfYear(int year)
        {

            if (this.GetAll().Count > 0)
            {
                List<Commande> cmdsYear = (from c in this.GetAll()
                                           where c.dateCommande.Year == year
                                           select c).ToList();
                if(cmdsYear.Count > 0)
                {
                    return new()
                    {
                        anne = year,
                        chargeAnuelle = cmdsYear.Sum(c => c.prixCommande),
                        mensuel = cmdsYear.GroupBy(c => c.dateCommande.Month).Select(g => new ChargeMensuelModel { mois = g.Key, charge = g.ToList().Sum(c => c.prixCommande) }).ToList()
                    };
                }
                else
                {
                    throw new CommandeNotFoundException("Aucune commande dans la base de données");
                }

            }
            else
            {
                throw new CommandeNotFoundException("Aucune commande dans la base de données");
            } 
        }
    }
}
