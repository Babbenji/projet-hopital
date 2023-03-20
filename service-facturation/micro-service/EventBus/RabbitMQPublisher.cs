using Microsoft.Extensions.Options;
using Newtonsoft.Json;
using RabbitMQ.Client;
using System.Text;

namespace micro_service.EventBus
{
    public class RabbitMQPublisher : RabbitMQProvider, IRabbitMQPublisher
    {
        public RabbitMQPublisher(IOptions<RabbitMQConfig> config) : base(config.Value) { }

        public void Publish<T>(T message, string queueName, string exchangeName, string routingKey)
        {
            using (IModel channel = this.connection.CreateModel())
            {
                channel.ExchangeDeclare(exchangeName, ExchangeType.Direct);

                channel.QueueDeclare(
                    queue: queueName,
                    durable: false,
                    exclusive: false,
                    autoDelete: false,
                    arguments: null);

                channel.QueueBind(queueName, exchangeName, routingKey, null);

                var body = Encoding.UTF8.GetBytes(JsonConvert.SerializeObject(message));

                channel.BasicPublish(exchangeName, routingKey, null, body);
            }
        }
    }
}
