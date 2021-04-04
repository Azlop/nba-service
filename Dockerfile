FROM openjdk:11-jre-slim

COPY target/nba-service-0.0.1-SNAPSHOT.jar nba-service-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/nba-service-0.0.1-SNAPSHOT.jar"]