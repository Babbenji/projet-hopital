using Microsoft.AspNetCore.Connections;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using System.Text;

namespace micro_service.EventBus
{
   
    public abstract class RabbitMQProvider
    {
        private readonly ConnectionFactory factory;
        protected readonly IConnection connection;
        //amqp://guest:guest@localhost:5672
        public RabbitMQProvider(string url)
        {
            this.factory = new ConnectionFactory();
            this.factory.Uri = new(url);
            this.factory.AutomaticRecoveryEnabled = true;
            this.factory.DispatchConsumersAsync = false;
            this.connection = factory.CreateConnection("DemoAppClient2");
        }

    }
}
