

### Reste à faire 

- [x] Tests http requests (service facturation)
- [x] Communication entre services ave RabbitMQ 
- [x] Sécurité des services (gestion des accès)
- [ ] Serveur de configuration centralisé ++
- [ ] Tests d'intégration avec karate
- [ ] Tests controleur avec REST Assured
- [ ] Tests services avec JUnit et Mockito
- [ ] Frontend avec Ajax et jQuery (template bootstrap)
- 
### Optionnel
- [ ] Documentation API REST DOC
- [ ] Open ID Connect
- [ ] Swagger pour afficher l'api design
- [ ] ENV. PROD et ENV. DEV à mettre en place +++   

### Problèmes à resoudre
- [x] Location
- [ ] Clé publique à générer et à récupérer de facon plus propre (la stocké server config ou consul)
- [x] Améliorer la gestion de la gateway (prod et dev)

### Déployer le service

#### Commandes docker environnement de "production"

````
# 1) Commande pour construire les images des services définis dans le fichier docker-compose.yml
docker compose build

# 2) Commande pour démarrer les conteneurs des services définis dans le fichier docker-compose.yml
docker compose up -d

 ````

#### Commandes docker environnement de développement

````
docker run -d --name=dev-consul -p 8500:8500 -e CONSUL_BIND_INTERFACE=eth0 consul

docker run -d --hostname rabbit-nimp --name rabbitmq -p 5672:5672 -p 15672:15672
rabbitmq:3-management

````

### Ordre de lancement des services

1. service annuaire (consul)
2. service messaging / bus d'events (rabbitmq)
3. serveurs de base de données (postgres, mongodb, h2)
4. service de configuration (config-server)
5. service gateway (spring-cloud-gateway)
6. service d'authentification 
7. service métiers

- service de notification
- service de gestion de rendez-vous
- service de gestion de stock
- service de facturation


### Dépendances principales du microservice (pom.xml)


**Pour tous les microservices :**

- Sprint Boot Actuator
- Consul Discovery

````
   <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-consul-discovery</artifactId>
    </dependency>
````

- Spring Web
- Spring Data JPA
- MongoDB Driver Database - Postgres Driver Database


**Service config :** :
- Spring Cloud Config Server

https://cloud.spring.io/spring-cloud-config/spring-cloud-config.html
https://docs.spring.io/spring-cloud-config/docs/current/reference/html/

**Service gateway :**
- Spring Cloud Gateway

**Service authentification :**
- Spring Security
- OAuth2 Ressource Server
- Spring Configuration Processor
- _Config Client_

**Service métiers & authentification :**
- OAuth2 Ressource Server
- RabbitMQ
- Config Client


````
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
        </dependency>
        
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.amqp</groupId>
            <artifactId>spring-rabbit-test</artifactId>
            <scope>test</scope>
        </dependency>
        
````

**Service notification :**
- Java Mail Sender : permet d'envoyer des emails

### Dépendances entre les services

Schéma de dépendances entre les services à venir



### Annexes - Documentation

https://springdoc.org/v2/



https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#section-builtin-constraints






### URL Swagger OPEN API


http://localhost:8080/service-notif/swagger-ui/index.html

http://localhost:8080/v3/api-docs/swagger-config





