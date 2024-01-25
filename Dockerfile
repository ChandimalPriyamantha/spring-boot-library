FROM eclipse-temurin:17-jdk-alpine
EXPOSE 8080
ADD target/spring-boot-library.jar spring-boot-library.jar
ENTRYPOINT ["java","-jar","/spring-boot-library.jar"]