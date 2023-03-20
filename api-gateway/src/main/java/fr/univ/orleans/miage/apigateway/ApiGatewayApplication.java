package fr.univ.orleans.miage.apigateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

	// ENV. PRODUCTION
	private String SERVICE_AUTH = "http://service-auth:8081";
	private String SERVICE_NOTIF = "http://service-notif:8082";
	private String SERVICE_RDV_PATIENTS = "http://service-rdv-patients:8083";
	private String SERVICE_STOCK_FOURNISSEURS = "http://service-stockfournisseurs:8084";
	private String SERVICE_FACTURATION = "http://service-facturation:80";

	// ENV. DEVELOPPEMENT

//	@Value("${dev.service-auth}")
//	private String SERVICE_AUTH;
//	@Value("${dev.service-notif}")
//	private String SERVICE_NOTIF;
//	@Value("${dev.service-rdv-patients}")
//	private String SERVICE_RDV_PATIENTS;
//	@Value("${dev.service-stock}")
//	private String SERVICE_STOCK_FOURNISSEURS;
//	@Value("${dev.service-facture}")
//	private String SERVICE_FACTURATION;

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator gatewayRoutes(RouteLocatorBuilder routeLocatorBuilder) {

		return routeLocatorBuilder.routes()
				// Route vers le service d'authentification
				.route(r -> r.path("/api/auth/**")
						.filters(f -> f.rewritePath("/api/auth/(?<remains>.*)", "/api/v1/auth/${remains}")
								.preserveHostHeader()
								.rewriteLocationResponseHeader("ALWAYS_STRIP","Location","","")
						)
						.uri(SERVICE_AUTH)
				)
				// Route vers le service de notification
				.route(r -> r.path("/api/notification/**")
						.filters(f -> f.rewritePath("/api/notification/(?<remains>.*)", "/api/v1/notif/${remains}")
								.preserveHostHeader()
						)
						.uri(SERVICE_NOTIF)
				)
				// Route vers le service de gestion rdv patients
				.route(r -> r.path("/rdvpatients/**")
						.filters(f -> f.rewritePath("/rdvpatients/(?<remains>.*)", "/api/v1/rdvpatients/${remains}")
								.preserveHostHeader()
						)
						.uri(SERVICE_RDV_PATIENTS)
				)
				// Route vers le service de gestion de stocks
				.route(r -> r.path("/gestionnaire/**")
						.filters(f -> f.rewritePath("/gestionnaire/(?<remains>.*)", "/api/v1/gestionnaire/${remains}")
								.preserveHostHeader()
						)
						.uri(SERVICE_STOCK_FOURNISSEURS)
				)
				// Route vers le service de facturation
				.route(r -> r.path("/api/facture/**")
						.filters(f -> f.rewritePath("/api/facture/(?<remains>.*)", "/api/facture/v1/${remains}")
								.preserveHostHeader()
						)
						.uri(SERVICE_FACTURATION)
				)
				.build();
	}


}
