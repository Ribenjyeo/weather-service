FROM eclipse-temurin:17.0.8.1_1-jdk

LABEL Description="Weather-Service"

WORKDIR /app

COPY ../build/libs/weather-service-1.0.0.jar /app/weather-service.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app/weather-service.jar"]
