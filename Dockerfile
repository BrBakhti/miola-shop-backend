FROM gradle:8.4-jdk17-alpine AS build
WORKDIR /app
COPY . .
RUN gradle clean bootJar -x test

FROM eclipse-temurin:17-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
