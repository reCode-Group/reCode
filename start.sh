#!/bin/bash

# Pull new changes
git pull

# Prepare Jar
mvn clean
mvn package

# Ensure, that docker-compose stopped
docker-compose stop
docker stop my

# Start new deployment


#docker-compose up --build -d
docker build -t my .
docker run -d -p 8080:3030 my


