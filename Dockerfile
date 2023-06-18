FROM openjdk:11-jre-slim
EXPOSE 8080

WORKDIR /app
COPY target/mentway-0.0.1-SNAPSHOT.jar /app/myapp.jar
ENTRYPOINT ["java", "-jar", "myapp.jar"]
