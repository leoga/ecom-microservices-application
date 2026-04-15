FROM amazoncorretto:26
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8889
ENTRYPOINT ["java", "-jar", "app.jar"]