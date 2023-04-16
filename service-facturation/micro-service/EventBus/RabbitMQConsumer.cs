using micro_service.Models;
using micro_service.Models.DTO;
using micro_service.Service;
using Newtonsoft.Json;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using System.Text;

namespace micro_service.EventBus
{
    public class RabbitMQConsumer : IRabbitMQConsumer
    {
        private readonly ICommandeService commandeService;
        private readonly IFactureService factureService;
        private readonly ILogger<RabbitMQConsumer> logger;
        private readonly RabbitMQProvider rabbitMQProvider;
        private readonly IModel channelFacture;
        private readonly IModel channelFactureCmd;
        private readonly IWebHostEnvironment env;
        private readonly IConfiguration configuration;

        public RabbitMQConsumer(ILogger<RabbitMQConsumer> logger, RabbitMQProvider rabbitMQProvider, ICommandeService commandeService, IFactureService factureService, IWebHostEnvironment env, IConfiguration configuration)
        {
            this.commandeService = commandeService;
            this.logger = logger;
            this.rabbitMQProvider = rabbitMQProvider;
            this.DeclarationExchangeQueue();
            this.channelFacture = this.rabbitMQProvider.Connection.CreateModel();
            this.channelFactureCmd = this.rabbitMQProvider.Connection.CreateModel();
            this.factureService = factureService;
            this.env = env;
            this.configuration = configuration;
        }

        public void DeclarationExchangeQueue()
        {
            using (IModel channel = this.rabbitMQProvider.Connection.CreateModel())
            {
                channel.ExchangeDeclare("facture.exchange", ExchangeType.Direct, durable: true);

                channel.QueueDeclare(
                    queue: "facture.queue",
                    durable: true,
                    exclusive: false,
                    autoDelete: false,
                    arguments: null);

                channel.QueueDeclare(
                    queue: "factureCmd.queue",
                    durable: true,
                    exclusive: false,
                    autoDelete: false,
                    arguments: null);

                channel.QueueBind("facture.queue", "facture.exchange", "facture.routingKey", null);

                channel.QueueBind("factureCmd.queue", "facture.exchange", "factureCmd.routingKey", null);
            }
        }
        
        public void Subcribe()
        {
            EventingBasicConsumer consumeFacture = new(this.channelFacture);
            consumeFacture.Received += ListennigFacture;

            EventingBasicConsumer consumeCmd = new(this.channelFactureCmd);
            consumeCmd.Received += ListennigCmd;

            this.channelFacture.BasicConsume(queue: "facture.queue", autoAck: false, consumer: consumeFacture);

            this.channelFactureCmd.BasicConsume(queue: "factureCmd.queue", autoAck: false, consumer: consumeCmd);
        }

        private void ListennigFacture(object? sender, BasicDeliverEventArgs e)
        {
            byte[] body = e.Body.ToArray();

            string message = Encoding.UTF8.GetString(body);

            this.channelFacture.BasicAck(e.DeliveryTag, false);

            FactureModel? factureModel = JsonConvert.DeserializeObject<FactureModel>(message);

            if (factureModel != null)
            {
                Patient patientModel = new() { 
                    prenom = factureModel.patient.prenom, 
                    nom = factureModel.patient.nom, 
                    email = factureModel.patient.email,
                    numSecu = factureModel.patient.numSecu,
                    numTel = factureModel.patient.numTel,
                    dateNaissance = factureModel.patient.dateNaissance,
                    genre = factureModel.patient.genre, 
                    idMedecinTraitant = factureModel.patient.idMedecinTraitant, 
                    antecedents = factureModel.patient.antecedents
                };
                Facture facture = new() { DateFature = DateTime.Now, type = factureModel.type, listeProduits = factureModel.listeProduits, patient = patientModel, coutDuPatient = factureModel.coutDuPatient };
                Facture entity = this.factureService.Create(facture);
                this.factureService.ConfirmationFactureGenere(entity, this.configuration.GetValue<string>("secretaireEmail")??"", Path.Combine(env.ContentRootPath, "pdfFile-bill"));
                logger.LogInformation($" message reçu le : {JsonConvert.SerializeObject(entity)}");
            }

        }

        private void ListennigCmd(object? sender, BasicDeliverEventArgs e)
        {
            byte[] body = e.Body.ToArray();

            string message = Encoding.UTF8.GetString(body);

            this.channelFactureCmd.BasicAck(e.DeliveryTag, false);

            JsonSerializerSettings settings = new JsonSerializerSettings{DateFormatString = "dd-MM-yyyy"};

            Commande? commande =  JsonConvert.DeserializeObject<Commande>(message, settings);
            if (commande != null)
            {
                Commande cmd = this.commandeService.Create(commande);
                logger.LogInformation($" message reçu le : {JsonConvert.SerializeObject(cmd)}");
            }
        }

        public void ClosingChannelAndConnection()
        {
            this.channelFacture.Close();
            this.channelFactureCmd.Close();
            this.rabbitMQProvider.Connection.Close();
        }
    }
}
