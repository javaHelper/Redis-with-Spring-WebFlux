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

# Best Practice - Storing Simple Keys

```
127.0.0.1:6379> set user:1:name sam
OK
127.0.0.1:6379> set user:2:name jake
OK
127.0.0.1:6379> get user:1:name
"sam"
127.0.0.1:6379> get user:2:name
"jake"
127.0.0.1:6379>
127.0.0.1:6379> set somekey "some value"
OK
127.0.0.1:6379> get somekey
"some value"
127.0.0.1:6379> 
```

# Accessing All keys

```
127.0.0.1:6379> KEYS *
1) "user:!:name"
2) "true"
3) "."
4) "............big............"
5) "1"
6) "user:2:name"
7) "somekey"
8) "a"
9) "user:1:name"
127.0.0.1:6379> 

127.0.0.1:6379> KEYS user*
1) "user:!:name"
2) "user:2:name"
3) "user:1:name"
127.0.0.1:6379>
```

# scan command involves with pagination

```
127.0.0.1:6379> SCAN 0
1) "0"
2) 1) "true"
   2) "user:!:name"
   3) "user:2:name"
   4) "1"
   5) "a"
   6) "user:1:name"
   7) "."
   8) "............big............"
   9) "somekey"
127.0.0.1:6379>

# Create some keys for Pagination

127.0.0.1:6379> set user:1:name 1
OK
127.0.0.1:6379> set user:2:name 2
OK
127.0.0.1:6379> set user:3:name 3
OK
127.0.0.1:6379> set user:4:name 4
OK
127.0.0.1:6379> set user:5:name 5
OK
127.0.0.1:6379> set user:6:name 6
OK
127.0.0.1:6379> set user:7:name 7
OK
127.0.0.1:6379> set user:8:name 8
OK
127.0.0.1:6379> set user:9:name 9
OK
127.0.0.1:6379> set user:10:name 10
OK
127.0.0.1:6379> set user:11:name 11
OK
127.0.0.1:6379> set user:12:name 12
OK
127.0.0.1:6379> set user:13:name 13
OK
127.0.0.1:6379> set user:14:name 14
OK
127.0.0.1:6379> set user:15:name 15
OK
127.0.0.1:6379> set user:16:name 16
OK
127.0.0.1:6379> set user:17:name 17
OK
127.0.0.1:6379> set user:18:name 18
OK
127.0.0.1:6379> set user:19:name 19
OK
127.0.0.1:6379> set user:20:name 20
OK
127.0.0.1:6379> 

```

```
127.0.0.1:6379> scan 0
1) "22" -> Use this to get next set of keys
2)  1) "user:19:name"
    2) "true"
    3) "user:9:name"
    4) "user:20:name"
    5) "user:14:name"
    6) "user:10:name"
    7) "user:!:name"
    8) "user:2:name"
    9) "1"
   10) "user:12:name"
127.0.0.1:6379> scan 22
1) "3" -> Use this to get next set of keys
2)  1) "user:6:name"
    2) "user:13:name"
    3) "user:1:name"
    4) "a"
    5) "............big............"
    6) "."
    7) "user:15:name"
    8) "user:11:name"
    9) "somekey"
   10) "user:18:name"
   11) "user:7:name"
127.0.0.1:6379> scan 3
1) "0" -> Use this to get next set of keys
2) 1) "user:16:name"
   2) "user:3:name"
   3) "user:5:name"
   4) "user:17:name"
   5) "user:4:name"
   6) "user:8:name"
```



