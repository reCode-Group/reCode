#!/bin/bash

# Pull new changes
git pull

# Prepare Jar
mvn clean
mvn package

# Ensure, that docker-compose stopped
docker stop recode-app
# docker rmi $(sudo docker images -q) -f   #удаление всех образов
# Start new deployment
#docker-compose up --build -d


docker build -t recode-app .
docker run -d -p 443:443 -t recode-app


