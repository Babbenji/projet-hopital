package fr.univ.orleans.miage.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

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
						)
						.uri("http://localhost:8080")
				)
				// Route vers le service de notification
				.route(r -> r.path("/api/notification/**")
						.filters(f -> f.rewritePath("/api/notification/(?<remains>.*)", "/api/v1/notif/${remains}")
								.preserveHostHeader()
						)
						.uri("http://localhost:8080")
				)
				// Route vers le service de gestion rdv patients
				.route(r -> r.path("/rdvpatients/**")
						.filters(f -> f.rewritePath("/rdvpatients/(?<remains>.*)", "/api/v1/rdvpatients/${remains}")
								.preserveHostHeader()
						)
						.uri("http://localhost:8080")
				)
				// Route vers le service de gestion de stocks
				.route(r -> r.path("/gestionnaire/**")
						.filters(f -> f.rewritePath("/gestionnaire/(?<remains>.*)", "/api/v1/gestionnaire/${remains}")
								.preserveHostHeader()
						)
						.uri("http://localhost:8080")
				)
				// Route vers le service de facturation
				.route(r -> r.path("/api/facture/**")
						.filters(f -> f.rewritePath("/api/facture/(?<remains>.*)", "/api/v1/facturation/${remains}")
								.preserveHostHeader()
						)
						.uri("http://localhost:8080")
				)
				.build();
	}



}
