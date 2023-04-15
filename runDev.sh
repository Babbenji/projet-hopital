#!/bin/bash


docker rm -f service-auth
docker rmi -f projet-hopital/service-authentification

# shellcheck disable=SC2164
cd service-authentification
mvn clean install
mvn spring-boot:build-image

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