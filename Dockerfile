FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY target/oms-0.0.1-SNAPSHOT.jar app.jar

# JVM tuning
ENTRYPOINT ["java", "-Xms2g", "-Xmx4g", "-XX:+UseG1GC", "-jar", "app.jar"]