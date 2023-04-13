using micro_service.Models;
using micro_service.Models.DTO;
using micro_service.Repository;
using micro_service.Service;
using micro_service.Service.Exceptions;
using Moq;

namespace test_micro_service.TestService
{
    [TestClass]
    public class CommandeServiceTest
    {

        private ICommendRepository mockCommandeRepository;
        private ICommandeService commandeService;
        private Commande commande;

        public CommandeServiceTest()
        {
            Dictionary<string, int> produitsCmd = new Dictionary<string, int>();
            produitsCmd.Add("paracipe", 5);
            produitsCmd.Add("Calpal", 8);
            produitsCmd.Add("lesopaine", 15);
            produitsCmd.Add("strpcile", 20);

            this.commande = new() { idCommande = 3, dateCommande = DateTime.Now, prixCommande = 40.5, produitsCommande = produitsCmd };


            Dictionary<string, int> produitsCmd1 = new Dictionary<string, int>();
            produitsCmd1.Add("paracipe", 5);
            produitsCmd1.Add("Calpal", 8);
            produitsCmd1.Add("lesopaine", 15);
            produitsCmd1.Add("strpcile", 20);
            Commande commande1 = new() { idCommande = 1, dateCommande = DateTime.Parse("15/02/2021"), prixCommande = 45.2, produitsCommande = produitsCmd1 };
            Dictionary<string, int> produitsCmd2 = new Dictionary<string, int>();
            produitsCmd2.Add("paracipe", 5);
            produitsCmd2.Add("Calpal", 8);
            produitsCmd2.Add("lesopaine", 15);
            produitsCmd2.Add("strpcile", 20);
            produitsCmd2.Add("ascotril", 5);
            produitsCmd2.Add("cobafast", 5);
            Commande commande2 = new() { idCommande = 2, dateCommande = DateTime.Parse("15/02/2022"), prixCommande = 55.6, produitsCommande = produitsCmd2 };

            List<Commande> commandeList = new() { commande1, commande2 };

            this.mockCommandeRepository = Mock.Of<ICommendRepository>();

            Mock.Get(this.mockCommandeRepository).Setup(c => c.Create(this.commande)).Returns(this.commande);
            Mock.Get(this.mockCommandeRepository).Setup(c => c.GetAll()).Returns(commandeList);

            Mock.Get(this.mockCommandeRepository).Setup(c => c.GetById(3)).Returns(this.commande);
            Mock.Get(this.mockCommandeRepository).Setup(c => c.GetById(4)).Returns(value:null);

            this.commandeService = new CommandeService(this.mockCommandeRepository);
        }

        

        

        [TestMethod]
        public void CreateTestOK()
        {
            Commande commande = this.commandeService.Create(this.commande);
            Assert.IsNotNull(commande);
            Assert.AreEqual(commande.idCommande, this.commande.idCommande);
        }

        [TestMethod]
        public void GetAllTestOK() 
        {
            List<Commande> list = this.commandeService.GetAll();
            Assert.IsNotNull(list);
            Assert.AreEqual(2,list.Count);
        }


        [TestMethod]
        public void GetByIdTestOk()
        {
            Commande commande = this.commandeService.GetById(3);
            Assert.IsNotNull(commande);

        }

        [TestMethod]
        [ExpectedException(typeof(CommandeNotFoundException))]
        public void GetByIdKOTest()
        {
            Commande commande = this.commandeService.GetById(4);

        }

        [TestMethod]
        public void GetAllChargeCommandeByYearTestOk()
        {
            List<ChargeAnnueModel> charges = this.commandeService.GetAllChargeCommandeByYear();

            Assert.IsNotNull(charges);
            Assert.AreEqual(2,charges.Count);

        }

        [TestMethod]
        [ExpectedException(typeof(CommandeNotFoundException))]
        public void GetAllChargeCommandeByYearKO()
        {
            ICommendRepository mockCmdRepository = Mock.Of<ICommendRepository>();
            Mock.Get(mockCmdRepository).Setup(c => c.GetAll()).Returns(new List<Commande>());
            CommandeService cmdService = new CommandeService(mockCmdRepository);
            List<ChargeAnnueModel> charges = cmdService.GetAllChargeCommandeByYear();

        }

        [TestMethod]
        public void GetAllChargeCommandeByMonthOfYearTestOK()
        {
            ChargeAnnuelDetailModel charge = this.commandeService.GetAllChargeCommandeByMonthOfYear(2022);
            Assert.IsNotNull(charge);
        }

        [TestMethod]
        [ExpectedException(typeof(CommandeNotFoundException))]
        public void GetAllChargeCommandeByMonthOfYearTestKO1()
        {
            ChargeAnnuelDetailModel charge = this.commandeService.GetAllChargeCommandeByMonthOfYear(2023);
        }

        [TestMethod]
        [ExpectedException(typeof(CommandeNotFoundException))]
        public void GetAllChargeCommandeByMonthOfYearTestKO2()
        {
            ICommendRepository mockCmdRepository = Mock.Of<ICommendRepository>();
            Mock.Get(mockCmdRepository).Setup(c => c.GetAll()).Returns(new List<Commande>());
            CommandeService cmdService = new CommandeService(mockCmdRepository);
            ChargeAnnuelDetailModel charge = cmdService.GetAllChargeCommandeByMonthOfYear(2022);
        }
    }
}
