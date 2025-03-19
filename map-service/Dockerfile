FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY build/libs/map-service-0.0.1-SNAPSHOT.jar /app/map-service.jar
ENTRYPOINT ["java", "-jar", "/app/map-service.jar"]