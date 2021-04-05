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

Run nba-service:
```shell
docker-compose start nba-service
```

Get logs of nba-service:
```shell
docker-compose logs -f nba-service
```

You can use postman to make requests to the container:
```
http://localhost:8080/games/264402
http://localhost:8080/games?date=2021-03-28
```