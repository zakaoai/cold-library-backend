FROM gradle:8.4.0-jdk17-alpine AS build

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM eclipse-temurin:17-jdk-alpine

EXPOSE 9000

ARG JAR_FILE="/home/gradle/src/build/libs/*.jar"
COPY --from=build ${JAR_FILE} /app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
