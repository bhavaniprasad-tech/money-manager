FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/moneymanager-v2-0.0.1-SNAPSHOT.jar moneymanager-v1.0.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "moneymanager-v1.0.jar"]
