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

	@Value("${prod.uri.service-auth:http://localhost:8081}")
	private String SERVICE_AUTH;
	@Value("${prod.uri.service-notif:http://localhost:8082}")
	private String SERVICE_NOTIF;
	@Value("${prod.uri.service-rdv-patients:http://localhost:8083}")
	private String SERVICE_RDV_PATIENTS;
	@Value("${prod.uri.service-stock:http://localhost:8084}")
	private String SERVICE_STOCK_FOURNISSEURS;
	@Value("${prod.uri.service-facture:http://localhost:8085}")
	private String SERVICE_FACTURATION;

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
								.rewriteResponseHeader("Location","/api/v1/auth/","/api/auth/")
						)
						.uri(SERVICE_AUTH)
				)
				// Route vers le service de notification
				.route(r -> r.path("/api/notification/**")
						.filters(f -> f.rewritePath("/api/notification/(?<remains>.*)", "/api/v1/notif/${remains}")
								.preserveHostHeader()
								.rewriteResponseHeader("Location","/api/v1/notif/","/api/notification/")
						)
						.uri(SERVICE_NOTIF)
				)
				// Route vers le service de gestion rdv patients
				.route(r -> r.path("/api/rdvpatients/**")
						.filters(f -> f.rewritePath("/api/rdvpatients/(?<remains>.*)", "/api/v1/rdvpatients/${remains}")
								.preserveHostHeader()
								.rewriteResponseHeader("Location","/api/v1/rdvpatients/","/api/rdvpatients/")
						)
						.uri("http://localhost:8083")
				)
				// Route vers le service de gestion de stocks
				.route(r -> r.path("/gestionnaire/**")
						.filters(f -> f.rewritePath("/gestionnaire/(?<remains>.*)", "/api/v1/gestionnaire/${remains}")
								.preserveHostHeader()
								.rewriteResponseHeader("Location","/api/v1/gestionnaire/","/gestionnaire/")
						)
						.uri(SERVICE_STOCK_FOURNISSEURS)
				)
				// Route vers le service de facturation
				.route(r -> r.path("/api/facture/**")
						.filters(f -> f.rewritePath("/api/facture/(?<remains>.*)", "/api/facture/v1/${remains}")
								.preserveHostHeader()
								.rewriteResponseHeader("Location","/api/facture/v1/","/api/facture/")
						)
						.uri(SERVICE_FACTURATION)
				)
				.build();
	}


}
