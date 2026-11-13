FROM eclipse-temurin:21-jdk
COPY ./target/group7-1.0-SNAPSHOT-jar-with-dependencies.jar /tmp/app.jar
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "app.jar"]