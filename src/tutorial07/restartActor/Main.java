/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial07.restartActor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Kill;
import akka.actor.Props;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class Main {

    public static void main(String[] args) {
        //create an actor system
        ActorSystem system = ActorSystem.create();

        //create a new actor
        ActorRef actor1 = system.actorOf(new Props(HelloActor.class), "actor1");
        ActorRef actor2 = system.actorOf(new Props(HelloActor.class), "actor2");
//        ActorRef actor2 = actor1.getContext().actorOf(new Props(HelloActor.class), "actor2");


        //send the actor a message
        Messages.EventImpl event1 = new Messages.EventImpl(actor1.path().name() + " - Hi 1 - ");
        actor1.tell(event1, actor2);

        isActorTerminated(actor1);

//        system.stop(actor1);
//        system.guardian().suspend(); //no effect on actor
//        system.guardian().stop();    //no effect on actor

//        isActorTerminated(actor1);

        Messages.EventImpl event2 = new Messages.EventImpl(actor1.path().name() + " - Hi 2 - ");
        actor1.tell(event2, actor2);

//        system.guardian().restart(null);
        isActorTerminated(actor1);

        Messages.EventImpl event3 = new Messages.EventImpl(actor1.path().name() + " - Hi 3 - ");
        actor1.tell(event3, actor2);

//        system.guardian().resume(new Exception("system.guardian().resume"));
        isActorTerminated(actor1);

        Messages.EventImpl event4 = new Messages.EventImpl(actor1.path().name() + " - Hi 4 - ");
        actor1.tell(event4, actor2);

//        actor1.tell(Kill.getInstance(), null);
//        isActorTerminated(actor1);

        Messages.EventImpl event5 = new Messages.EventImpl(actor1.path().name() + " - Hi 5 - ");
        actor1.tell(event5, actor2);

        Messages.Stop stop = new Messages.Stop();
        actor1.tell(stop, actor2);
        isActorTerminated(actor1);

        Messages.EventImpl event6_1 = new Messages.EventImpl(actor1.path().name() + " - Hi 6.1 - ");
        Messages.EventImpl event6_2 = new Messages.EventImpl(actor1.path().name() + " - Hi 6.2 - ");

        actor1.tell(event6_1, actor2);
        actor2.tell(event6_2, actor2);

        isActorTerminated(actor1);


        Messages.Restart restart = new Messages.Restart();
        actor2.tell(restart, actor2);

        isActorTerminated(actor1);


        Messages.EventImpl event7 = new Messages.EventImpl(actor2.path().name() + " - Hi 7 - ");
        actor2.tell(event7, actor2);

        isActorTerminated(actor1);

        //shutdown the actor system
        system.shutdown();
        system.awaitTermination();
    }

    private static void isActorTerminated(ActorRef actor) {

        if (actor.isTerminated()) {
            String msg = "  is terminated";
            System.out.println(msg);

            actor.tell(actor.path().name() + msg, null);

        } else {
            String msg = "  is NOT terminated";
            System.out.println(msg);

            actor.tell(actor.path().name() + msg, null);
        }
    }
}
