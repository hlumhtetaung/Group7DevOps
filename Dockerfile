FROM eclipse-temurin:26-jdk
COPY ./target/group7-1.0-SNAPSHOT-jar-with-dependencies.jar /tmp/
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "group7-1.0-SNAPSHOT-jar-with-dependencies.jar"]