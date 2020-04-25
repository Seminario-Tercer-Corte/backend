FROM adoptopenjdk/openjdk11:latest
VOLUME /tmp

ADD /target/api-0.0.1-SNAPSHOT.jar dockerdemo.jar
ENTRYPOINT ["java", "-jar", "dockerdemo.jar"]