
```bash

# commande pour allumer tous les conteurs Docker :

docker start $(docker ps -aq)

#commande pour stopper tous les conteneurs Docker instantanément :

docker stop $(docker ps -q)

#pour supprimer tous les ressources Docker non utilisés (conteneur,volumes,images) :

docker system prune -a

#pour afficher les connexions réseaux associés à un port :

lsof -i :5432

#Si l'adresse est déjà utilisé :

sudo kill -9 $(sudo lsof -t -i:8080)

``̀` 
    