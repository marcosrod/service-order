FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-alpine
COPY --from=build /app/target/serviceorder-1.0.0.jar serviceorder-1.0.0.jar
ENTRYPOINT ["java", "-jar", "/serviceorder-1.0.0.jar"]