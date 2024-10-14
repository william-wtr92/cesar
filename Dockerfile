FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build/libs/cesar-0.0.1-SNAPSHOT.jar /app/cesar-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/cesar-0.0.1-SNAPSHOT.jar"]