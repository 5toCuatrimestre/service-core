FROM openjdk:21-jdk-slim
ARG JAR_FILE=target/service-core-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} service-core.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "service-core.jar"]