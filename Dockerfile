FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /usr/app
COPY . .
RUN mvn clean compile assembly:single

#
# Package stage
#
FROM openjdk:17-oracle
ARG JAR_FILE=/usr/app/target/*.jar
WORKDIR /app
COPY .env .
COPY --from=build $JAR_FILE runner.jar
ENTRYPOINT ["java", "-jar", "runner.jar"]
