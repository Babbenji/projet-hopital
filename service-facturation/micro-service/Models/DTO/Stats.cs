namespace micro_service.Models.DTO
{
    //Charge
    public class ChargeAnnueModel
    {
        public int anne { get; set; }

        public double charge { get; set; }
    }


    public class ChargeMensuelModel
    {
        public int mois { get; set; }

        public double charge { get; set; }
    }

    public class ChargeAnnuelDetailModel
    {
        public int anne { get; set; }

        public double chargeAnuelle { get; set; }

        public List<ChargeMensuelModel> mensuel { get; set; } = new();
    }




    //Chiffre d'affaire

    public class ChiffreAffaireAnnuelleModel
    {
        public int anne { get; set; }

        public double chiffreAffireAnnnuel { get; set; }
    }

    public class ChiffreAffireMensuelModel
    {
        public int mois { get; set; }

        public double chiffreAffireMensuel { get; set; }
    }


    public class ChiffreAffaireDetailsModel
    {
        public int anne { get; set; }

        public double chiffreAffireAnnnuel { get; set; }

        public List<ChiffreAffireMensuelModel> chiffreAffireMensuelModels { get; set; } = new();
    }

    //Bilan
    public class BilanAnnuelModel
    {
        public int anne { get; set; }

        public double differance { get; set; }
    }
}
