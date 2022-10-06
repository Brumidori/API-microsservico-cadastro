FROM openjdk:8-jre-slim-buster
COPY target/capgemini.start-1.0.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
