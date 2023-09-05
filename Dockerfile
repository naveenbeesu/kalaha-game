FROM openjdk:17-oracle
LABEL authors="nbeesu"
COPY target/kalaha-game.jar kalaha-game.jar

ENTRYPOINT ["java", "-jar", "/kalaha-game.jar"]

EXPOSE 8081