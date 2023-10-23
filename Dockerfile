FROM openjdk:17-oracle AS build
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD . $HOME
RUN --mount=type=cache,target=/root/.m2/ ./mvnw -f $HOME/pom.xml clean package

#
# Package stage
#
FROM openjdk:17-oracle
ARG JAR_FILE=/usr/app/target/*.jar
COPY --from=build $JAR_FILE /app/runner.jar
ENTRYPOINT ["java", "-jar", "/app/runner.jar"]