FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} service-notification.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/service-notification.jar"]