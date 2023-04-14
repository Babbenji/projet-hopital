using Consul;
using System.Text;
namespace micro_service.Security
{
    public class PublicKeyFetching : BackgroundService
    {

        private readonly IConsulClient consulClient;
        private readonly ILogger<PublicKeyFetching> logger;

        public PublicKeyFetching(IConsulClient consulClient, ILogger<PublicKeyFetching> logger)
        {
            this.consulClient = consulClient;
            this.logger = logger;
        }

        protected override async Task ExecuteAsync(CancellationToken stoppingToken)
        { 
            
            Task.Delay(5000).Wait();

            while (!stoppingToken.IsCancellationRequested)
            {
                QueryResult<KVPair> getPair = await consulClient.KV.Get("config/crytographie/clepublique");

                if (getPair != null && getPair.Response != null)
                {
                    RsaKey.publicKey = Encoding.UTF8.GetString(getPair.Response.Value);
                    logger.LogInformation("public key received");
                }

                await Task.Delay(3600000);
            }
        }
    }
}
