FROM maven:3.9.12-eclipse-temurin-25 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -q
COPY src ./src
RUN mvn clean package -DskipTests -q

FROM eclipse-temurin:25-jre
WORKDIR /app
EXPOSE 8080
COPY --from=build /app/target/catalogue-service-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]