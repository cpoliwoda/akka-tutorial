Based on the packages tutorial05:
This packages (tutorial06) are a simple example of:


## Learning
- how to establish a ping-pong-communication between actors in different actor systems


## Notice:
- In these Example it is not imported which class (ServerMain or ClientMain) was 
  started first, but both should be started in a 10 second timespace.


## Output:
You should see something similiar like these:

### Client:
> Client system is running ...
> Waited 0 seconds.
> Waited 1 seconds.
> Waited 2 seconds.
> Waited 3 seconds.
> Waited 4 seconds.
> Waited 5 seconds.
> Waited 6 seconds.
> Waited 7 seconds.
> Waited 8 seconds.
> Waited 9 seconds.
> sending message to an actor in an other actor system ...
> ClientActor got mail:
>   C3>> original send from Actor[akka://clientSystem/deadLetters],
>  with message:   S5>> counter = 1,
>  with actor: Actor[akka://serverSystem@127.0.0.1:2552/user/serverActor]
> ClientActor got mail:
>   C3>> original send from Actor[akka://clientSystem/deadLetters],
>  with message:   S5>> counter = 2,
>  with actor: Actor[akka://serverSystem@127.0.0.1:2552/user/serverActor]
> ClientActor got mail:
>   C3>> original send from Actor[akka://clientSystem/deadLetters],
>  with message:   S5>> counter = 3,
>  with actor: Actor[akka://serverSystem@127.0.0.1:2552/user/serverActor]
> ClientActor got mail:
>   C3>> original send from Actor[akka://clientSystem/deadLetters],
>  with message:   S5>> counter = 4,
>  with actor: Actor[akka://serverSystem@127.0.0.1:2552/user/serverActor]

### Server
> Server system is running ...
> Waited 0 seconds.
> Waited 1 seconds.
> Waited 2 seconds.
> Waited 3 seconds.
> Waited 4 seconds.
> Waited 5 seconds.
> Waited 6 seconds.
> ServerActor got mail:
>   S3>> original send from Actor[akka://serverSystem/deadLetters],
>  with message: msg from CLIENT-MAIN,
>  with actor: Actor[akka://clientSystem@127.0.0.1:2553/user/clientActor]
> ServerActor got mail:
>   S3>> original send from Actor[akka://serverSystem/deadLetters],
>  with message:   C5>> currentTimeMillis = 1353596465381,
>  with actor: Actor[akka://clientSystem@127.0.0.1:2553/user/clientActor]
> ServerActor got mail:
>   S3>> original send from Actor[akka://serverSystem/deadLetters],
>  with message:   C5>> currentTimeMillis = 1353596465385,
>  with actor: Actor[akka://clientSystem@127.0.0.1:2553/user/clientActor]
> ServerActor got mail:
>   S3>> original send from Actor[akka://serverSystem/deadLetters],
>  with message:   C5>> currentTimeMillis = 1353596465388,
>  with actor: Actor[akka://clientSystem@127.0.0.1:2553/user/clientActor]
> ServerActor got mail:
>   S3>> original send from Actor[akka://serverSystem/deadLetters],
>  with message:   C5>> currentTimeMillis = 1353596465391,
>  with actor: Actor[akka://clientSystem@127.0.0.1:2553/user/clientActor]
>   S6>> telling the other actor to close his system
>   S7>> telling myself to close
> ServerActor got mail:
>   S2>> system is shutting down now !!!
> Waited 7 seconds.
> Waited 8 seconds.
> Waited 9 seconds.
