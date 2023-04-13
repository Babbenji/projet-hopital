using micro_service.Models.DTO;
using micro_service.Service;
using Moq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace test_micro_service.TestService
{
    [TestClass]
    public class BilanServiceTest
    {
        private ICommandeService mockCommandeService;
        private IFactureService mockFactureService;
        private IBilanService bilanService;
        public BilanServiceTest() 
        {
            this.mockCommandeService = Mock.Of<ICommandeService>();
            this.mockFactureService = Mock.Of<IFactureService>();

            List<ChiffreAffaireAnnuelleModel> chiffreAffaires = new List<ChiffreAffaireAnnuelleModel>()
            {
                new ChiffreAffaireAnnuelleModel()
                {
                    anne = 2023,
                    chiffreAffireAnnnuel = 100.0
                }
            };

            List<ChargeAnnueModel> charges = new List<ChargeAnnueModel>()
            {
                new ChargeAnnueModel()
                {
                    anne = 2023,
                    charge = 50.0
                }
            };

            Mock.Get(this.mockCommandeService).Setup(m => m.GetAllChargeCommandeByYear()).Returns(charges);
            Mock.Get(this.mockFactureService).Setup(m => m.GetChiffreAffaireModel()).Returns(chiffreAffaires);
            this.bilanService = new BilanService(mockFactureService,mockCommandeService);
        }

        [TestMethod] 
        public void GetBilanAnnuelModels()
        {
            List<BilanAnnuelModel>  bilanAnnuels = this.bilanService.GetBilanAnnuelModels();
            Assert.AreEqual(1, bilanAnnuels.Count);
            Assert.AreEqual(50.0, bilanAnnuels[0].differance);
        }
    }
}
