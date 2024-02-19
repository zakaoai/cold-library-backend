FROM gradle:8.4.0-jdk17-alpine AS cache
RUN mkdir -p /home/gradle/cache_home
ENV GRADLE_USER_HOME /home/gradle/cache_home
COPY build.gradle.kts /home/gradle/java-code/
COPY settings.gradle.kts /home/gradle/java-code/
WORKDIR /home/gradle/java-code
RUN gradle clean build -i --stacktrace -x bootJar

FROM gradle:8.4.0-jdk17-alpine AS build
COPY --from=cache /home/gradle/cache_home /home/gradle/.gradle
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build -i --stacktrace

FROM eclipse-temurin:17-jdk-alpine

EXPOSE 9000

ARG JAR_FILE="/home/gradle/src/build/libs/*.jar"
COPY --from=build ${JAR_FILE} /app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
