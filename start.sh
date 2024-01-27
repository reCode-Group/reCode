#!/bin/bash

# Pull new changes
git pull

# Prepare Jar
mvn clean
mvn package

# Ensure, that docker-compose stopped
docker-compose stop

# Start new deployment


#docker-compose up --build -d
docker build -t my .
docker run -d -p 8080:8080 my


