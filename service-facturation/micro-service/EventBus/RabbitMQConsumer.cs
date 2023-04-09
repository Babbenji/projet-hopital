using micro_service.Models;
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
        private readonly ILogger<RabbitMQConsumer> logger;
        private readonly RabbitMQProvider rabbitMQProvider;
        private readonly IModel channelFacture;
        private readonly IModel channelFactureCmd;

        public RabbitMQConsumer(ILogger<RabbitMQConsumer> logger, RabbitMQProvider rabbitMQProvider, ICommandeService commandeService)
        {
            this.commandeService = commandeService;
            this.logger = logger;
            this.rabbitMQProvider = rabbitMQProvider;
            this.DeclarationExchangeQueue();
            this.channelFacture = this.rabbitMQProvider.Connection.CreateModel();
            this.channelFactureCmd = this.rabbitMQProvider.Connection.CreateModel();
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

            logger.LogInformation($" message reçu le : {message}");
        }

        private void ListennigCmd(object? sender, BasicDeliverEventArgs e)
        {
            byte[] body = e.Body.ToArray();

            string message = Encoding.UTF8.GetString(body);

            this.channelFactureCmd.BasicAck(e.DeliveryTag, false);

            JsonSerializerSettings settings = new JsonSerializerSettings
            {
                DateFormatString = "dd-MM-yyyy"
            };

            Commande? commande =  JsonConvert.DeserializeObject<Commande>(message, settings);
            if (commande != null)
            {
                Commande cmd = this.commandeService.Create(commande);
                logger.LogInformation($" message reçu le : {cmd}");
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
