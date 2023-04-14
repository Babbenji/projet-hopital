#!/bin/bash

docker compose down --rmi all --volumes --remove-orphans

docker system prune -a -f
#
#docker volume rm $(docker volume ls -q)


# Stop and remove containers starting with "tp"
#docker ps -a | grep 'tp' | awk '{print $1}' | xargs docker stop | xargs docker rm


