
## Service notification

Le service notification permet de notifier les utilisateurs par email ou par sms.

### Backlog fonctionnel
- [x] Envoi d'une notification par e-mail
- [ ] Envoi d'une notification par e-mail avec une pièce jointe
- [ ] Envoi d'une notification par SMS

**Notifications déclenchées par les événements des autres services :**

_Service authentification :_
- [ ] Envoi d'une notification après la création d'un compte utilisateur pour confirmer l'inscription
- [ ] Envoi d'une notification après la modification du mot de passe

_Service gestion des rendez-vous :_
- [ ] Envoi d'une notification après la réservation d'un rendez-vous
- [ ] Envoi d'une notification pour rappeler un rendez-vous
- [ ] Envoi d'une notification après la modification d'un rendez-vous
- [ ] Envoi d'une notification après la suppression d'un rendez-vous

_Service gestion de stock :_
- [ ] Envoi d'une notification alerte de stock bas
- [ ] Envoi d'une notification alerte de stock épuisé

### Backlog technique 

- [x] Mise en place de la couche de présentation : Spring Web
- [x] Mise en place de la couche de persistance : Spring Data JPA
- [x] Mise en place de la librairie d'envoi d'e-mails : Java Mail Sender
- [x] Mise en place d'une base de données : PostgresSQL ~~ou MongoDB~~
- [x] Mise en place d'un serveur annuaire de services : Consul
- [x] Mise en place d'un serveur de surveillance de l'état de santé des services : Spring Boot Actuator
- [x] Mise en place d'un serveur d'écoute des événements des autres services : RabbitMQ 
- [ ] Mise en place d'un serveur de configuration : Spring Cloud Config
- [ ] Mise en place d'un serveur de monitoring : Spring Boot Admin

- [x] Stocker les notifications dans la base de données

- [ ] Gestion des notifications par type (email, notif, ~~sms~~)
- [ ] Gestion des notifications par statut (envoyée, en attente, erreur)
- [ ] Gestion des notifications par contenu (texte, html, json, xml)
- [ ] Gestion des notifications par destinataire

### Technologies utilisées

- Java 17
- Spring Boot 3.0.2


### Dépendances du service

- Spring Web : permet de créer des applications web
- Spring Data JPA 
- Lombok : permet de générer automatiquement des getters, setters, constructeurs, toString, hashCode, equals
- Postgres Driver Database
- ~~MongoDB Driver Database~~
- Java Mail Sender : permet d'envoyer des emails

### API Design - Tableau des URIs

| Méthode | URI                     | Description                                  |
|:--------|-------------------------|----------------------------------------------|
| POST    | /notifications/email    | Envoyer une notification par e-mail          |
| POST    | /notifications/sms      | Envoyer une notification par SMS             |
| GET     | /notifications/{id}     | Obtenir une notification par son identifiant |
| GET     | /notifications          | Obtenir la liste des notifications           |
| PUT     | /notifications/{id}     | Modifier une notification                    |
| DELETE  | /notifications/{id}     | Supprimer une notification                   |
| GET     | /notifications?status={status}&type={type}	| Rechercher des notifications par statut et/ou type |

[//]: # (| GET     | /notifications/health   | Obtenir le status de la base de données      |)

[//]: # (| GET     | /notifications/info     | Obtenir les informations du service          |)

[//]: # (| GET     | /notifications/env      | Obtenir les variables d'environnement        |)

[//]: # (| GET     | /notifications/metrics  | Obtenir les métriques du service             |)

[//]: # (| GET     | /notifications/trace    | Obtenir les traces du service                |)

[//]: # (| GET     | /notifications/refresh  | Rafraîchir les configurations                |)


### API Design - Détails des URIs


### Ressources documentaires

````
Gestion des mails : https://www.cdg61.fr/extranet/file_manager/2675.pdf

Durée conservation des données : https://www.cnil.fr/fr/les-durees-de-conservation-des-donnees
````
### Idées fonctionnalités supplémentaires

**Notification**
- Newsletter
- Politique de confidentialité
- Archivage et suppression des notifications après un certain temps
- Statistiques
- Spécifier le type de notification (email, sms, notif)
- Spécifier le statut de la notification (envoyée, en attente, erreur)
- Spécifier le type de contenu (texte, html, json, xml)
- Spam protection
- Gestion des templates
- Gestion des destinataires

**Monitoring**
- Gestion des logs
- Gestion des métriques
- Gestion des traces
- Gestion des configurations
- Gestion des variables d'environnement

**Authentification et Sécurité**
- Gestion des profils
- Gestion des utilisateurs
- Gestion des rôles
- Gestion des permissions
- Gestion des tokens
- Gestion des sessions
- Gestion des cookies
- Gestion des CORS
- Gestion des secrets
- Gestion des certificats


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

# Afficher les bases de données
\l

# Sélectionner une base de données
\c postgresbd

# Afficher la liste des tables de la base de données
\dt

# Afficher la description d'une table
\d <table name>

# Afficher les données d'une table
SELECT * FROM <table name>;

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


### Méthodes de déploiement

**Méthode 1 : Docker**

```bash
docker build -t notifications-service .
docker run -p 8080:8080 notifications-service
```

**Méthode 2 : Docker Compose**
    
```bash 
docker-compose up
```

**Méthode 3 : Kubernetes**



### Mémo commandes git

```bash
# create a new repository on the command line :

echo "# micro-services" >> README.md
git init
git add README.md
git commit -m "first commit"
git branch -M main
git remote add origin https://github.com/rachy-da/micro-services.git
git notif -u origin main

#notif an existing repository from the command line :
git remote add origin https://github.com/rachy-da/micro-services.git
git branch -M main
git notif -u origin main
git notif origin master

# create a new branch :
git checkout -b feature_x 
git branch feature_x

# switch to another branch :
git checkout master
git switch feature_x

# remove a branch :
git branch -d feature_x
 
# notif a branch to remote :
git notif origin feature_x
git notif --set-upstream origin feature_x

# renommer une branche
git branch -m feature_x feature_y 

# annuler le dernier commit 
git reset HEAD~1

# afficher l'historique des commits
git log

```

### Diagramme de cas d'utilisation

à venir

### Diagramme de séquence

à venir

### Diagramme de classe

à venir

### Diagramme de déploiement

à venir