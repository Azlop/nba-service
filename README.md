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

Run nba-service:
```shell
docker-compose start nba-service
```

You can use postman to make requests to the container:
```
http://localhost:8080/games/<game-id>
http://localhost:8080/games?date=2021-03-28
```