FROM eclipse-temurin:17-jre

WORKDIR /app

COPY build/libs/notificationService-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java","-jar","app.jar"]