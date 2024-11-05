#TODO add app compilation on server side (removing generated files from vcs)
#FROM eclipse-temurin:17-jdk-alpine
##RUN apk add --no-cache nodejs npm
##RUN npm install -g prettier
#
#ARG JAR_FILE=target/*.jar
#VOLUME /tmp
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]
#EXPOSE 8080

FROM maven:3.8.6-eclipse-temurin-17 as builder
WORKDIR /opt/app
COPY ./src ./src
RUN mvn clean install -DskipTests

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /opt/app
EXPOSE 8080
COPY --from=builder /opt/app/target*.jar /opt/app/*.jar
ENTRYPOINT ["java","-jar","opt/app/*.jar"]