In this package (tutorial10.kill_actor) is an example of:

## Learning:

- killing an actor


## Output:

myActor.preStart() called.
###>> waiting 1 MINUTES before shutting down
child1.preStart() called.
child2.preStart() called.
  >> myActor children: child1 isTerminated = false
  >> myActor children: child2 isTerminated = false
[ERROR] [02/22/2013 18:02:36.567] [default-akka.actor.default-dispatcher-4] [akka://default/user/myActor/child1] Kill (akka.actor.ActorKilledException)
child1.postStop() called.
  >> myActor got Terminated msg from child1
  >> myActor children: child2 isTerminated = false
###>> shutting akka system down
child2.postStop() called.
myActor.postStop() called.