using Newtonsoft.Json;
using RabbitMQ.Client;
using System.Text;

namespace micro_service.EventBus
{
    public class RabbitMQPublisher: IRabbitMQPublisher
    {
        private readonly ILogger<RabbitMQPublisher> logger;
        private readonly RabbitMQProvider rabbitMQProvider;


        public RabbitMQPublisher(ILogger<RabbitMQPublisher> logger, RabbitMQProvider rabbitMQProvider)
        { 
            this.logger = logger;
            this.rabbitMQProvider = rabbitMQProvider;
        }

        public void Publish<T>(T message, string exchangeName, string routingKey)
        {
            using (IModel channel = this.rabbitMQProvider.Connection.CreateModel())
            {
                this.logger.LogInformation(JsonConvert.SerializeObject(message));
                byte[] body = Encoding.UTF8.GetBytes(JsonConvert.SerializeObject(message));

                channel.BasicPublish(exchangeName, routingKey, null, body);
            }
        }
    }
}
