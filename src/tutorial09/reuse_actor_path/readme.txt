In this package (tutorial09.reuse_actor_path) is an example of:

## Learning

DeathWatch / Lifecycle Monitoring
-> stopping and restarting a NON_CHILD actor

example created with two actors on same system-level
- one actor is watching the second
- checking termination status of the observed/watched actor in some reusing constalations


## Output:

# # # waiting 1 SECONDS # # #
  >> receiver = Observer.observer , sender = observer , message = Observe: RepointableActorRef.watched_actor
watched_actor.isTerminated() = false
# # # waiting 1 SECONDS # # #
watched_actor.isTerminated() = true
looking for (new / second) ActorRef on old path
# # # waiting 1 SECONDS # # #
watched_actor.isTerminated() = true
watched_actor2.isTerminated() = true
# # # waiting 1 SECONDS # # #
RECREATING a terminated actor
# # # waiting 1 SECONDS # # #
watched_actor.isTerminated() = true
watched_actor2.isTerminated() = false
# # # waiting 1 SECONDS # # #
# # # waiting 1 SECONDS # # #
  >> receiver = Observer.observer , sender = observer , message = IsTerminated
  >> watched actor = watched_actor is terminated = true
setting ActorRef watched_actor in Main again
watched_actor.isTerminated() = false
# # # waiting 1 SECONDS # # #
  >> receiver = Observer.observer , sender = observer , message = IsTerminated
  >> watched actor = watched_actor is terminated = true
replace in observer actor the watched actor
# # # waiting 1 SECONDS # # #
  >> receiver = Observer.observer , sender = observer , message = Observe: RepointableActorRef.watched_actor
check if replace works. Means watched actor in observer responses with FALSE on isTerminated.
  >> receiver = Observer.observer , sender = observer , message = IsTerminated
  >> watched actor = watched_actor is terminated = false