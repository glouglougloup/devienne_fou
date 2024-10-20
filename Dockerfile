FROM alpine/java:21-jdk
ARG JAR_FILE=target/*.jar
COPY ./target/devienne-fou-weekly-check-0.0.1-SNAPSHOT.jar devienne_fou.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/devienne_fou.jar"]