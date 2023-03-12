
# commande pour générer le fichier .jar de chaque service
./mvn clean package -DskipTests

# commande pour executer le docker-compose.yml

docker compose build
docker compose up

