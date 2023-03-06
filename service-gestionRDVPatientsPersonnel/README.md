
## Service notification

Le service notification permet de notifier les utilisateurs par email ou par sms.

### Backlog fonctionnel
- [x] A
- [ ] B

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

### Technologies utilisées

### Dépendances du service

### API Design - Tableau des URIs

| Méthode | URI                                                           | Description                                                                           |
|:--------|---------------------------------------------------------------|---------------------------------------------------------------------------------------|
| POST    | /rdvpatients/medecin/nouveau                                  | Créer un nouvel utilisateur de type médecin                                           |
| POST    | /rdvpatients/patient/nouveau                                  | Créer un nouvel utilisateur de type patient                                           |
| PATCH   | /rdvpatients/personnel/modifpatient/{numSecu}/antecedents     | Modifier les antécédents d'un patient                                                 |
| PATCH   | /rdvpatients/personnel/modifpatient/{numSecu}/medecintraitant | Modifier le médecin traitant d'un patient                                             |
| PUT     | /rdvpatients/consultation/{id}/confirmer                      | Confirmer une consultation (notification)                                             |
| GET     | /rdvpatients/medecin/{id}/consultations                       | Voir les consultations d'un medecin                                                   |
| PUT     | /rdvpatients/consultation/{id}/compterendu	                   | Modifier le compte-rendu d'une consultation                                           |
| DELETE  | /rdvpatients/consultation/{id}/annuler	                       | Annuler une consultation (notification)                                               |
| POST    | /rdvpatients/consultation/nouveau	                            | Créer une nouvelle consultation (notification)                                        |
| POST    | /rdvpatients/consultation/{id}/demandeannulation	             | Le patient fait une demande d'annulation pour une de ses consultations (notification) |

### API Design - Détails des URIs


### Ressources documentaires

````

````

### Idées fonctionnalités supplémentaires

**Notification**

**Monitoring**

**Authentification et Sécurité**

### Commandes environnement de développement

```bash 
# Quitter le serveur de base de données
\q
````


### Mémo commandes git

```bash
# create a new repository on the command line :

echo "# micro-services" >> README.md
git init
git add README.md
git commit -m "first commit"
git branch -M main
git remote add origin https://github.com/rachy-da/micro-services.git
git push -u origin main

#push an existing repository from the command line :

git remote add origin https://github.com/rachy-da/micro-services.git
git branch -M main
git push -u origin main
git push origin master

# create a new branch :
git checkout -b feature_x 
git branch feature_x

# switch to another branch :
git checkout master
git switch feature_x

# remove a branch :
git branch -d feature_x
 
# push a branch to remote :
git push origin feature_x
git push --set-upstream origin feature_x

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

### Commandes 
