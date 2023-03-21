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
        
        private readonly IModel channel;

        public RabbitMQConsumer(ILogger<RabbitMQConsumer> logger, IOptions<RabbitMQConfig> config) : base(config.Value)
        {
            this.logger = logger;
            this.channel = this.connection.CreateModel();
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
            this.connection.Close();
        }
    }
}
