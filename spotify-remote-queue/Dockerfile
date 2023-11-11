FROM openjdk:17-alpine

MAINTAINER bryan loh

COPY target/spotify-remote-queue-2.0.1.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar", "/app.jar"]