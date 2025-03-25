
FROM maven:3.8.1-openjdk-11 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests


FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /app/target/golfclub-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
