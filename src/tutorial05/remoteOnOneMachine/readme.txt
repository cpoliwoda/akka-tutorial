Based on the packages tutorial04:
This packages (tutorial05) are a simple example of:


## Learning
- sending from each system a message to the other one

## Output:
You should see something similiar like these:

### Client:
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
>   1>> remote msg from ServerMain

### Server
> Waited 0 seconds.
> Waited 1 seconds.
> Waited 2 seconds.
> Waited 3 seconds.
> Waited 4 seconds.
> Waited 5 seconds.
> Waited 6 seconds.
> Waited 7 seconds.
> ServerActor got mail:
>   1>> remote msg from ClientMain
> Waited 8 seconds.
> Waited 9 seconds.
> sending message to an actor in an other actor system ...


## Notice:

- In these Example it is not imported which class (ServerMain or ClientMain) was 
  started first, but both should be started in a 10 second timespace.
- You need to close the systems manually by our one.