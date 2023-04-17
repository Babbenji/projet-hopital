#!/bin/bash
export VAULT_TOKEN="token45"

# commande pour construire une image docker de chaque service spring boot
for dir in */ ; do
    if [ -e "$dir/pom.xml" ]; then
        echo "Building image for $dir"
        cd "$dir"
        mvn spring-boot:build-image -DskipTests
        cd ..
    fi
done

# variable d'environnement pour le token de vault

# commande pour construite l'image docker du service .NET
docker build -t service-comptable:1.0 ./service-facturation

# commande pour construire et executer les containers docker
docker compose build
docker compose up


