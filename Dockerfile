FROM openjdk:8-jdk-alpine
COPY target/*.jar patientApi-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/patientApi-1.0-SNAPSHOT.jar"]