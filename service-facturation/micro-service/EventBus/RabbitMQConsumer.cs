using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using System.Text;

namespace micro_service.EventBus
{
    public class RabbitMQConsumer : IRabbitMQConsumer
    {
        private readonly ILogger<RabbitMQConsumer> logger;
        private readonly RabbitMQProvider rabbitMQProvider;
        private readonly IModel channel;

        public RabbitMQConsumer(ILogger<RabbitMQConsumer> logger, RabbitMQProvider rabbitMQProvider)
        {
            this.logger = logger;
            this.rabbitMQProvider = rabbitMQProvider;
            this.DeclarationExchangeQueue();
            this.channel = this.rabbitMQProvider.Connection.CreateModel();
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

                channel.QueueBind("facture.queue", "facture.exchange", "facture.routingKey", null);
            }
        }
        
        public void SubcribeQueue(string queueName)
        {
            EventingBasicConsumer consume = new(this.channel);
            consume.Received += Listennig;

            this.channel.BasicConsume(queue: queueName, autoAck: false, consumer: consume);
        }

        private void Listennig(object? sender, BasicDeliverEventArgs e)
        {
            byte[] body = e.Body.ToArray();

            string message = Encoding.UTF8.GetString(body);

            this.channel.BasicAck(e.DeliveryTag, false);

            logger.LogInformation($" message reçu le : {message}");
        }

        public void ClosingChannelAndConnection()
        {
            this.channel.Close();
            this.rabbitMQProvider.Connection.Close();
        }
    }
}
