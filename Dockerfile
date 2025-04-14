FROM openjdk:8-jdk-alpine
ARG BUILD_NUMBER
ADD target/gestion-station-ski-${BUILD_NUMBER}.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
