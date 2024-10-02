#!/bin/bash

# Ensure, that docker-compose stopped
docker stop $(docker ps -q)

# Ensure, that the old application won't be deployed again.
