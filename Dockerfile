FROM openjdk:11.0.7-jre-slim-buster
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
VOLUME configuration:/var/lib/load/config
ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.config.location=file:/var/lib/load/config/application-dev.yml"]