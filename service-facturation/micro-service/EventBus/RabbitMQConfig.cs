namespace micro_service.EventBus
{
    public class RabbitMQConfig
    {
        public string Protocol { get; set; } = string.Empty;
        public string Host { get; set; } = string.Empty;
        public string Username { get; set; } = string.Empty;
        public string Password { get; set; } = string.Empty;
        public int Port { get; set; }
    }
}
