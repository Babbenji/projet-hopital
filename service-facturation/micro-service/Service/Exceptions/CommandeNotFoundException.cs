namespace micro_service.Service.Exceptions
{
    public class CommandeNotFoundException : Exception
    {
        public CommandeNotFoundException(string message) : base(message) { }

        public CommandeNotFoundException() { }
    }
}
