In this package (tutorial00.helloworld.part01_ask) is an example of:

## Notice
Using Patterns.ask() will send a message to the receiving Actor as with tell(), 
and the receiving actor must reply with ***getSender().tell(reply)*** in order to 
complete the returned Future with a value.

## Learning
- starting an actor system with a system name
- creating an actor with name
- asking the actor
- waiting for a response / answer of the actor
- printing the response


## Output:
You should see something similiar like these:

> HelloActor.Actor[akka://AskSystem/user/actorA] got mail from Actor[akka://AskSystem/temp/$a]:
>   String >> request
> Response is instanceof String : 
> request
> request