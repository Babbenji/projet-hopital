using micro_service.Controllers;
using micro_service.Models.DTO;
using micro_service.Service;
using Microsoft.AspNetCore.Mvc;
using Moq;

namespace test_micro_service.TestController
{
    [TestClass]
    public class BilanControllerTest
    {
        [TestMethod]
        public void GetRecetteOK()
        {
            // Arrange
            List<BilanAnnuelModel> bilanAnnuels = new List<BilanAnnuelModel>()
            {
                new BilanAnnuelModel()
                {
                    anne = 2022,
                    differance = 50.0
                },

                new BilanAnnuelModel()
                {
                    anne = 2023,
                    differance = 150.0
                }
            };
            IBilanService mockService = Mock.Of<IBilanService>();

            Mock.Get(mockService).Setup(b => b.GetBilanAnnuelModels()).Returns(bilanAnnuels);

            BilanController controller = new BilanController(mockService);

            // Act
            IActionResult actionResult = controller.GetRecette();

            // Assert
            Assert.IsInstanceOfType(actionResult, typeof(OkObjectResult));
            OkObjectResult result = (OkObjectResult)actionResult;
            Assert.IsNotNull(result.Value);

        }

        [TestMethod]
        public void GetRecetteKO()
        {
            // Arrange
            List<BilanAnnuelModel> bilanAnnuels = new();

            IBilanService mockService = Mock.Of<IBilanService>();

            Mock.Get(mockService).Setup(b => b.GetBilanAnnuelModels()).Returns(bilanAnnuels);

            BilanController controller = new BilanController(mockService);

            // Act
            IActionResult actionResult = controller.GetRecette();

            // Assert
            Assert.IsInstanceOfType(actionResult, typeof(NoContentResult));
            NoContentResult result = (NoContentResult)actionResult;
            Assert.IsNotNull(result);

        }
    }
}
