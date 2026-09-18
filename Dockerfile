FROM eclipse-temurin:26-jdk
COPY ./target/classes/com /tmp/com
WORKDIR /tmp
ENTRYPOINT ["java", "com.napier.group7.Main"]