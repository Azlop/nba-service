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

Create container with Docker Compose:
```shell
docker-compose up --no-start
```


