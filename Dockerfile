FROM openjdk:8-jdk-alpine
ADD target/gestion-station-ski-1.0.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
