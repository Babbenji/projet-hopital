using micro_service.Controllers;
using micro_service.Models;
using micro_service.Service;
using micro_service.Service.Exceptions;
using Microsoft.AspNetCore.Mvc;
using Moq;
using Microsoft.AspNetCore.Hosting;
using micro_service.Models.DTO;

namespace test_micro_service.TestController
{
    [TestClass]
    public class FactureControllerTest
    {

        [TestMethod]
        public void GetFacturesTestOk()
        {
            // Arrange
            IFactureService mockFactureService = Mock.Of<IFactureService>();
            IWebHostEnvironment mockEnv = Mock.Of<IWebHostEnvironment>();
            Patient patient = new()
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

            Dictionary<string, int> keyValuePairs = new Dictionary<string, int>();

            keyValuePairs.Add("paracipe", 4);
            keyValuePairs.Add("lesopaine", 2);
            keyValuePairs.Add("strecile", 10);
            //HttpControllerContext context = new HttpControllerContext();
            

            Facture facture = new() { Id = "azerty", DateFature = DateTime.Now, type = "Optiques", listeProduits = keyValuePairs, patient = patient };
            Facture facture1 = new() { Id = "azert", DateFature = DateTime.Now, type = "Optiques", listeProduits = keyValuePairs, patient = patient };
            List<Facture> factures = new List<Facture>() { facture, facture1 };
            Mock.Get(mockFactureService).Setup(m => m.GetAll()).Returns(factures);
            FactureController controller = new FactureController(mockFactureService, mockEnv);
            
            

            // Act
            IActionResult actionResult = controller.GetFactures();

            // Assert
            Assert.IsInstanceOfType(actionResult, typeof(OkObjectResult));
            OkObjectResult createResult = (OkObjectResult)actionResult;
            Assert.IsNotNull(createResult.Value);
        }

        [TestMethod]
        public void GetFactureTest()
        {
            // Arrange
            IFactureService mockFactureService = Mock.Of<IFactureService>();
            IWebHostEnvironment mockEnv = Mock.Of<IWebHostEnvironment>();
            Patient patient = new()
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

            Dictionary<string, int> keyValuePairs = new Dictionary<string, int>();

            keyValuePairs.Add("paracipe", 4);
            keyValuePairs.Add("lesopaine", 2);
            keyValuePairs.Add("strecile", 10);

            Facture facture = new() { Id = "azerty", DateFature = DateTime.Now, type = "Optiques", listeProduits = keyValuePairs, patient = patient };
            Mock.Get(mockFactureService).Setup(m => m.GetById("azerty")).Returns(facture);
            FactureController controller = new FactureController(mockFactureService, mockEnv);

            // Act
            IActionResult actionResult = controller.GetFacture("azerty");

            // Assert
            Assert.IsInstanceOfType(actionResult, typeof(OkObjectResult));
            OkObjectResult createResult = (OkObjectResult)actionResult;
            Assert.IsNotNull(createResult.Value);
        }

        [TestMethod]
        public void GetFactureTestKo()
        {
            //Arrange
            IFactureService mockFactureService = Mock.Of<IFactureService>();
            IWebHostEnvironment mockEnv = Mock.Of<IWebHostEnvironment>();

            
            Mock.Get(mockFactureService).Setup(m => m.GetById("azerty")).Throws<FactureNotFoundException>();
            FactureController controller = new FactureController(mockFactureService, mockEnv);

            // Act
            IActionResult actionResult = controller.GetFacture("azerty");

            // Assert
            Assert.IsInstanceOfType(actionResult, typeof(BadRequestObjectResult));
            BadRequestObjectResult result = (BadRequestObjectResult)actionResult;
            Assert.IsNotNull(result.Value);
        }

        [TestMethod]
        public void SendFactureToPatientKo()
        {
            //Arrange
            IFactureService mockFactureService = Mock.Of<IFactureService>();
            IWebHostEnvironment mockEnv = Mock.Of<IWebHostEnvironment>();
            Mock.Get(mockEnv).SetupGet(mockEnv => mockEnv.ContentRootPath).Returns("/chemain");
            string filePath = Path.Combine("/chemain", "pdfFile-bill");
            

            
            Mock.Get(mockFactureService).Setup(m => m.SendFactureEmailToPatient("azerty", "azerty", filePath)).Throws<FactureNotFoundException>();
            FactureController controller = new FactureController(mockFactureService, mockEnv);
            

            // Act
            IActionResult actionResult = controller.SendFactureToPatient("azerty", "azerty");

            // Assert
            Assert.IsInstanceOfType(actionResult, typeof(BadRequestObjectResult));
            BadRequestObjectResult result = (BadRequestObjectResult)actionResult;
            Assert.IsNotNull(result.Value);
        }

