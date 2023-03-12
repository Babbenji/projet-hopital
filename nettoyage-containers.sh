#!/bin/bash

docker compose down --rmi all

# Stop and remove containers starting with "tp"
#docker ps -a | grep 'tp' | awk '{print $1}' | xargs docker stop | xargs docker rm


