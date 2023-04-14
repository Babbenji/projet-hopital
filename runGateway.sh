mvn api-gateway:clean
mvn api-gateway:install
mvn spring-boot:build-image -DskipTests
docker-compose build
docker-compose up