using micro_service.Controllers;
using Microsoft.Extensions.Options;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using System.Text;

namespace micro_service.EventBus
{
    public class RabbitMQConsumer : RabbitMQProvider, IRabbitMQConsumer
    {
        private readonly ILogger<RabbitMQConsumer> logger;
        public RabbitMQConsumer(ILogger<RabbitMQConsumer> logger, IOptions<RabbitMQConfig> config) : base(config.Value)
        {
            this.logger = logger;
        }
        
        public void SubcribeQueue(string queueName)
        {
            IModel channel = this.connection.CreateModel();

            EventingBasicConsumer consume = new(channel);
            consume.Received += (sender, args) =>
            {
                byte[] body = args.Body.ToArray();

                string message = Encoding.UTF8.GetString(body);

                channel.BasicAck(args.DeliveryTag, false);

                logger.LogInformation($" message reçu le : {message}");
            };

            channel.BasicConsume(queue: queueName, autoAck: false, consumer: consume);
        }
    }
}
