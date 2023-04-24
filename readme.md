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

# Use regular expression with scan command

```
127.0.0.1:6379> SCAN 0 MATCH user*
1) "22"
2) 1) "user:19:name"
   2) "user:9:name"
   3) "user:20:name"
   4) "user:14:name"
   5) "user:10:name"
   6) "user:!:name"
   7) "user:2:name"
   8) "user:12:name"
127.0.0.1:6379> SCAN 22 MATCH user*
1) "3"
2) 1) "user:6:name"
   2) "user:13:name"
   3) "user:1:name"
   4) "user:15:name"
   5) "user:11:name"
   6) "user:18:name"
   7) "user:7:name"
127.0.0.1:6379> SCAN 3 MATCH user*
1) "0"
2) 1) "user:16:name"
   2) "user:3:name"
   3) "user:5:name"
   4) "user:17:name"
   5) "user:4:name"
   6) "user:8:name"
127.0.0.1:6379> 
```

# If I want to see only 3

```
127.0.0.1:6379> SCAN 0 MATCH user* count 3
1) "24"
2) 1) "user:19:name"
   2) "user:9:name"
127.0.0.1:6379>
```

# Removing Keys

```
127.0.0.1:6379> keys user*
 1) "user:16:name"
 2) "user:3:name"
 3) "user:15:name"
 4) "user:12:name"
 5) "user:5:name"
 6) "user:9:name"
 7) "user:11:name"
 8) "user:13:name"
 9) "user:19:name"
10) "user:!:name"
11) "user:6:name"
12) "user:17:name"
13) "user:4:name"
14) "user:8:name"
15) "user:20:name"
16) "user:14:name"
17) "user:10:name"
18) "user:2:name"
19) "user:18:name"
20) "user:7:name"
21) "user:1:name"
127.0.0.1:6379> 

# For ex: remove user:8
127.0.0.1:6379> DEL user:8:name
(integer) 1
127.0.0.1:6379>
# If you want to delete key which doesn't present
127.0.0.1:6379> DEL user:100:name
(integer) 0
127.0.0.1:6379>
# delete multiple keys
127.0.0.1:6379> DEL user:1:name user:2:name
(integer) 2
127.0.0.1:6379>
```

# Remove everthing from DB

```
127.0.0.1:6379> FLUSHDB
OK
127.0.0.1:6379>
```

# Expring Keys Part-1

```
127.0.0.1:6379> set a b ex 10   (will expire in 10 sec)
OK
127.0.0.1:6379> keys *
1) "a"
127.0.0.1:6379> get a
"b"
127.0.0.1:6379> get a
"b"
127.0.0.1:6379> get a
(nil)
127.0.0.1:6379>

# You can check the time left to expire

127.0.0.1:6379> set a b ex 10
OK
127.0.0.1:6379> ttl a
(integer) 8
127.0.0.1:6379> ttl a
(integer) 7
127.0.0.1:6379> ttl a
(integer) 5
127.0.0.1:6379> ttl a
(integer) 2
127.0.0.1:6379> ttl a
(integer) -2
127.0.0.1:6379>

127.0.0.1:6379> set a b ex 10
OK
127.0.0.1:6379> ttl a
(integer) 8
127.0.0.1:6379> expire a 60  (increase the expiry by 60 sec)
(integer) 1
127.0.0.1:6379> ttl a
(integer) 58
127.0.0.1:6379> ttl a
(integer) 54
127.0.0.1:6379>


127.0.0.1:6379> set a b exat 4324234234 (Unix time)
OK
127.0.0.1:6379> ttl a
(integer) 2641886167
127.0.0.1:6379> ttl b
(integer) -2
127.0.0.1:6379> ttl b 

127.0.0.1:6379> set b c px 3000 (millisec)
OK
127.0.0.1:6379> ttl b
(integer) 1
127.0.0.1:6379> ttl b
(integer) -2
127.0.0.1:6379> 

# Although I changed the value, still keeping the TTL continue

127.0.0.1:6379> set a b ex 60
OK
127.0.0.1:6379> TTL a
(integer) 55
127.0.0.1:6379> get a
"b"
127.0.0.1:6379> set a c keepttl
OK
127.0.0.1:6379> TTL a
(integer) 45
127.0.0.1:6379>
```
------

# Set options -XX/NX

Note: XX means it's present, NX means it's not present

```
127.0.0.1:6379> FLUSHDB
OK
127.0.0.1:6379> set a b xx
(nil)
127.0.0.1:6379> get a
(nil)
127.0.0.1:6379> set a b nx
OK
127.0.0.1:6379> set a c nx (only if a not present)
(nil)
127.0.0.1:6379> set a c xx
OK
127.0.0.1:6379>
```
----------

# Exists command

```
127.0.0.1:6379> keys *
1) "a"
127.0.0.1:6379> EXISTS a
(integer) 1  (1 means present)
127.0.0.1:6379>
127.0.0.1:6379> EXISTS b
(integer) 0
127.0.0.1:6379>


127.0.0.1:6379> set user:1:session token ex 10
OK
127.0.0.1:6379> EXISTS user:1:session
(integer) 1
127.0.0.1:6379> EXISTS user:1:session
(integer) 0
127.0.0.1:6379>
```
-------

