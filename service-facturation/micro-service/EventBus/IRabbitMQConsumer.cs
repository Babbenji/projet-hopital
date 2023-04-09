namespace micro_service.EventBus
{
    public interface IRabbitMQConsumer
    {
        void Subcribe();

        void ClosingChannelAndConnection();
    }
}
