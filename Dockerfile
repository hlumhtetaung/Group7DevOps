# Build the JAR with Maven
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /tmp
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Create runtime container
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /tmp/target/group7-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]