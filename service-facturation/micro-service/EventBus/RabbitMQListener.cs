namespace micro_service.EventBus
{
    public class RabbitMQListener : IHostedService
    {
        private readonly IRabbitMQConsumer consumer;

        public RabbitMQListener(IRabbitMQConsumer consumer)
        {
            this.consumer = consumer;
        }
        public Task StartAsync(CancellationToken cancellationToken)
        {
            this.consumer.SubcribeQueue("boite_recept");
            return Task.CompletedTask;
        }

        public Task StopAsync(CancellationToken cancellationToken)
        {
            this.consumer.ClosingChannelAndConnection();
            return Task.CompletedTask;
        }
    }
}
