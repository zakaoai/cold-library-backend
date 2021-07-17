FROM maven:3.8.1-jdk-11 AS MAVEN_BUILD

MAINTAINER Zakaoai

COPY pom.xml /build/
COPY src /build/src/

WORKDIR /build/
RUN mvn package

FROM adoptopenjdk/openjdk11:alpine-jre


ARG JAR_FILE="/build/target/*.jar"
COPY --from=MAVEN_BUILD ${JAR_FILE} /app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
