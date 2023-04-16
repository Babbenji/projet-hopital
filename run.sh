#!/bin/bash


# commande pour construire une image docker de chaque service spring boot

for dir in */ ; do
    if [ -e "$dir/pom.xml" ]; then
        echo "Building image for $dir"
        cd "$dir"
        mvn spring-boot:build-image -DskipTests
        cd ..
    fi
done


# commande pour construite l'image docker du service .NET
docker build -t service-comptable:1.0 ./service-facturation


# variable d'environnement
export VAULT_TOKEN="token45"


# commande pour construire et executer les containers docker
docker compose build
docker compose up





# ancienne version :

#
#cd service-gestion-rdv-patients-personnel
#mvn spring-boot:build-image -DskipTests

# commande pour générer le fichier .jar de chaque service en ignorant les tests
#./mvn clean package -DskipTests
#mvn clean package -DskipTests
# commande pour construire une image docker en ignorant les tests
#mvn spring-boot:build-image -DskipTests

