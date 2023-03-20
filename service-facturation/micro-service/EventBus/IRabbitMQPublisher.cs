namespace micro_service.EventBus
{
    public interface IRabbitMQPublisher
    {
        void Publish<T>(T message, string queueName, string exchangeName, string routingKey);
    }
}
