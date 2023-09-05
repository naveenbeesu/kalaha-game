# kalaha-game
Kalaha game or Mancala game Rules

# Board Setup
Each of the two players has his six pits in front of him. To the right of the six pits, each player has a larger pit. At the start of the game, there are six stones in each of the six round pits .
Rules
# Game Play
The player who begins with the first move picks up all the stones in any of his own six pits, and sows the stones on to the right, one in each of the following pits, including his own big pit. No stones are put in the opponents' big pit. If the player's last stone lands in his own big pit, he gets another turn. This can be repeated several times before it's the other player's turn.
# Capturing Stones
During the game the pits are emptied on both sides. Always when the last stone lands in an own empty pit, the player captures his own stone and all stones in the opposite pit (the other player’s pit) and puts them in his own (big or little?) pit.
# The Game Ends
The game is over as soon as one of the sides runs out of stones. The player who still has stones in his pits keeps them and puts them in his big pit. The winner of the game is the player who has the most stones in his big pit.
You can also find some visual explanations of the game rules by running a Google Search for Mancala or Kalaha game.


## How to run this application?
- Do mvn clean install
- Run as Spring Boot application

- To view front end screen i.e. board, open below url in browser
http://localhost:8081/kalaha/
- to test rest endpoints directly
  - Import the attached postman collection and hit the endpoints
  - GET: http://localhost:8080/kalaha/startGame?id=1
  - POST: http://localhost:8081/kalaha/moveStones
- To view the swagger documentation
  - http://localhost:8081/swagger

## To Run as docker image
- To Run as Docker image Please refer this link -> https://www.baeldung.com/dockerizing-spring-boot-application
- Run the docker quick start terminal and it will be up in few minutes
- Move to the project directory i.e. kalaha-game
- Create Docker image --> docker build –t kalaha-game.jar
- To check docker image, type command --> docker image ls
- You should be able to see the docker image with name -> kalaha-game.jar
- To run the docker the image --> docker run -p 9090:8081 kalaha-game.jar
- Here, 9090 is docker host port(it might be different in your system) where our application will be running, and 8081 is our api’s exposed server port
- It should be up now
- We can access the docker image using port 9090 --> Post http://docker-ipaddress:9090/moveStone
- And also using api server port 8080 --> POST http://localhost:8080/moveStone
