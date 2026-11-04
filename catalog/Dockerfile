FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/catalog-0.0.1-SNAPSHOT.jar /app/catalog-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "catalog-0.0.1-SNAPSHOT.jar"]