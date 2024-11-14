#TODO add app compilation on server side (removing generated files from vcs)

FROM maven:3.8.6-eclipse-temurin-17 AS builder
WORKDIR /tmp
COPY mvnw pom.xml ./
COPY ./src ./src
RUN mvn package -DskipTests

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /tmp
EXPOSE 443
COPY --from=builder /tmp/target/*.jar /tmp/*.jar
ENTRYPOINT ["java","-jar","/tmp/*.jar"]