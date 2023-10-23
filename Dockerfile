FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /usr/app
COPY . .
RUN mvn clean compile assembly:single

#
# Package stage
#
FROM openjdk:17-oracle
ARG JAR_FILE=/usr/app/target/*.jar
COPY --from=build $JAR_FILE /app/runner.jar
ENTRYPOINT ["java", "-jar", "/app/runner.jar"]