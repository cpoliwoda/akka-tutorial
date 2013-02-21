/* TEXT FROM: http://doc.akka.io/docs/akka/2.1.0/general/supervision.html
 * What Lifecycle Monitoring Means
 * 
 * Note: Lifecycle Monitoring in Akka is usually referred to as DeathWatch
 * 
 * In contrast to the special relationship between parent and child described 
 * above, each actor may monitor any other actor. Since actors emerge from 
 * creation fully alive and restarts are not visible outside of the affected 
 * supervisors, the only state change available for monitoring is the 
 * transition from alive to dead. Monitoring is thus used to tie one actor to 
 * another so that it may react to the other actor’s termination, in contrast 
 * to supervision which reacts to failure.
 * 
 * Lifecycle monitoring is implemented using a Terminated message to be received 
 * by the monitoring actor, where the default behavior is to throw a special 
 * DeathPactException if not otherwise handled. In order to start listening for 
 * Terminated messages is to use ActorContext.watch(targetActorRef) and then 
 * ActorContext.unwatch(targetActorRef) to stop listening for that. One important 
 * property is that the message will be delivered irrespective of the order in 
 * which the monitoring request and target’s termination occur, i.e. you still 
 * get the message even if at the time of registration the target is already dead.
 * 
 * Monitoring is particularly useful if a supervisor cannot simply restart its 
 * children and has to terminate them, e.g. in case of errors during actor 
 * initialization. In that case it should monitor those children and re-create 
 * them or schedule itself to retry this at a later time.
 * 
 * Another common use case is that an actor needs to fail in the absence of an 
 * external resource, which may also be one of its own children. If a third party 
 * terminates a child by way of the system.stop(child) method or sending a 
 * PoisonPill, the supervisor might well be affected.
 */

/* TEXT FROM: http://doc.akka.io/docs/akka/2.1.0/general/addressing.html#actorof-vs-actorfor
 * Reusing Actor Paths
 * 
 * When an actor is terminated, its path will point to the dead letter mailbox, 
 * DeathWatch will publish its final transition and in general it is not expected 
 * to come back to life again (since the actor life cycle does not allow this). 
 * While it is possible to create an actor at a later time with an identical 
 * path—simply due to it being impossible to enforce the opposite without keeping 
 * the set of all actors ever created available—this is not good practice: remote 
 * actor references which “died” suddenly start to work again, but without any 
 * guarantee of ordering between this transition and any other event, hence the 
 * new inhabitant of the path may receive messages which were destined for the 
 * previous tenant.
 * 
 * It may be the right thing to do in very specific circumstances, but make sure 
 * to confine the handling of this precisely to the actor’s supervisor, because 
 * that is the only actor which can reliably detect proper deregistration of the 
 * name, before which creation of the new child will fail.
 * 
 * It may also be required during testing, when the test subject depends on being 
 * instantiated at a specific path. In that case it is best to mock its supervisor 
 * so that it will forward the Terminated message to the appropriate point in the 
 * test procedure, enabling the latter to await proper deregistration of the name.
 * 
 */
package tutorial09.reuse_actor_path;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class Main {

    public static void main(String[] args) {

        //create an actor system
        ActorSystem system = ActorSystem.create();

        //create a new actors
        ActorRef observer = system.actorOf(new Props(Observer.class), "observer");
        ActorRef watched_actor = system.actorOf(new Props(WatchedActor.class), "watched_actor");
        ActorRef watched_actor2 = null;

        //tell observer which actor to observe
        observer.tell(new Messages.Observe(watched_actor), observer);

        wait(1, TimeUnit.SECONDS);

        System.out.println("watched_actor.isTerminated() = " + watched_actor.isTerminated());//FALSE

        system.stop(watched_actor);

        wait(1, TimeUnit.SECONDS);

        System.out.println("watched_actor.isTerminated() = " + watched_actor.isTerminated());//TRUE

        System.out.println("looking for (new / second) ActorRef on old path");
        watched_actor2 = system.actorFor(watched_actor.path());

        wait(1, TimeUnit.SECONDS);

        System.out.println("watched_actor.isTerminated() = " + watched_actor.isTerminated());//TRUE
        System.out.println("watched_actor2.isTerminated() = " + watched_actor2.isTerminated());//TRUE

        wait(1, TimeUnit.SECONDS);

        System.out.println("RECREATING a terminated actor");
        Class actorClass = watched_actor.getClass();
//        Props props = new Props(actorClass);//ERROR
        Props props = new Props(WatchedActor.class);
        watched_actor2 = system.actorOf(props, watched_actor.path().name());

        wait(1, TimeUnit.SECONDS);

        System.out.println("watched_actor.isTerminated() = " + watched_actor.isTerminated());//TRUE
        System.out.println("watched_actor2.isTerminated() = " + watched_actor2.isTerminated());//FALSE

        wait(1, TimeUnit.SECONDS);

        observer.tell(new Messages.IsTerminated(), observer);//old Ref in observer isTerminated = TRUE 
        wait(1, TimeUnit.SECONDS);

        System.out.println("setting ActorRef watched_actor in Main again");
        ////setting ActorRef watched_actor to watched_actor2
//        watched_actor = watched_actor2;

//        //looking FOR new Actor, on old path and reusing old ActorRef 
        watched_actor = system.actorFor(watched_actor.path());

        System.out.println("watched_actor.isTerminated() = " + watched_actor.isTerminated());//FALSE
        observer.tell(new Messages.IsTerminated(), observer);//TRUE

        wait(1, TimeUnit.SECONDS);

        System.out.println("replace in observer actor the watched actor");
        observer.tell(new Messages.Observe(watched_actor), observer);

        wait(1, TimeUnit.SECONDS);
        System.out.println("check if replace works."
                + " Means watched actor in observer responses with FALSE"
                + " on isTerminated.");
        observer.tell(new Messages.IsTerminated(), observer);//FALSE


        //shutdown the actor system
        system.shutdown();
        system.awaitTermination();
    }//main

    private static void wait(long time, TimeUnit timeUnit) {

        System.out.println("# # # waiting " + time + " " + timeUnit + " # # #");
        try {
            timeUnit.sleep(time);

        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//wait
}
