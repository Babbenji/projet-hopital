namespace micro_service.EventBus
{
    public interface IRabbitMQConsumer
    {
        void SubcribeQueue(string queueName);

        void ClosingChannelAndConnection();
    }
}
