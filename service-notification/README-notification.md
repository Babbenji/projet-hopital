
## Service notification

Le service notification permet de notifier les utilisateurs par email ou par sms.



### Dépendances du service

- Spring Web : permet de créer des applications web
- Spring Data JPA :
- H2 Database
- Lombok : permet de générer automatiquement des getters, setters, constructeurs, toString, hashCode, equals
- Postgres Driver Database
- MongoDB Driver Database
- Java Mail Sender : permet d'envoyer des emails

### API Design - Tableau des URIs

| Méthode | URI                     | Description                                  |
|:--------|-------------------------|----------------------------------------------|
| POST    | /notifications/email    | Envoyer une notification par e-mail          |
| POST    | /notifications/sms      | Envoyer une notification par SMS             |
| POST    | /notifications/push     | Envoyer une notification push                |
| POST    | /notifications/teams    | Envoyer une notification par Teams           |
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


### Commandes git


