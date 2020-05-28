FROM openjdk:11.0.7-jre-slim-buster
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.2.1/wait /wait
RUN chmod +x /wait
