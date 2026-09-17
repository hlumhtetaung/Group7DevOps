# Build the JAR with Maven and Temurin 26
FROM maven:3.9-eclipse-temurin-26 AS build
WORKDIR /tmp
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Create runtime container with Temurin 26 JRE
FROM eclipse-temurin:26-jre
WORKDIR /app
COPY --from=build /tmp/target/seMethods-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]