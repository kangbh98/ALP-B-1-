FROM openjdk:17-jdk
WORKDIR /app

# 애플리케이션 JAR 파일을 복사
COPY target/KtTrip-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
