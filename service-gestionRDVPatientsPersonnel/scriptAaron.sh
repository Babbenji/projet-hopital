docker system prune -a -f
docker volume rm $(docker volume ls -q)
docker pull mongo:latest
mvn clean install
#docker run -d -p 27017:27017 --name service-gestionrdvpatientspersonnel_mongo_db_1 mongo:latest
docker build -t springboot-mongodb-rdvpatients:1.0 .
#docker run -p 8080:8080 --name springboot-mongodb --link service-gestionrdvpatientspersonnel_mongo_db_1:mongo -d springboot-mongodb:1.0
#docker stop springboot-mongodb
#docker rm springboot-mongodb
#docker stop service-gestionrdvpatientspersonnel_mongo_db_1
#docker rm service-gestionrdvpatientspersonnel_mongo_db_1
docker-compose up