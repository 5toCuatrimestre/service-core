FROM openjdk:21-slim

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 5000

ENTRYPOINT ["java", "-jar", "app.jar"] 