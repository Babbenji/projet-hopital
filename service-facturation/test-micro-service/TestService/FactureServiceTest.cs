using micro_service.EventBus;
using micro_service.Models;
using micro_service.Repository;
using micro_service.Service;
using micro_service.Service.Exceptions;
using Moq;
using Org.BouncyCastle.Bcpg.Sig;

namespace test_micro_service.TestService
{
    [TestClass]
    public class FactureServiceTest
    {
        IFactureService factureService;
        IFactureRepository mockFactureRepository;
        IRabbitMQPublisher mockRabbitMQPublisher;
        Facture facture;


        [TestInitialize] 
        public void Setup()
        {
            List<Facture> factures = new List<Facture>{
                new() { Id ="azerty", DateFature = DateTime.Now, NomPatient="Alice", Produits = new List<Produit>(){ new() { Nom = "doliprame", Marque = "paracipal", Quantite = 8 } } },
                new() { Id ="azert", DateFature = DateTime.Now, NomPatient="Bob", Produits = new List<Produit>(){ new() { Nom = "doliprame", Marque = "paracipal", Quantite = 8 } } },
                };
            this.facture = new() { Id = "qsdfghj", DateFature = DateTime.Now, Produits = null }; 
            this.mockFactureRepository = Mock.Of<IFactureRepository>();
            this.mockRabbitMQPublisher = Mock.Of<IRabbitMQPublisher>();
            Mock.Get(mockFactureRepository).Setup(m => m.GetAll()).Returns(factures);
            Mock.Get(mockFactureRepository).Setup(m => m.Create(facture)).Returns(facture);
            Mock.Get(mockFactureRepository).Setup(m => m.GetById("qsdfghj")).Returns(facture);
            Mock.Get(mockFactureRepository).Setup(m => m.GetById("qsd")).Returns(value: null);
            this.factureService = new FactureService(mockFactureRepository, mockRabbitMQPublisher);
        }
       

        [TestMethod]
        public void GetAllFactureTest()
        {
            List<Facture> factures = this.factureService.GetAll();
            Assert.AreEqual(2, factures.Count);
        }

        [TestMethod] 
        public void GetFacturePatientNotNullTest()
        {
            Facture?  facture  = this.factureService.GetFacturePatient("azert", "Bob");

            Assert.IsNotNull(facture);

        }

        [TestMethod]
        public void GetFacturePatientNullTest()
        {
            Facture? facture = this.factureService.GetFacturePatient("azerty", "Bob");

            Assert.IsNull(facture);

        }


        [TestMethod]
        [ExpectedException(typeof(FactureNotFoundException))]
        public void SendEmailKo()
        {
            this.factureService.SendFactureEmail("azerty", "Bob");

        }

        [TestMethod]
        public void GetByIdTest()
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
    }
}
