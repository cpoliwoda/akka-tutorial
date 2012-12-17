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
        ActorRef actor = system.actorOf(new Props(HelloActor.class));

        //send the actor a message
        actor.tell(" - Hi 1 - ");

        isActorTerminated(actor);

        system.stop(actor);
//        system.guardian().suspend(); //no effect on actor
//        system.guardian().stop();    //no effect on actor

        isActorTerminated(actor);

        actor.tell(" - Hi 2 - ");

        system.guardian().restart(null);
        isActorTerminated(actor);
        
        actor.tell(" - Hi 3 - ");
        
        system.guardian().resume();
        isActorTerminated(actor);
        
        actor.tell(" - Hi 4 - ");
        
        actor.tell(Kill.getInstance(), null);
        isActorTerminated(actor);
        
        actor.tell(" - Hi 5 - ");

        //shutdown the actor system
        system.shutdown();
    }

    private static void isActorTerminated(ActorRef actor) {
        if (actor.isTerminated()) {
            System.out.println("actor is terminated");
        } else {
            System.out.println("actor is NOT terminated");
        }
    }
}
