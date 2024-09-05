#TODO add app compilation on server side (removing generated files from vcs)
FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE=target/*.jar
VOLUME /tmp
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 443
