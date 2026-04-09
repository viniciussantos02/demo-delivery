FROM maven:3.9.6-openjdk-21-slim AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:21-jre-slim

COPY --from=build /app/target/*.jar app.jar

EXPOSE 9999

ENTRYPOINT ["java","-jar","app.jar"]
