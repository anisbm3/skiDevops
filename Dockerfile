FROM openjdk:8-jdk-alpine
EXPOSE 8089
ADD http://192.167.33.10:8081/repository/maven-releases/tn/esprit/spring/saifMeddeb4twin2/1.0/saifMeddeb4twin2-1.0.jar saifmeddeb4twin2skidevops-1.0.jar
ENTRYPOINT ["java","-jar","/saifmeddeb4twin2skidevops-1.0.jar"]
