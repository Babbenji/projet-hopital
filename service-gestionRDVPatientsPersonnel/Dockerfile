FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"] CMD ["-start"]

FROM mongo:latest
COPY data/ /data/
CMD mongod --fork --logpath /var/log/mongodb.log; \
    mongorestore /data/rdvpatients/; \
    mongod --shutdown; \
    docker-entrypoint.sh mongod