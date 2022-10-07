FROM openjdk:8-jre-slim-buster
COPY target/app.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
