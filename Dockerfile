FROM openjdk:11
ADD target/detector-0.0.1-SNAPSHOT.jar detector-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "detector-0.0.1-SNAPSHOT.jar"]