FROM openjdk:11
COPY build/libs/AlfaTest-0.0.1-SNAPSHOT.jar AlfaTest.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "AlfaTest.jar"]