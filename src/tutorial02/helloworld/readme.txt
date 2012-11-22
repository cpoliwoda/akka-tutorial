Based on the package tutorial01.helloworld:
This package (tutorial02.helloworld) is a simple example of:

- reading a configuration string/file
- starting an ("akka") actor system with the settings from the configuration
- creating an actor
- sending the actor a message, which has an other message as responds
- and shuting down the system


## Output:

As output you should see (without the first ">" in each line):
> HelloActor got mail:
>   2>> sender = Actor[akka://helloSystem/deadLetters]
> HelloActor got mail:
>   1>> forwarded mail from Actor[akka://helloSystem/user/actor1],
>  original send from Actor[akka://helloSystem/deadLetters],
>  with original message: be happy


## Notice:

deadLetters means that the sender of the message was:
- **not** an actor
- or the sender actor is no longer available in system