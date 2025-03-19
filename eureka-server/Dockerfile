FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY build/libs/eureka-server-0.0.1-SNAPSHOT.jar /app/eureka-server.jar
ENTRYPOINT ["java","-jar","/app/eureka-server.jar"]