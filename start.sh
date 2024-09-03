#!/bin/bash

# Pull new changes
git pull

# Prepare Jar
mvn clean
mvn package

# Ensure, that docker-compose stopped
docker stop my
docker rm my

# docker rmi $(sudo docker images -q) -f   #удаление всех образов

# Start new deployment
#docker-compose up --build -d




docker build -t my .
docker run -d -p 3030:443 my


