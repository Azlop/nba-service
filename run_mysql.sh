#!/bin/bash
docker run --rm -d -e MYSQL_ROOT_PASSWORD=games -e MYSQL_DATABASE=games -e MYSQL_USER=games -e MYSQL_PASSWORD=games -p 3306:3306 --name mysql mysql:8.0.23
