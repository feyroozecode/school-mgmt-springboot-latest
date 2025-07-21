FROM openjdk:17-jdk-slim

WORKDIR /app

COPY pom.xml ./

RUN apt-get update && apt-get install -y maven

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean install -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/school-mgmt-springboot-0.0.1-SNAPSHOT.jar"]

