#!/bin/bash
# variable d'environnement pour le token de vault
export VAULT_TOKEN="token45"

docker rm -f service-auth
docker rmi -f projet-hopital/service-authentification:latest
#
#docker rm -f config-serveur
#docker rmi -f projet-hopital/config-serveur:latest

# shellcheck disable=SC2164
cd service-authentification
mvn clean install
mvn spring-boot:build-image

#cd ..
#cd config-serveur || exit
#mvn clean install
#mvn spring-boot:build-image

#docker compose up config-serveur
docker compose up service-auth





#docker compose up -d db-postgres-auth
#docker compose up -d db-postgres-notif
#docker compose up -d db-mongo-stockfournisseurs
#docker compose up -d db-mongo-rdv-patients
#docker compose up -d db-mongo-comptable
#
#
#docker compose up -d discovery
#docker compose up -d rabbitmq