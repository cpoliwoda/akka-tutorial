Based on the package tutorial02.helloworld:
This packages (tutorial03) are a simple example of:

- getting an actor reference from a remote system
- sending the remote actor a message
- showing that an actor can 
  + send hisself a new message as responds on an other message
  + shutdown his system

## Output:

### Client:
> sending message to an actor in an other actor system ...
> shutting down ClientMain !!!

### Server
> ServerActor got mail:
>   1>> remote msg from ClientMain
> ServerActor got mail:
>   2>> system is shutting down now !!!


## Notice:

ServerMain need to be started before ClientMain is executed.