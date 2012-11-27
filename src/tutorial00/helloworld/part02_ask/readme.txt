In this package (tutorial00.helloworld.part02_ask) is an example of:

## Learning
- starting an ("akka") actor system with a system name
- creating actors
- asking actors for a (string) message
- pipe the responds to a third actor
- and shuting down the system


## Output:
You should see something similiar like these:

> HelloActor.Actor[akka://AskSystem/user/actorA] got mail from Actor[akka://AskSystem/temp/$a]:
> HelloActor.Actor[akka://AskSystem/user/actorB] got mail from Actor[akka://AskSystem/temp/$b]:
>   Result >> x = 0 , s = zero
>   String >> request
> HelloActor.Actor[akka://AskSystem/user/actorC] got mail from Actor[akka://AskSystem/deadLetters]:
>   unhandled Message:
> Failure(java.lang.ClassCastException: tutorial00.helloworld.part02_ask.Result cannot be cast to java.lang.String)