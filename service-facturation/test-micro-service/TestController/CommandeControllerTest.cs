using micro_service.Controllers;
using micro_service.Models;
using micro_service.Models.DTO;
using micro_service.Service;
using micro_service.Service.Exceptions;
using Microsoft.AspNetCore.Mvc;
using Moq;

namespace test_micro_service.TestController
{
    [TestClass]
    public class CommandeControllerTest
    {
        [TestMethod]
        public void GetCommandeByIdTestOK()
        {
            // Arrange
            ICommandeService mockCommendService = Mock.Of<ICommandeService>();
            Dictionary<string, int> produitsCmd = new Dictionary<string, int>();
            produitsCmd.Add("paracipe", 5);
            produitsCmd.Add("Calpal", 8);
            produitsCmd.Add("lesopaine", 15);
            produitsCmd.Add("strpcile", 20);

            Commande commande = new() { idCommande = 3, dateCommande = DateTime.Now, prixCommande = 40.5, produitsCommande = produitsCmd };
            Mock.Get(mockCommendService).Setup(m => m.GetById(3)).Returns(commande);
            CommandeController controller = new CommandeController(mockCommendService);

            // Act
            IActionResult actionResult = controller.GetCommandeById(3);

            // Assert
            Assert.IsInstanceOfType(actionResult, typeof(OkObjectResult));
            OkObjectResult result = (OkObjectResult)actionResult;
            Assert.IsNotNull(result.Value);
        }


        [TestMethod]
        public void GetCommandeByIdTestKO()
        {
            // Arrange
            ICommandeService mockCommendService = Mock.Of<ICommandeService>();
           
            Mock.Get(mockCommendService).Setup(m => m.GetById(3)).Throws<CommandeNotFoundException>();
            CommandeController controller = new CommandeController(mockCommendService);

            // Act
            IActionResult actionResult = controller.GetCommandeById(3);

            // Assert
            Assert.IsInstanceOfType(actionResult, typeof(BadRequestObjectResult));
            BadRequestObjectResult result = (BadRequestObjectResult)actionResult;
            Assert.IsNotNull(result.Value);
        }

        [TestMethod]
        public void GetChargeTotalAnnuelTestOK()
        {
            // Arrange
            ICommandeService mockCommendService = Mock.Of<ICommandeService>();

            ChargeAnnuelDetailModel chargeAnnuel = new() { anne = 2020, chargeAnuelle = 12.5, mensuel = new List<ChargeMensuelModel>() { new() { mois = 4, charge = 12.6 } } };

            Mock.Get(mockCommendService).Setup(m => m.GetAllChargeCommandeByMonthOfYear(2020)).Returns(chargeAnnuel);
            CommandeController controller = new CommandeController(mockCommendService);

            // Act
            IActionResult actionResult = controller.GetChargeTotalAnnuel(2020);

            // Assert
            Assert.IsInstanceOfType(actionResult, typeof(OkObjectResult));
            OkObjectResult result = (OkObjectResult)actionResult;
            Assert.IsNotNull(result.Value);
        }

        [TestMethod]
        public void GetChargeTotalAnnuelTestKO()
        {
            // Arrange
            ICommandeService mockCommendService = Mock.Of<ICommandeService>();

            Mock.Get(mockCommendService).Setup(m => m.GetAllChargeCommandeByMonthOfYear(2020)).Throws<CommandeNotFoundException>();
            CommandeController controller = new CommandeController(mockCommendService);

            // Act
            IActionResult actionResult = controller.GetChargeTotalAnnuel(2020);

            // Assert
            Assert.IsInstanceOfType(actionResult, typeof(BadRequestObjectResult));
            BadRequestObjectResult result = (BadRequestObjectResult)actionResult;
            Assert.IsNotNull(result.Value);
        }


        [TestMethod]
        public void GetAllChargeAnnuelOK()
        {
            // Arrange
            ICommandeService mockCommendService = Mock.Of<ICommandeService>();

            Mock.Get(mockCommendService).Setup(m => m.GetAllChargeCommandeByYear()).Returns(new List<ChargeAnnueModel>());
            CommandeController controller = new CommandeController(mockCommendService);

            // Act
            IActionResult actionResult = controller.GetAllChargeAnnuel();

            // Assert
            Assert.IsInstanceOfType(actionResult, typeof(OkObjectResult));
            OkObjectResult result = (OkObjectResult)actionResult;
            Assert.IsNotNull(result.Value);
        }


        [TestMethod]
        public void GetAllChargeAnnuelKO()
        {
            // Arrange
            ICommandeService mockCommendService = Mock.Of<ICommandeService>();

            Mock.Get(mockCommendService).Setup(m => m.GetAllChargeCommandeByYear()).Throws<CommandeNotFoundException>();
            CommandeController controller = new CommandeController(mockCommendService);

            // Act
            IActionResult actionResult = controller.GetAllChargeAnnuel();

            // Assert
            Assert.IsInstanceOfType(actionResult, typeof(NoContentResult));
            NoContentResult result = (NoContentResult)actionResult;
            Assert.IsNotNull(result);
        }

        [TestMethod]
        public void GetCommandesOK()
        {
            // Arrange
            ICommandeService mockCommendService = Mock.Of<ICommandeService>();



            Mock.Get(mockCommendService).Setup(m => m.GetAll()).Returns(new List<Commande>());
            CommandeController controller = new CommandeController(mockCommendService);

            // Act
            IActionResult actionResult = controller.GetCommandes();

            // Assert
            Assert.IsInstanceOfType(actionResult, typeof(OkObjectResult));
            OkObjectResult result = (OkObjectResult)actionResult;
            Assert.IsNotNull(result);
        }
    }
}
