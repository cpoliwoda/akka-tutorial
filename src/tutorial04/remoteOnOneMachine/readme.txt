Based on the packages tutorial03:
This packages (tutorial04) are a simple example of:

### like tutorial03
- getting an actor reference from a remote system
- sending the remote actor a message
- showing that an actor can 
  + send hisself a new message as responds on an other message
  + shutdown his system
### new in tutorial04
- using a constants class to reduce typos


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