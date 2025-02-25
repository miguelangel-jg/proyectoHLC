FROM maven:3-eclipse-temurin-17 AS build 
WORKDIR /app 
COPY . /app
WORKDIR /app 
RUN mvn clean package -DskipTests 

FROM eclipse-temurin:17-alpine 
WORKDIR /app 
COPY --from=build /app/target/*.jar app.jar 
CMD ["java", "-jar", "app.jar"]