# nba-service
A service to fetch part of information from free NBA service.

## Run application
Create an artifact:
```shell
mvn clean install
```

Build docker image:
```shell
docker build -t nba-service .
```

Create containers with Docker Compose:
```shell
docker-compose up --no-start
```

Run mysql first:
```shell
docker-compose start mysql
```

Check if mysql is ready for connections
```shell
docker-compose logs -f mysql | grep "ready for connections"
```

Then, run nba-service:
```shell
docker-compose start nba-service
```

Get logs of nba-service:
```shell
docker-compose logs -f nba-service
```

## Interact with application
You can use postman to make requests to the container by importing the file: **nba-service.postman_collection.json**

Or, simply use curl:
```shell
curl -i -X GET http://localhost:8080/games?date=2021-03-28
curl -i -X GET http://localhost:8080/games/264402
curl -i -X POST http://localhost:8080/comments -d '{"gameId":264402,"text":"It was cool!"}"'
curl -i -X PATCH http://localhost:8080/comments/<comment id> -d '{"text":"It was bad!"}"'
curl -i -X DELETE http://localhost:8080/comments/<comment id>
```

Change log level (for instance, DEBUG, INFO):
```shell
curl -i -X GET http://localhost:8080/actuator/loggers/com.carta.nbaservice -d '{"configuredLevel": "<log level>"}'
```

## Known limitations
* Caching game information on a database only happens when listing games for a date.
* View a single game information does not cache on a database, so commenting a game only works after listing games to get the game IDs.