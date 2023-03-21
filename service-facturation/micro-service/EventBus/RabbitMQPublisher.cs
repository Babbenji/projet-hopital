using Microsoft.Extensions.Options;
using Newtonsoft.Json;
using RabbitMQ.Client;
using System.Text;

namespace micro_service.EventBus
{
    public class RabbitMQPublisher : RabbitMQProvider, IRabbitMQPublisher
    {
        private readonly ILogger<RabbitMQPublisher> logger;

        public RabbitMQPublisher(IOptions<RabbitMQConfig> config, ILogger<RabbitMQPublisher> logger) : base(config.Value) 
        { 
            this.logger = logger;
        }

        public void Publish<T>(T message, string exchangeName, string routingKey)
        {
            using (IModel channel = this.connection.CreateModel())
            {
                this.logger.LogInformation(JsonConvert.SerializeObject(message));
                byte[] body = Encoding.UTF8.GetBytes(JsonConvert.SerializeObject(message));

                channel.BasicPublish(exchangeName, routingKey, null, body);
            }
        }
    }
}
