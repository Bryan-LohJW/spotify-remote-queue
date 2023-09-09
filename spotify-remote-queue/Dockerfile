FROM openjdk:17-alpine

MAINTAINER bryan loh

COPY target/spotify-remote-queue-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar", "/app.jar"]