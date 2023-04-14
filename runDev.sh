#!/bin/bash

docker compose up -d db-postgres-auth
docker compose up -d db-postgres-notif
docker compose up -d db-mongo-stockfournisseurs
docker compose up -d db-mongo-rdv-patients
docker compose up -d db-mongo-facturation


docker compose up -d discovery
docker compose up -d rabbitmq