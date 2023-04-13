using micro_service.EventBus;
using micro_service.Helpers;
using micro_service.Models;
using micro_service.Models.DTO;
using micro_service.Repository;
using micro_service.Service;
using micro_service.Service.Exceptions;
using Microsoft.Extensions.Options;
using Moq;

namespace test_micro_service.TestService
{
    [TestClass]
    public class FactureServiceTest
    {
        private IFactureService factureService;
        private IFactureRepository mockFactureRepository;
        private IRabbitMQPublisher mockRabbitMQPublisher;
       private Facture facture;


        
        public FactureServiceTest()
        {

            Patient patient1 = new()
            {
                prenom = "Alice",
                nom = "Bob",
                email = "email.alice@emil.com",
                numSecu = "87451254621256231",
                numTel = "4521621562",
                dateNaissance = "04/06/1995",
                genre = "Féminun",
                idMedecinTraitant = 4,
                antecedents = "sdfgbn"
            };

            Dictionary<string, int> keyValuePairs = new Dictionary<string, int>();

            keyValuePairs.Add("paracipe", 4);
            keyValuePairs.Add("lesopaine", 2);
            keyValuePairs.Add("strecile", 10);

            Facture facture = new() { Id = "azert", DateFature = DateTime.Parse("15/02/2022"), type = "dentaire", listeProduits = keyValuePairs, patient = patient1 };


            Patient patient2 = new()
            {
                prenom = "Bob",
                nom = "Alice",
                email = "email.bob@emil.com",
                numSecu = "87451254621256231",
                numTel = "4521621562",
                dateNaissance = "04/09/1999",
                genre = "Masculin",
                idMedecinTraitant = 4,
                antecedents = "sdfgbn"
            };

            Dictionary<string, int> keyValuePairs2 = new Dictionary<string, int>();

            keyValuePairs2.Add("paracipe", 4);
            keyValuePairs2.Add("lesopaine", 2);
            keyValuePairs2.Add("strecile", 10);

            Facture facture1 = new() { Id= "azerty", DateFature = DateTime.Now, type = "Optiques", listeProduits = keyValuePairs2, patient = patient2 };

            List<Facture> factures = new List<Facture>{
                facture, facture1
                };

            Patient patient3 = new()
            {
                prenom = "charlie",
                nom = "Dupond",
                email = "email.charlie@emil.com",
                numSecu = "87451254621256231",
                numTel = "4521621562",
                dateNaissance = "10/08/2000",
                genre = "Féminum",
                idMedecinTraitant = 4,
                antecedents = "sdfgbn"
            };

            Dictionary<string, int> keyValuePairs3 = new Dictionary<string, int>();

            keyValuePairs3.Add("paracipe", 4);
            keyValuePairs3.Add("lesopaine", 2);
            keyValuePairs3.Add("strecile", 10);

            this.facture = new() { Id = "qsdfghj", DateFature = DateTime.Now, type = "Optiques", listeProduits = keyValuePairs3, patient = patient3 };
            this.mockFactureRepository = Mock.Of<IFactureRepository>();
            this.mockRabbitMQPublisher = Mock.Of<IRabbitMQPublisher>();
            IOptions<HyperLinKHelpers> mockHyperLink = Mock.Of<IOptions<HyperLinKHelpers>>();
            Mock<PDFHelpers> pDFHelpers = new Mock<PDFHelpers>(mockHyperLink);
            Mock.Get(mockFactureRepository).Setup(m => m.GetAll()).Returns(factures);
            Mock.Get(mockFactureRepository).Setup(m => m.Create(this.facture)).Returns(this.facture);
            Mock.Get(mockFactureRepository).Setup(m => m.GetById("qsdfghj")).Returns(this.facture);
            Mock.Get(mockFactureRepository).Setup(m => m.GetById("qsd")).Returns(value: null);
            this.factureService = new FactureService(mockFactureRepository, mockRabbitMQPublisher, pDFHelpers.Object);
        }

        [TestMethod]       
        public void CreateFeactureOK()
        {
            Facture facture = this.factureService.Create(this.facture);

            Assert.IsNotNull(facture);
        }
        


        [TestMethod]
        public void GetAllFactureTestOk()
        {
            List<Facture> factures = this.factureService.GetAll();
            Assert.AreEqual(2, factures.Count);
        }

