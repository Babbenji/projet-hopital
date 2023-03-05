
## Authentification

### Description

Le service d'authentification permet de gérer les utilisateurs et les rôles de l'application. Il permet de s'authentifier et de récupérer un token JWT.


+------------------------+                 +------------------------+
|                        |                 |                        |
|   Client (utilisateur) |                 |   Serveur (AuthConfig)  |
|                        |                 |                        |
+------------------------+                 +------------------------+
|                                           |
|   1. Demande d'authentification             |
|------------------------------------------>|
|                                           |
|                                           |
|                                           |
|   2. Envoi des informations d'identification|
|<------------------------------------------|
|                                           |
|                                           |
|   3. Vérification des informations          |
|                                           |
|                                           |
|                                           |
|        4.1 Si les informations sont valides |
|<------------------------------------------|
|                                           |
|                                           |
|        4.2 Si les informations ne sont pas  |
|            valides                          |
|<------------------------------------------|
|                                           |
|                                           |
|   5. Génération d'un JWT                    |
|------------------------------------------>|
|                                           |
|                                           |
|   6. Envoi du JWT                           |
|<------------------------------------------|
|                                           |


### Security

* Spring Security
* Spring OAuth2
* Spring Cloud Security
* Spring Cloud OAuth2
* Spring Cloud OpenID Connect

Lorsque Spring Security est utilisé avec Spring Cloud, il est possible de configurer Spring Cloud Security pour utiliser Spring Security OAuth2. Spring Cloud Security fournit une implémentation de Spring Security OAuth2 qui est compatible avec les services d'authentification OAuth2 de Spring Cloud.

### Spring Cloud Security

Spring Cloud Security fournit une implémentation de Spring Security OAuth2 qui est compatible avec les services d'authentification OAuth2 de Spring Cloud. Spring Cloud Security fournit également une implémentation de Spring Security OAuth2 qui est compatible avec les services d'authentification OpenID Connect de Spring Cloud.

Quand Spring security se lance il va chercher les informations de configuration dans le fichier application.yml. Il va ensuite chercher les informations de configuration dans le fichier bootstrap.yml. Il va ensuite chercher les informations de configuration dans le fichier application.properties. Il va ensuite chercher les informations de configuration dans le fichier bootstrap.properties.

Quant Spring security se lance au lancement de l'application, il va utiliser un objet de type UserDetailsService pour récupérer les informations de l'utilisateur. Il va ensuite utiliser un objet de type PasswordEncoder pour encoder le mot de passe de l'utilisateur.
Cet userDetailsService est défini dans la classe de configuration SecurityConfig. Il s'agit d'une interface qui est implémentée par la classe CustomUserDetailsService. Cette classe CustomUserDetailsService est annotée avec @Service. Cette classe CustomUserDetailsService est annotée avec @Primary. Cette classe CustomUserDetailsService est annotée avec @RequiredArgsConstructor. Cette classe CustomUserDetailsService est annotée avec @Slf4j. Cette classe CustomUserDetailsService est annotée avec @Transactional.    


### Commande pour lancer consul

`docker run -d --name=dev-consul -p 8500:8500 -e CONSUL_BIND_INTERFACE=eth0 consul`

### Dépendances à ajouter dans le pom.xml de chaque service

**Consul**

    <dependency>
          <groupId>org.springframework.cloud</groupId>
          <artifactId>spring-cloud-starter-consul-discovery</artifactId>
    </dependency>

**Actuator**

    <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>

### Commandes environnement de développement

**Serveur de base de données PostgresSQL**

```bash 
# Récupérer l'image docker de la base de données
docker pull postgres

# Lancer le serveur de base de données
docker run --name postgres -e POSTGRES_PASSWORD=postgres -d -p 5432:5432 postgres

# Vérifier que le serveur de base de données est bien lancé
docker ps

# Se connecter au serveur de base de données
docker exec -it postgres psql -U postgres

# Créer la base de données
CREATE DATABASE postgresbd;

# Créer un utilisateur
CREATE USER user WITH ENCRYPTED PASSWORD 'mdp';

# Donner les droits à l'utilisateur
GRANT ALL PRIVILEGES ON DATABASE postgresbd TO user;

# Quitter le serveur de base de données
\q

# debug docker socket
sudo systemctl restart docker.socket docker.service
docker rm -f <container id>
````

### Ordre de lancement

* consul
* gateway
* auth-service
* pileouface-service
    
### Pour tester les services

Exectuer les deux fichiers de test "requetes.http" et "requetes-sans-gateway.http"

### Backlog fonctionnel du service

    * [ ] creer un compte
    * [ ] se connecter
    * [ ] se deconnecter
    * [ ] recuperer un token
    * [ ] recuperer les infos de l'utilisateur
    * [ ] recuperer les roles de l'utilisateur

### Backlog technique du service

    * [ ] classe de configuration
    * [ ] accès à la base de données
    * [ ] acces token
    * [ ] refresh token recommandé pour les applications mobiles et openId connect
    * [ ] scope
    * [ ] OpenAPI (swagger)
    * [ ] OpenAPI (springdoc)
    * [ ] OpenID Connect (OIDC)
    * [ ] 

### exemmples tableaux (à compléter)

| colonne 1 | colonne 2 | colonne 3 |
|-----------|-----------|-----------|
|           |           |           |
|           |           |           |
|           |           |           |


### exemples images

    ![image](https://user-images.githubusercontent.com/59559312/116824001-1b1b4a00-ab7f-11eb-9b9a-1b8b8b8b8b8b.png)

### exemples liens

    [lien](
    ************)
    
    [lien](

### exemples code

    ```java 

    // code java

    ```

    ```javascript

    // code javascript

    ```

    ```python

### exemples citations

    > citation
    
    > citation

