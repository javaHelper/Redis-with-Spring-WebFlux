# Redis Authentication


|                                      |                              |
|-------------                         |  -------------               |
|acl list                              | show current users           |
|acl whoami                            | current uset                 |
|acl setuser [username] > [password]   | create user with password.   |
|acl setuser [username] nopass         | create user with no pass.    |
|acl setuser [username] on             | enable the user              |
|acl setuser [username] off            | disable the user.            |
|acl deluser [username]                | removes the user             |


```
prateekashtikar@Prateeks-MacBook-Pro ~ % docker exec -it redis bash
root@redis:/data# redis-cli 
127.0.0.1:6379> ACL LIST
1) "user default on nopass ~* &* +@all"
127.0.0.1:6379> ACL WHOAMI
"default"
127.0.0.1:6379> ACL SETUSER sam > secret
(error) ERR Error in ACL SETUSER modifier 'secret': Syntax error
127.0.0.1:6379> ACL SETUSER sam >secret
OK
127.0.0.1:6379> ACL LIST
1) "user default on nopass ~* &* +@all"
2) "user sam off #2bb80d537b1da3e38bd30361aa855686bde0eacd7162fef6a25fe97bf527a25b &* -@all"
127.0.0.1:6379> ACL DELUSER sam
(integer) 1
127.0.0.1:6379> ACL SETUSER sam >pass123 on
OK
127.0.0.1:6379> ACL LIST
1) "user default on nopass ~* &* +@all"
2) "user sam on #9b8769a4a742959a2d0298c36fb70623f2dfacda8436237df08d8dfd5b37374c &* -@all"
127.0.0.1:6379> ACL SETUSER sam off
OK
127.0.0.1:6379> ACL LIST
1) "user default on nopass ~* &* +@all"
2) "user sam off #9b8769a4a742959a2d0298c36fb70623f2dfacda8436237df08d8dfd5b37374c &* -@all"
127.0.0.1:6379> ACL SETUSER sam on
OK
127.0.0.1:6379> AUTH sam pass123
OK
127.0.0.1:6379> ACL WHOAMI
(error) NOPERM this user has no permissions to run the 'acl' command or its subcommand
127.0.0.1:6379> AUTH default nopass
OK
127.0.0.1:6379> 

```

# acl setuser [username] >[password] on

|                                      |                               |
|-------------                         |  -------------                |
|allcommands / +@all                   |access to all commands in redis|
|-get, -set...                         |no access to get set commands. |
|+@set, +@hash, +@list                 |access to set and hash related commands|
|allkeys / ~*                          |access to all the keys         |
|~numbers:*|                           |access to keys starting with no.|



```
127.0.0.1:6379> acl whoami
"default"
127.0.0.1:6379> acl list
1) "user default on nopass ~* &* +@all"
127.0.0.1:6379> ACL SETUSER sam >pass123 on allcommands allkeys 
OK
127.0.0.1:6379> acl list
1) "user default on nopass ~* &* +@all"
2) "user sam on #9b8769a4a742959a2d0298c36fb70623f2dfacda8436237df08d8dfd5b37374c ~* &* +@all"
127.0.0.1:6379> auth sam pass123
OK
127.0.0.1:6379> acl whoami
"sam"
127.0.0.1:6379> set a 1
OK
127.0.0.1:6379> get a
"1"
127.0.0.1:6379> RPUSH users 1
(integer) 1
127.0.0.1:6379> keys *
1) "users"
2) "a"
127.0.0.1:6379> auth default nopass
OK
127.0.0.1:6379> acl whoami
"default"
127.0.0.1:6379> acl setuser sam -get
OK
127.0.0.1:6379> acl list
1) "user default on nopass ~* &* +@all"
2) "user sam on #9b8769a4a742959a2d0298c36fb70623f2dfacda8436237df08d8dfd5b37374c ~* &* +@all -get"
127.0.0.1:6379> auth sam pass123
OK
127.0.0.1:6379> acl whoami
"sam"
127.0.0.1:6379> keys *
1) "users"
2) "a"
127.0.0.1:6379> get a
(error) NOPERM this user has no permissions to run the 'get' command or its subcommand
127.0.0.1:6379> 
127.0.0.1:6379> auth default nopass
OK
127.0.0.1:6379> acl setuser sam -@dangerous 
OK
127.0.0.1:6379> acl list
1) "user default on nopass ~* &* +@all"
2) "user sam on #9b8769a4a742959a2d0298c36fb70623f2dfacda8436237df08d8dfd5b37374c ~* &* +@all -@dangerous -get"
127.0.0.1:6379>
```
