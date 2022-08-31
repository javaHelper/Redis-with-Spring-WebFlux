# Redis-with-Spring-WebFlux

We would be discussing more on this in the following lectures. Please PREFER DOCKER for setting up Redis.

# Docker Users:

1. Save this docker-compose.yml

```
version: '3'
services:
  redis:
    container_name: redis
    hostname: redis
    image: redis:6.2
    ports:
    - 6379:6379
```    
    
2. Run this command. docker-compose up

3. Launch a separate terminal to access redis-cli

`docker exec -it redis bash`


# Mac User (who does not want to use docker):

- Run below commands
```
brew update
brew install redis
```
- Launch 2 terminals.
- Run this command in terminal 1 redis-server
- Run this command in terminal 2 redis-cli

# Windows User (who does not want to use docker):

Redis does not support direct windows installation. There is a workaround. Redis team has created an official windows redis installation steps in a video which is available in Youtube.

Redis Windows Install -  https://www.youtube.com/watch?v=1psWME8UH_0
