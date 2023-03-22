FROM maven:3.8.5-openjdk-17 AS MAVEN_BUILD

MAINTAINER Zakaoai

COPY pom.xml /build/
COPY src /build/src/

WORKDIR /build/
RUN mvn package

FROM eclipse-temurin:17-jdk-alpine


ARG JAR_FILE="/build/target/*.jar"
COPY --from=MAVEN_BUILD ${JAR_FILE} /app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
