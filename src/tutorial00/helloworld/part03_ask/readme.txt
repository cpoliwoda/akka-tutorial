In this package (tutorial00.helloworld.part03_ask) is an example of:

## Learning
- catching the Failure within an actor from tutorial00.helloworld.part02_ask


## Output:
You should see something similiar like these:

> HelloActor.Actor[akka://AskSystem/user/actorA] got mail from Actor[akka://AskSystem/temp/$b]:
>   String >> request
> HelloActor.Actor[akka://AskSystem/user/actorB] got mail from Actor[akka://AskSystem/temp/$a]:
>   String >> another request
> HelloActor.Actor[akka://AskSystem/user/actorC] got mail from Actor[akka://AskSystem/deadLetters]:
>   unhandled Message:
> Failure(java.lang.ClassCastException: java.lang.String cannot be cast to java.lang.Integer)