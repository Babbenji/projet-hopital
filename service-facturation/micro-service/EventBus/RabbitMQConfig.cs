namespace micro_service.EventBus
{
    public class RabbitMQConfig
    {
        public string Protocol { get; set; } = "";
        public string Host { get; set; } = "";
        public string Username { get; set; } = "";
        public string Password { get; set; } = "";
        public int Port { get; set; }

        //public string Uri { get; set; } = "";
    }
}
