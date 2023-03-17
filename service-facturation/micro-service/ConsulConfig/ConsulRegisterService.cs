using Consul;
using Microsoft.AspNetCore.Hosting.Server;
using Microsoft.AspNetCore.Hosting.Server.Features;
using static System.Net.WebRequestMethods;

namespace micro_service.ConsulConfig
{
    public class ConsulRegisterService : IHostedService
    {
        private readonly ILogger<ConsulRegisterService> logger;
        private readonly IConsulClient consulClient;
        public ConsulRegisterService(IConsulClient consulClient, ILogger<ConsulRegisterService> logger)
        {
            this.logger = logger;
            this.consulClient = consulClient;
        }

        public async Task StartAsync(CancellationToken cancellationToken)
        {

           
            Uri uri = new Uri("http://localhost:41487");
            AgentServiceRegistration agentServiceRegistration = new AgentServiceRegistration()
            {
                Address = uri.Host,
                Name = "service facturation",
                Port = uri.Port,
                ID = "service-facturation"
            };

           await this.consulClient.Agent.ServiceDeregister("service-facturation", cancellationToken);
           await this.consulClient.Agent.ServiceRegister(agentServiceRegistration, cancellationToken);
        }

        public async Task StopAsync(CancellationToken cancellationToken)
        {
            try
            {
                await this.consulClient.Agent.ServiceDeregister("service-facturation", cancellationToken);
                
            }
            catch (Exception ex)
            {
                logger.LogError(ex.Message);
            }
        }
    }
}
