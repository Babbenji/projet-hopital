namespace micro_service.Service.Exceptions
{
    public class FactureNotFoundException : Exception
    {
        public FactureNotFoundException(string message):base(message){ }
    }
}
