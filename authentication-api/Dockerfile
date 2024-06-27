FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-alpine
COPY --from=build /app/target/authentication-1.0.0.jar authentication-1.0.0.jar
ENTRYPOINT ["java", "-jar", "/authentication-1.0.0.jar"]