        [TestMethod]
        public void GetChiffreAffaireModelOK()
        {
            IFactureService mockFactureService = Mock.Of<IFactureService>();
            IWebHostEnvironment mockEnv = Mock.Of<IWebHostEnvironment>();


            
            Mock.Get(mockFactureService).Setup(m => m.GetChiffreAffaireModel()).Returns(new List<ChiffreAffaireAnnuelleModel>());
            FactureController controller = new FactureController(mockFactureService, mockEnv);

            // Act
            IActionResult actionResult = controller.GetChiffreAffaireModel();

            // Assert
            Assert.IsInstanceOfType(actionResult, typeof(OkObjectResult));
            OkObjectResult result = (OkObjectResult)actionResult;
            Assert.IsNotNull(result);
        }

        [TestMethod]
        public void GetChiffreAffaireModelKO()
        {
            IFactureService mockFactureService = Mock.Of<IFactureService>();
            IWebHostEnvironment mockEnv = Mock.Of<IWebHostEnvironment>();

            
            Mock.Get(mockFactureService).Setup(m => m.GetChiffreAffaireModel()).Throws<FactureNotFoundException>();
            FactureController controller = new FactureController(mockFactureService, mockEnv);

            // Act
            IActionResult actionResult = controller.GetChiffreAffaireModel();

            // Assert
            Assert.IsInstanceOfType(actionResult, typeof(NoContentResult));
            NoContentResult result = (NoContentResult)actionResult;
            Assert.IsNotNull(result);
        }


        [TestMethod]
        public void GetAllChiffireAffaireByMonthOfYearOK()
        {
            IFactureService mockFactureService = Mock.Of<IFactureService>();
            IWebHostEnvironment mockEnv = Mock.Of<IWebHostEnvironment>();

            
            Mock.Get(mockFactureService).Setup(m => m.GetAllChiffireAffaireByMonthOfYear(2022)).Returns(new ChiffreAffaireDetailsModel());
            FactureController controller = new FactureController(mockFactureService, mockEnv);

            // Act
            IActionResult actionResult = controller.GetAllChiffireAffaireByMonthOfYear(2022);

            // Assert
            Assert.IsInstanceOfType(actionResult, typeof(OkObjectResult));
            OkObjectResult result = (OkObjectResult)actionResult;
            Assert.IsNotNull(result);
        }


        [TestMethod]
        public void GetAllChiffireAffaireByMonthOfYearKO()
        {
            IFactureService mockFactureService = Mock.Of<IFactureService>();
            IWebHostEnvironment mockEnv = Mock.Of<IWebHostEnvironment>();

            
            Mock.Get(mockFactureService).Setup(m => m.GetAllChiffireAffaireByMonthOfYear(2022)).Throws<FactureNotFoundException>();
            FactureController controller = new FactureController(mockFactureService, mockEnv);

            // Act
            IActionResult actionResult = controller.GetAllChiffireAffaireByMonthOfYear(2022);

            // Assert
            Assert.IsInstanceOfType(actionResult, typeof(BadRequestObjectResult));
            BadRequestObjectResult result = (BadRequestObjectResult)actionResult;
            Assert.IsNotNull(result);
        }

        [TestMethod]
        public void GetFacturesPatientOk()
        {
            IFactureService mockFactureService = Mock.Of<IFactureService>();
            IWebHostEnvironment mockEnv = Mock.Of<IWebHostEnvironment>();

            Patient patient1 = new()
            {
                prenom = "Alice",
                nom = "Bob",
                email = "email.bob@emil.com",
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

            Facture facture1 = new() { Id = "azerty", DateFature = DateTime.Now, type = "Optiques", listeProduits = keyValuePairs2, patient = patient2 };

            List<Facture> factures = new List<Facture>{
                facture, facture1
                };

            
            Mock.Get(mockFactureService).Setup(m => m.GetAllFacturePatient("email.bob@emil.com")).Returns(factures);
            FactureController controller = new FactureController(mockFactureService, mockEnv);

            // Act
            IActionResult actionResult = controller.GetFacturesPatient("email.bob@emil.com");

            // Assert
            Assert.IsInstanceOfType(actionResult, typeof(OkObjectResult));
            OkObjectResult result = (OkObjectResult)actionResult;
            Assert.IsNotNull(result);
        }


        [TestMethod]
        public void GetFacturesPatientKO()
        {
            IFactureService mockFactureService = Mock.Of<IFactureService>();
            IWebHostEnvironment mockEnv = Mock.Of<IWebHostEnvironment>();

            
            Mock.Get(mockFactureService).Setup(m => m.GetAllFacturePatient("emai.exemple@com")).Returns(new List<Facture>()); ;
            FactureController controller = new FactureController(mockFactureService, mockEnv);

            // Act
            IActionResult actionResult = controller.GetFacturesPatient("emai.exemple@com");

            // Assert
            Assert.IsInstanceOfType(actionResult, typeof(BadRequestObjectResult));
            BadRequestObjectResult result = (BadRequestObjectResult)actionResult;
            Assert.IsNotNull(result);
        }

        
    }
}
