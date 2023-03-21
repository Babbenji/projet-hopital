namespace micro_service.EventBus
{
    public interface IRabbitMQPublisher
    {
        void Publish<T>(T message, string exchangeName, string routingKey);
    }
}
