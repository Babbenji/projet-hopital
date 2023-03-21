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


            using (var channel = connection.CreateModel())
            {
                channel.ExchangeDeclare("CustomerNotification", ExchangeType.Direct);

                channel.QueueDeclare(
                    queue: "boite_recept",
                    durable: false,
                    exclusive: false,
                    autoDelete: false,
                    arguments: null);

                channel.QueueBind("boite_recept", "CustomerNotification", "recept", null);
            }

           

        }

    }
}
