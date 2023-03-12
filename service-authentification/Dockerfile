FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} service-authentification.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/service-authentification.jar"]