#!/bin/bash

# commande pour construire une image docker pour chaque service

for dir in */ ; do
    if [ -e "$dir/pom.xml" ]; then
        echo "Building image for $dir"
        cd "$dir"
        mvn spring-boot:build-image -DskipTests
        cd ..
    fi
done

#
#cd service-gestion-rdv-patients-personnel
#mvn spring-boot:build-image -DskipTests
# commande pour construire et executer les containers docker

docker compose build
docker compose up



# ancienne version :

# commande pour générer le fichier .jar de chaque service en ignorant les tests
#./mvn clean package -DskipTests
#mvn clean package -DskipTests
# commande pour construire une image docker en ignorant les tests
#mvn spring-boot:build-image -DskipTests
