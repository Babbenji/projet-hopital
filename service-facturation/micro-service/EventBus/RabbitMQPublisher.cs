using Microsoft.Extensions.Options;
using Newtonsoft.Json;
using RabbitMQ.Client;
using System.Text;

namespace micro_service.EventBus
{
    public class RabbitMQPublisher : RabbitMQProvider, IRabbitMQPublisher
    {
        //amqp://guest:guest@localhost:5672 config.Value.Protocol + "://" + config.Value.Username + ":" + config.Value.Password + "@" + config.Value.Host + ":" + config.Value.Port + ""
        public RabbitMQPublisher(IOptions<RabbitMQConfig> config) : base(config.Value.Protocol + "://" + config.Value.Username + ":" + config.Value.Password + "@" + config.Value.Host + ":" + config.Value.Port + "") { }

        public void Publish<T>(T message)
        {
            using (IModel channel = this.connection.CreateModel())
            {
                channel.ExchangeDeclare("CustomerNotification", ExchangeType.Direct);

                channel.QueueDeclare(
                    queue: "sending",
                    durable: false,
                    exclusive: false,
                    autoDelete: false,
                    arguments: null);

                channel.QueueBind("sending", "CustomerNotification", "recept", null);

                var body = Encoding.UTF8.GetBytes(JsonConvert.SerializeObject(message));

                channel.BasicPublish("CustomerNotification", "recept", null, body);
            }
            


        }
    }
}
