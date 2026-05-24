FROM maven:3.8.8-amazoncorretto-17 AS BUILD
WORKDIR /app
COPY . .
RUN mvn clean install -DskipTest

FROM amazoncorretto:17-alpine
WORKDIR /app
COPY --from=build app/target/*.jar app.jar
EXPOSE 8085
CMD ["java", "-jar", "/app/app.jar"]