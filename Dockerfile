# ---------- BUILD STAGE ----------
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN ./mvnw clean package -DskipTests

# ---------- RUNTIME STAGE ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=builder /app/target/*SNAPSHOT.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
