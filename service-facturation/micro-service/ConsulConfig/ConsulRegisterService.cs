using Consul;
using Microsoft.Extensions.Options;

namespace micro_service.ConsulConfig
{
    public class ConsulRegisterService : IHostedService
    {
        private readonly ILogger<ConsulRegisterService> logger;
        private readonly IConsulClient consulClient;
        private readonly ServerHostConfiguration serverHostConfiguration;
        public ConsulRegisterService(IConsulClient consulClient, ILogger<ConsulRegisterService> logger, IOptions<ServerHostConfiguration> serverHostConfiguration)
        {
            this.logger = logger;
            this.consulClient = consulClient;
            this.serverHostConfiguration = serverHostConfiguration.Value;
        }

        public async Task StartAsync(CancellationToken cancellationToken)
        {
           

            Uri uri = new Uri("http://"+ this.serverHostConfiguration.Host + ":"+ this.serverHostConfiguration.Port+"");
            AgentServiceRegistration agentServiceRegistration = new AgentServiceRegistration()
            {
                Address = uri.Host,
                Name = "service-comptable",
                Port = uri.Port,
                ID = "service-comptable"
            };

           await this.consulClient.Agent.ServiceDeregister("service-comptable", cancellationToken);
           await this.consulClient.Agent.ServiceRegister(agentServiceRegistration, cancellationToken);
            
        }

        public async Task StopAsync(CancellationToken cancellationToken)
        {
            try
            {
                await this.consulClient.Agent.ServiceDeregister("service-comptable", cancellationToken);
                
            }
            catch (Exception ex)
            {
                logger.LogError(ex.Message);
            }
        }
    }
}
