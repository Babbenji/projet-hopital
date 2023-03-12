namespace micro_service.EventBus
{
    public class EventBusBackgroundService : BackgroundService
    {
        private readonly RabbitMQService rabbitMQService;

        public EventBusBackgroundService(RabbitMQService rabbitMQService)
        {
            this.rabbitMQService = rabbitMQService;
        }

        protected override async Task ExecuteAsync(CancellationToken stoppingToken)
        {
            while (!stoppingToken.IsCancellationRequested)
            {
                rabbitMQService.Consuming();

                await Task.Delay(1000);
            }
        }
    }
}
