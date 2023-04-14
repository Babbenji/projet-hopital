using Consul;
using micro_service.ConsulConfig;
using micro_service.EventBus;
using micro_service.Helpers;
using micro_service.Repository;
using micro_service.Security;
using micro_service.Service;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.OpenApi.Models;
using MongoDB.Driver;
using System.Security.Claims;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen(c =>
{
    c.SwaggerDoc("v1", new OpenApiInfo
    {
        Title = "API Service comptable",
        Version = "1.0",
        Description = "Documentation OpenAPI du service comptable",
        License = new OpenApiLicense() { Name= "Universite d'Orleans", Url= new Uri("https://www.univ-orleans.fr/fr") }
    });
});

builder.Services.AddSingleton<IMongoClient>(p => new MongoClient(builder.Configuration.GetConnectionString("Default")));

builder.Services.Configure<RabbitMQConfig>(builder.Configuration.GetSection("RabbitMQ"));

builder.Services.Configure<ServerHostConfiguration>(builder.Configuration.GetSection("ServeurDetails"));

builder.Services.Configure<HyperLinKHelpers>(builder.Configuration.GetSection("ServeurPDFLink"));

builder.Services.AddSingleton<RabbitMQProvider>();

builder.Services.AddSingleton<PDFHelpers>();

builder.Services.AddSingleton<IRabbitMQConsumer, RabbitMQConsumer>();

builder.Services.AddSingleton<IRabbitMQPublisher, RabbitMQPublisher>();

builder.Services.AddSingleton<IFactureRepository, FactureRepository>();

builder.Services.AddSingleton<IFactureService, FactureService>();

builder.Services.AddSingleton<ICommendRepository, CommendRepository>();

builder.Services.AddSingleton<ICommandeService, CommandeService>();

builder.Services.AddSingleton<IBilanService, BilanService>();



builder.Services.AddSingleton<IConsulClient, ConsulClient>(p => new ConsulClient(config => config.Address = new Uri("http://"+builder.Configuration.GetSection("Consul:Host").Value + ":" + builder.Configuration.GetSection("Consul:Port").Value)));

builder.Services.AddHostedService<ConsulRegisterService>();

builder.Services.AddHostedService<RabbitMQListener>();

builder.Services.AddHostedService<PublicKeyFetching>();




builder.Services.AddHealthChecks();





builder.Services.AddAuthentication(JwtBearerDefaults.AuthenticationScheme).AddJwtBearer(opt =>
{
    opt.TokenValidationParameters = new() 
    {
        ValidateIssuer = false,
        ValidateAudience = false,
        ValidateLifetime = true,
        ValidateIssuerSigningKey = true,
        RoleClaimType = "scope",
        NameClaimType = "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier",
        IssuerSigningKey = RSAConfiguration.RSASignature()
    };
});



var app = builder.Build();


// Configure the HTTP request pipeline.
//if (app.Environment.IsDevelopment())
//{
    app.UseSwagger();
    app.UseSwaggerUI();
//}


app.Use(async (context, next) =>
{
    if(context.User.Identity != null)
    {
        if (context.User.Identity.IsAuthenticated)
        {
            Claim? claim = context.User.FindFirst("exp");
            string expires =  claim != null ? claim.Value : "";
            if (!string.IsNullOrEmpty(expires) && long.TryParse(expires, out long expiresEpoch))
            {
                DateTime expiresUtc = DateTimeOffset.FromUnixTimeSeconds(expiresEpoch).UtcDateTime;
                if (expiresUtc <= DateTime.UtcNow)
                {
                    context.Response.StatusCode = 401;
                    await context.Response.WriteAsync("Token expir�");
                    return;
                }
            }
        }
    }

    await next.Invoke();
});

app.UseRouting();

app.UseAuthorization();

app.UseEndpoints(endpoints => { endpoints.MapHealthChecks("/health"); });



app.MapControllers();



app.Run();
