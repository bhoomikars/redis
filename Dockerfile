
FROM openjdk:8-jre
ADD target/redis-swagger-0.0.8-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar","--spring.config.location=/config/application.prod.properties"]