# INCR and DECR commands

```
127.0.0.1:6379> FLUSHDB
OK
127.0.0.1:6379> set a 1
OK
127.0.0.1:6379> INCR a
(integer) 2
127.0.0.1:6379> get a
"2"
127.0.0.1:6379> INCR a
(integer) 3
127.0.0.1:6379> get a
"3"
127.0.0.1:6379> INCR a
(integer) 4
127.0.0.1:6379> get a
"4"
127.0.0.1:6379> set a 100
OK
127.0.0.1:6379> INCR a
(integer) 101
127.0.0.1:6379> get a
"101"
127.0.0.1:6379>

# Here redis will automatically create and increment
127.0.0.1:6379> INCR bb
(integer) 1
127.0.0.1:6379> INCR bb
(integer) 2
127.0.0.1:6379> DECR bb
(integer) 1
127.0.0.1:6379> DECR bb
(integer) 0
127.0.0.1:6379> INCR prod:a:visit
(integer) 1
127.0.0.1:6379> INCR prod:b:visit
(integer) 1
127.0.0.1:6379> INCR prod:a:visit
(integer) 2
127.0.0.1:6379> INCR prod:c:visit
(integer) 1
127.0.0.1:6379> get prod:a:visit
"2"   (how many times Prod:a:visit visited)     
127.0.0.1:6379>

127.0.0.1:6379> set a 1.02
OK
127.0.0.1:6379> get a
"1.02"
127.0.0.1:6379> INCRBYFLOAT a 0.3
"1.32"
127.0.0.1:6379> INCRBYFLOAT a -0.3
"1.02"
127.0.0.1:6379> INCRBYFLOAT a -0.3
"0.72"
127.0.0.1:6379>

127.0.0.1:6379> set sam 100
OK
127.0.0.1:6379> INCR sam
(integer) 101
127.0.0.1:6379> INCRBY sam 20
(integer) 121
127.0.0.1:6379> INCRBY sam 20
(integer) 141

127.0.0.1:6379> DECRBY sam 10
(integer) 131
127.0.0.1:6379>
```

--------

# Rate Limiting 

```
127.0.0.1:6379> set user:1:lives 3 ex 1800
OK
127.0.0.1:6379> ttl user:1:lives
(integer) 1791
127.0.0.1:6379> DECR user:1:lives
(integer) 2
127.0.0.1:6379> DECR user:1:lives
(integer) 1
127.0.0.1:6379> DECR user:1:lives
(integer) 0
127.0.0.1:6379>
```

----

# Hash Part-1

```
127.0.0.1:6379> FLUSHDB
OK
127.0.0.1:6379> HSET user:1 name sam age 10 city atlanta
(integer) 3
127.0.0.1:6379> keys *
1) "user:1"
127.0.0.1:6379> HGETALL user:1
1) "name"
2) "sam"
3) "age"
4) "10"
5) "city"
6) "atlanta"
127.0.0.1:6379> HGET user:1 name
"sam"
127.0.0.1:6379> HGET user:1 age
"10"
127.0.0.1:6379> HGET user:1 city
"atlanta"
127.0.0.1:6379> 

127.0.0.1:6379> hset user:2 birthYear 2023 status active
(integer) 2
127.0.0.1:6379> HGETALL user:2
1) "birthYear"
2) "2023"
3) "status"
4) "active"
127.0.0.1:6379> 
127.0.0.1:6379> TYPE user:1
hash
127.0.0.1:6379> TYPE user:2
hash

127.0.0.1:6379> EXPIRE user:2 10
(integer) 1
127.0.0.1:6379> ttl user:2
(integer) 6
127.0.0.1:6379> keys *
1) "user:1"
127.0.0.1:6379> keys *
1) "user:1"
127.0.0.1:6379>

# If I want to access all keys only 

127.0.0.1:6379> HKEYS user:1
1) "name"
2) "age"
3) "city"
# If I want to access all values only
127.0.0.1:6379> HVALS user:1
1) "sam"
2) "10"
3) "atlanta"
127.0.0.1:6379> 
# How to check if key has certain fields
127.0.0.1:6379> HEXISTS user:1 age 
(integer) 1
127.0.0.1:6379> HEXISTS user:1 status
(integer) 0
127.0.0.1:6379> hgetall user:1
1) "name"
2) "sam"
3) "age"
4) "10"
5) "city"
6) "atlanta"
127.0.0.1:6379> HDEL user:1 age
(integer) 1
127.0.0.1:6379> hgetall user:1
1) "name"
2) "sam"
3) "city"
4) "atlanta"
127.0.0.1:6379>
```

--------

# List and Queues

```
127.0.0.1:6379> RPUSH users sam mike jake
(integer) 3
127.0.0.1:6379> keys *
1) "users"
127.0.0.1:6379> TYPE users
list
127.0.0.1:6379> LLEN users
(integer) 3
127.0.0.1:6379> LRANGE users 0 -1  (If you want to see everthing - if you dont know any elements)
1) "sam"
2) "mike"
3) "jake"
127.0.0.1:6379> LRANGE users 0 1
1) "sam"
2) "mike"
127.0.0.1:6379>
```