        [TestMethod] 
        public void GetFacturePatientNotNullTest()
        {
            Facture? facture = this.factureService.GetFacturePatient("azert", "email.alice@emil.com");

            Assert.IsNotNull(facture);

        }

        [TestMethod]
        public void GetFacturePatientNullTest()
        {
            Facture? facture = this.factureService.GetFacturePatient("azerty", "email.alice@emil.com");

            Assert.IsNull(facture);

        }


        [TestMethod]
        [ExpectedException(typeof(FactureNotFoundException))]
        public void SendEmailKo()
        {
            this.factureService.SendFactureEmailToPatient("azerty", "Bob","/chemain");

        }

        [TestMethod]
        public void GetByIdTestOk()
        {
            Facture facture = this.factureService.GetById("qsdfghj");
            Assert.IsNotNull(facture);

        }

        [TestMethod]
        [ExpectedException(typeof(FactureNotFoundException))]
        public void GetByIdKOTest()
        {
            Facture facture = this.factureService.GetById("qsd");

        }


        [TestMethod]
        public void GetChiffreAffaireModelTestOK()
        {
            List<ChiffreAffaireAnnuelleModel> chiffreAffaireAnnuelleModels = this.factureService.GetChiffreAffaireModel();

            Assert.AreEqual(2,chiffreAffaireAnnuelleModels.Count);
        }

        [TestMethod]
        [ExpectedException(typeof(FactureNotFoundException))]
        public void GetChiffreAffaireModelTestKO()
        {
            IFactureRepository mockBillRepository = Mock.Of<IFactureRepository>();
            Mock.Get(mockBillRepository).Setup(f => f.GetAll()).Returns(new List<Facture>());
            IOptions<HyperLinKHelpers> link = Mock.Of<IOptions<HyperLinKHelpers>>();
            Mock<PDFHelpers> pdf = new Mock<PDFHelpers>(link);
            IFactureService billService = new FactureService(mockBillRepository,mockRabbitMQPublisher,pdf.Object);
            List<ChiffreAffaireAnnuelleModel> chiffreAffaireAnnuelleModels = billService.GetChiffreAffaireModel();
        }

        [TestMethod]
        public void GetAllChiffireAffaireByMonthOfYearTestOK()
        {

            ChiffreAffaireDetailsModel chiffreAffaire = this.factureService.GetAllChiffireAffaireByMonthOfYear(2022);

            Assert.IsNotNull(chiffreAffaire);
        }

        [TestMethod]
        [ExpectedException(typeof(FactureNotFoundException))]
        public void GetAllChiffireAffaireByMonthOfYearTestKO1()
        {
            IFactureRepository mockBillRepository = Mock.Of<IFactureRepository>();
            Mock.Get(mockBillRepository).Setup(f => f.GetAll()).Returns(new List<Facture>());
            IOptions<HyperLinKHelpers> link = Mock.Of<IOptions<HyperLinKHelpers>>();
            Mock<PDFHelpers> pdf = new Mock<PDFHelpers>(link);
            IFactureService billService = new FactureService(mockBillRepository, mockRabbitMQPublisher, pdf.Object);
            ChiffreAffaireDetailsModel chiffreAffaire = billService.GetAllChiffireAffaireByMonthOfYear(2022);
        }


        [TestMethod]
        [ExpectedException(typeof(FactureNotFoundException))]
        public void GetAllChiffireAffaireByMonthOfYearTestKO2()
        {

            ChiffreAffaireDetailsModel chiffreAffaire = this.factureService.GetAllChiffireAffaireByMonthOfYear(2020);

        }

        [TestMethod]
        public void GetAllFacturePatientOK()
        {
            List<Facture> factures = this.factureService.GetAllFacturePatient("email.alice@emil.com");

            Assert.AreEqual(1, factures.Count);
        }


        [TestMethod]
        public void GetAllFacturePatientKO()
        {
            List<Facture> factures = this.factureService.GetAllFacturePatient("bob.alice@emil.com");

            Assert.AreEqual(0, factures.Count);
        }


        [TestMethod]
        [ExpectedException(typeof(FactureNotFoundException))]
        public  void ConfirmationFactureGenereTestKo()
        {
            this.factureService.ConfirmationFactureGenere(null, "secrataire", "/chemain");


        }
    }
}
