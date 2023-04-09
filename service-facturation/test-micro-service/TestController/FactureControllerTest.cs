using micro_service.Controllers;
using micro_service.Models;
using micro_service.Service;
using micro_service.Service.Exceptions;
using Microsoft.AspNetCore.Mvc;
using System.Web.Http.Controllers;
using Microsoft.Extensions.Logging;
using Moq;
using System.Security.Claims;

namespace test_micro_service.TestController
{
    [TestClass]
    public class FactureControllerTest
    {


        [TestMethod]
        public void PostFacture()
        {
            // Arrange
            IFactureService mockFactureService = Mock.Of<IFactureService>();
            ILogger<FactureController> logger = Mock.Of<ILogger<FactureController>>();
            Facture facture = new Facture() { Id="azerty", NomPatient="benti", Produits = new List<Produit>() { new() { Nom = "paracipal", Marque="doliprane", Quantite=4 } } };
            Mock.Get(mockFactureService).Setup(m => m.Create(facture)).Returns(facture);
            FactureController controller = new FactureController(logger, mockFactureService);

            // Act
            IActionResult actionResult = controller.CreationFacture(facture);

            // Assert
            Assert.IsInstanceOfType(actionResult, typeof(CreatedAtActionResult));
            var createResult = (CreatedAtActionResult)actionResult;
            Assert.IsNotNull(createResult.Value);
        }


        [TestMethod]
        public void GetFacureTest()
        {
            // Arrange
            IFactureService mockFactureService = Mock.Of<IFactureService>();
            ILogger<FactureController> logger = Mock.Of<ILogger<FactureController>>();
            Facture facture = new Facture() { Id = "azerty", NomPatient = "benti", Produits = new List<Produit>() { new() { Nom = "paracipal", Marque = "doliprane", Quantite = 4 } } };
            Mock.Get(mockFactureService).Setup(m => m.GetById("azerty")).Returns(facture);
            FactureController controller = new FactureController(logger, mockFactureService);

            // Act
            IActionResult actionResult = controller.GetFacture("azerty");

            // Assert
            Assert.IsInstanceOfType(actionResult, typeof(OkObjectResult));
            OkObjectResult createResult = (OkObjectResult)actionResult;
            Assert.IsNotNull(createResult.Value);
        }

        [TestMethod]
        public void GetFacureTestKo()
        {
            // Arrange
            IFactureService mockFactureService = Mock.Of<IFactureService>();
            ILogger<FactureController> logger = Mock.Of<ILogger<FactureController>>();
            
            //Mock.Get(mockFactureService).Setup(m => m.GetById("azerty")).Throws(new FactureNotFoundException("Facture n'existe pas !!!"));
            Mock.Get(mockFactureService).Setup(m => m.GetById("azerty")).Throws<FactureNotFoundException>();
            FactureController controller = new FactureController(logger, mockFactureService);

            // Act
            IActionResult actionResult = controller.GetFacture("azerty");

            // Assert
            Assert.IsInstanceOfType(actionResult, typeof(NotFoundObjectResult));
            NotFoundObjectResult createResult = (NotFoundObjectResult)actionResult;
            Assert.IsNotNull(createResult.Value);
        }

        [TestMethod]
        public void SendFactureToPatientKo()
        {
            // Arrange
            IFactureService mockFactureService = Mock.Of<IFactureService>();
            ILogger<FactureController> logger = Mock.Of<ILogger<FactureController>>();

            var context = new ControllerContext();
            var principal = new ClaimsPrincipal(new ClaimsIdentity(new[] { new Claim(ClaimTypes.Role, "Admin") }));

            //Mock.Get(mockFactureService).Setup(m => m.GetById("azerty")).Throws(new FactureNotFoundException("Facture n'existe pas !!!"));
            Mock.Get(mockFactureService).Setup(m => m.SendFactureEmail("azerty","azerty")).Throws<FactureNotFoundException>();
            FactureController controller = new FactureController(logger, mockFactureService);
            context.HttpContext.User = principal;
            controller.ControllerContext = context;

            // Act
            IActionResult actionResult = controller.SendFactureToPatient("azerty","azerty");

            // Assert
            Assert.IsInstanceOfType(actionResult, typeof(NotFoundObjectResult));
            NotFoundObjectResult createResult = (NotFoundObjectResult)actionResult;
            Assert.IsNotNull(createResult.Value);
        }


        [TestMethod]
        public void SendFactureToPatientKoAvecRole()
        {
            // Arrange
            IFactureService mockFactureService = Mock.Of<IFactureService>();
            ILogger<FactureController> logger = Mock.Of<ILogger<FactureController>>();

            var context = new ControllerContext();
            var principal = new ClaimsPrincipal(new ClaimsIdentity(new[] { new Claim(ClaimTypes.Role, "Admin") }));

            //Mock.Get(mockFactureService).Setup(m => m.GetById("azerty")).Throws(new FactureNotFoundException("Facture n'existe pas !!!"));
            Mock.Get(mockFactureService).Setup(m => m.SendFactureEmail("azerty", "azerty")).Throws<FactureNotFoundException>();
            FactureController controller = new FactureController(logger, mockFactureService);
            context.HttpContext.User = principal;
            controller.ControllerContext = context;

            // Act
            IActionResult actionResult = controller.SendFactureToPatient("azerty", "azerty");

            // Assert
            Assert.IsInstanceOfType(actionResult, typeof(NotFoundObjectResult));
            NotFoundObjectResult createResult = (NotFoundObjectResult)actionResult;
            Assert.IsNotNull(createResult.Value);
        }
    }
}
