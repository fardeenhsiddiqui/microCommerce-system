FROM eclipse-temurin:17-jre

WORKDIR /app

COPY build/libs/discovery-server-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8761

ENTRYPOINT ["java","-jar","app.jar"]