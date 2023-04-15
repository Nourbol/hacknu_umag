FROM openjdk:17
ADD target/*.jar shop.jar
ENTRYPOINT ["java", "-jar", "shop.jar"]