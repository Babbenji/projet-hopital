using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using System.Text;

namespace micro_service.EventBus
{
   
    public class RabbitMQService
    {
        private readonly ILogger<RabbitMQService> logger;

        private ConnectionFactory factory;

        public RabbitMQService(ILogger<RabbitMQService> logger)
        {
            this.logger = logger;
            this.factory = new ConnectionFactory
            {
                HostName = "localhost"
            };
        }

        public void Consuming()
        {
            

            using IConnection connection = this.factory.CreateConnection();

            using IModel channel = connection.CreateModel();
            channel.QueueDeclare(
                queue: "boite_recept",
                durable: false,
                autoDelete: false,
                exclusive: false,
                arguments: null
            );


            EventingBasicConsumer consume = new(channel);

            consume.Received += (sender, args) =>
            {
                byte[] body = args.Body.ToArray();

                string message = Encoding.UTF8.GetString(body);
                logger.LogInformation($" message reçu le : {message}");
            };

            channel.BasicConsume(queue: "boite_recept", autoAck: true, consumer: consume);
        }

        





    }
}
