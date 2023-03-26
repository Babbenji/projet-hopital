using Microsoft.Extensions.Options;
using RabbitMQ.Client;

namespace micro_service.EventBus
{
   
    public class RabbitMQProvider
    {
        private readonly ConnectionFactory factory;
        private readonly IConnection connection;

        public RabbitMQProvider(IOptions<RabbitMQConfig> rabbitMQConfig)
        {
            this.factory = new ConnectionFactory();
            this.factory.Uri = new Uri($"{rabbitMQConfig.Value.Protocol}://{rabbitMQConfig.Value.Username}:{rabbitMQConfig.Value.Password}@{rabbitMQConfig.Value.Host}:{rabbitMQConfig.Value.Port}");
            this.factory.AutomaticRecoveryEnabled = true;
            this.factory.DispatchConsumersAsync = false;
            this.connection = factory.CreateConnection("service-facturation");
        }

        public IConnection Connection
        {
            get { return this.connection; }
        }

    }
}
