FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ /app/src/
RUN mvn package -DskipTests

FROM ibm-semeru-runtimes:open-17-jdk
EXPOSE 8080

WORKDIR /app
COPY --from=build /app/target/mentway-0.0.1-SNAPSHOT.jar /app/myapp.jar
ENTRYPOINT ["java", "-jar", "myapp.jar"]