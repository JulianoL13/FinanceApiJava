FROM maven:3.8.6-openjdk-17 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app
COPY --from=build /app/target/finance-api-spring-*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
