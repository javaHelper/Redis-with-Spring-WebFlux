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


```
prateekashtikar@Prateeks-MacBook-Pro Redis-with-Spring-WebFlux % ls
README.md		docker-compose.yml
prateekashtikar@Prateeks-MacBook-Pro Redis-with-Spring-WebFlux % docker-compose up -d 
[+] Running 7/7
 ⠿ redis Pulled                                                                                                                                        19.8s
   ⠿ 5b1423465504 Pull complete                                                                                                                        11.9s
   ⠿ 4216a986e3df Pull complete                                                                                                                        12.0s
   ⠿ f74254280149 Pull complete                                                                                                                        12.1s
   ⠿ 1bc2dce56d35 Pull complete                                                                                                                        12.4s
   ⠿ 391d2a4c9e19 Pull complete                                                                                                                        12.5s
   ⠿ 044896002e52 Pull complete                                                                                                                        12.5s
[+] Running 2/2
 ⠿ Network redis-with-spring-webflux_default  Created                                                                                                   0.0s
 ⠿ Container redis                            Started                                                                                                   0.5s
prateekashtikar@Prateeks-MacBook-Pro Redis-with-Spring-WebFlux % docker exec -it redis bash
root@redis:/data# redis-cli
127.0.0.1:6379> ping
PONG
127.0.0.1:6379>
```


`config set notify-keyspace-events AKE`

# Access Redis from within docker

```
prateekashtikar@Prateeks-MacBook-Pro Documents % docker exec -it redis bash
root@redis:/data# redis-cli 
127.0.0.1:6379> ping 
PONG
127.0.0.1:6379> 
```

# Some Operations

```
127.0.0.1:6379> set a b 
OK
127.0.0.1:6379> get a
"b"
127.0.0.1:6379> get c
(nil)
127.0.0.1:6379> set a 1
OK
127.0.0.1:6379> get a
"1"
127.0.0.1:6379> set a 100.23
OK
127.0.0.1:6379> get a
"100.23"
127.0.0.1:6379> set a .....................big......................
OK
127.0.0.1:6379> get a
".....................big......................"
127.0.0.1:6379> set ............big............ somevalue
OK
127.0.0.1:6379> get ............big............
"somevalue"
127.0.0.1:6379> set 1 10
OK
127.0.0.1:6379> get 1
"10"
127.0.0.1:6379> 
127.0.0.1:6379> set true false
OK
127.0.0.1:6379> get true
"false"
127.0.0.1:6379> get Truw
(nil)
127.0.0.1:6379> get True
(nil)
127.0.0.1:6379>
127.0.0.1:6379> set . -
OK
127.0.0.1:6379> get .
"-"
127.0.0.1:6379>
```

# Best Practice

```

```



