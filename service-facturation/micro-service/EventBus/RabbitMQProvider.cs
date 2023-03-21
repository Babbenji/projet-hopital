using RabbitMQ.Client;

namespace micro_service.EventBus
{
   
    public abstract class RabbitMQProvider
    {
        private readonly ConnectionFactory factory;
        protected readonly IConnection connection;

        public RabbitMQProvider(RabbitMQConfig rabbitMQConfig)
        {
            this.factory = new ConnectionFactory();
            this.factory.Uri = new Uri($"{rabbitMQConfig.Protocol}://{rabbitMQConfig.Username}:{rabbitMQConfig.Password}@{rabbitMQConfig.Host}:{rabbitMQConfig.Port}");
            this.factory.AutomaticRecoveryEnabled = true;
            this.factory.DispatchConsumersAsync = false;
            this.connection = factory.CreateConnection("service-facturation");


            using (IModel channel = connection.CreateModel())
            {
                channel.ExchangeDeclare("facture.exchange", ExchangeType.Direct,durable: true);

                channel.QueueDeclare(
                    queue: "facture.queue",
                    durable: true,
                    exclusive: false,
                    autoDelete: false,
                    arguments: null);

                channel.QueueBind("facture.queue", "facture.exchange", "facture.routingKey", null);
            }

           

        }

    }
}
