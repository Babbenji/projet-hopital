using micro_service.Models.DTO;

namespace micro_service.Service
{
    public interface IBilanService
    {
        List<BilanAnnuelModel> GetBilanAnnuelModels();
    }


    public class BilanService : IBilanService 
    {
        private readonly IFactureService factureService;
        private readonly ICommandeService commandeService;

        public BilanService(IFactureService factureService, ICommandeService commandeService) 
        {
            this.factureService = factureService;
            this.commandeService = commandeService;
        }

        public List<BilanAnnuelModel> GetBilanAnnuelModels()
        {
            List<BilanAnnuelModel> bilanAnnuels = new();
            List<ChiffreAffaireAnnuelleModel> chiffreAffaires = this.factureService.GetChiffreAffaireModel();
            List<ChargeAnnueModel> charges = this.commandeService.GetAllChargeCommandeByYear();

            foreach(ChiffreAffaireAnnuelleModel ca in chiffreAffaires)
            {
                foreach (ChargeAnnueModel charge in charges)
                {
                    if (ca != null && charge != null)
                    {
                        if(ca.anne == charge.anne)
                            bilanAnnuels.Add(new() { anne = charge.anne, differance = ca.chiffreAffireAnnnuel - charge.charge });
                    }
                }
            }
            return bilanAnnuels;
        }
    }   
}
