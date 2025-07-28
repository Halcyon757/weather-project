FROM maven:3.9.11-ibm-semeru-17-noble AS build
WORKDIR /app
COPY pom.xml ./
RUN mvn dependency:resolve
COPY src/ src/
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17.0.15_6-jdk-noble
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
