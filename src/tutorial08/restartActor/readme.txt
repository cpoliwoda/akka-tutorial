In this package (tutorial08.restartActor) is an example of:

## Learning

- stopping and restarting an child actor


## Output:

- Lines that starts with " >> " are produced by SupervisorActor and 
  lines which starts directly with text are proced by the observed child (worker) actor.
- The line which starts with "[ERROR]" is produced by the akka system.
- "# # # waiting" beginning lines are pauses before sending the next message in 
  Main class to the supervisor actor.

# # # waiting 1 seconds # # #
worker.preStart() called.
  >> actor2.preStart() called.
  >> actor1.preStart() called.
worker.preStart() called.
  >> SupervisorActor.actor1 got mail from Actor[akka://default/user/actor2]:
  >> unhandled message :-|
  >> BEFORE unhandled(): worker.isTerminated() false
WorkerActor.worker got mail from Actor[akka://default/user/actor1]:
unhandled message :-( 
BEFORE unhandled: isTerminated() false
  >> AFTER unhandled(): worker.isTerminated() false
AFTER unhandled: isTerminated() false
  >> SupervisorActor.actor1 got mail from Actor[akka://default/user/actor2]:
  >> START
WorkerActor.worker got mail from Actor[akka://default/user/actor1]:
START
# # # waiting 1 seconds # # #
  >> SupervisorActor.actor1 got mail from Actor[akka://default/user/actor2]:
  >> STOP
  >> want to stop actor: worker
  >> BEFORE getContext().stop( worker ): worker.isTerminated()  false
WorkerActor.worker got mail from Actor[akka://default/user/actor1]:
STOP
  >> AFTER getContext().stop( worker ): worker.isTerminated() false
worker.postStop() called.
  >> SupervisorActor.actor1 got mail from Actor[akka://default/user/actor1/worker]:
  >> unhandled message :-|
  >> BEFORE unhandled(): worker.isTerminated() true
[ERROR] [02/13/2013 15:05:10.015] [default-akka.actor.default-dispatcher-5] [akka://default/user/actor1] Monitored actor [Actor[akka://default/user/actor1/worker]] terminated (akka.actor.DeathPactException)
  >> actor1.postStop() called.
  >> actor1.preRestart() called.
  >> actor1.preStart() called.
worker.preStart() called.
  >> actor1.postRestart() called.
# # # waiting 1 seconds # # #
  >> SupervisorActor.actor1 got mail from Actor[akka://default/user/actor2]:
  >>  - post stop - 
# # # waiting 1 seconds # # #
# # # waiting 3 seconds # # #
  >> SupervisorActor.actor1 got mail from Actor[akka://default/user/actor2]:
  >> CHECKTERMINATION
  >> BEFORE checkTermination: worker.isTerminated() false
  >> AFTER checkTermination: worker.isTerminated() false
WorkerActor.worker got mail from Actor[akka://default/user/actor1]:
CHECKTERMINATION
  >> SupervisorActor.actor1 got mail from Actor[akka://default/user/actor2]:
  >> unhandled message :-|
  >> BEFORE unhandled(): worker.isTerminated() false
  >> AFTER unhandled(): worker.isTerminated() false
  >> SupervisorActor.actor1 got mail from Actor[akka://default/user/actor2]:
  >> CHECKTERMINATION
  >> BEFORE checkTermination: worker.isTerminated() false
WorkerActor.worker got mail from Actor[akka://default/user/actor1]:
unhandled message :-( 
  >> AFTER checkTermination: worker.isTerminated() false
BEFORE unhandled: isTerminated() false
AFTER unhandled: isTerminated() false
WorkerActor.worker got mail from Actor[akka://default/user/actor1]:
CHECKTERMINATION
worker.postStop() called.
worker.postStop() called.
  >> actor1.postStop() called.
  >> actor2.postStop() called.