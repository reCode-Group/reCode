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
WORKDIR /tmp
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /tmp
EXPOSE 443
COPY --from=builder /tmp/target*.jar /tmp/*.jar
ENTRYPOINT ["java","-jar","opt/app/*.jar"]