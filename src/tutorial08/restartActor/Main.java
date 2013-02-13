/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial08.restartActor;

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

        //create a new actor
        ActorRef actor1 = system.actorOf(new Props(SupervisorActor.class), "actor1");
        ActorRef actor2 = system.actorOf(new Props(SupervisorActor.class), "actor2");

        String actor1name = actor1.path().name();
        String actor2name = actor2.path().name();

        //send the actor a message
        
        Messages.Event unhandeledEvent = new Messages.Event() {};
        actor1.tell(unhandeledEvent, actor2);

//        Messages.EventImpl e000 = new Messages.EventImpl(" - pre start - ");
//        actor1.tell(e000, actor2);

        Messages.Start e00 = new Messages.Start();
        actor1.tell(e00, actor2);
        
        waitSeconds(1);

//        Messages.EventImpl e0 = new Messages.EventImpl(" - post start - ");
//        actor1.tell(e0, actor2);
//        
//        Messages.EventImpl e1 = new Messages.EventImpl(" - pre stop - ");
//        actor1.tell(e1, actor2);

        Messages.Stop stop = new Messages.Stop();
        actor1.tell(stop, actor2);

        waitSeconds(1);

        Messages.EventImpl e2 = new Messages.EventImpl(" - post stop - ");
        actor1.tell(e2, actor2);
        
        waitSeconds(1);

//        Messages.EventImpl e3 = new Messages.EventImpl( " - pre resume - ");
//        actor1.tell(e3, actor2);
//
//        Messages.Resume resume = new Messages.Resume();
//        actor1.tell(resume, actor2);
//
//        waitSeconds(1);
//        
//        Messages.EventImpl e4 = new Messages.EventImpl(" - post resume - ");
//        actor1.tell(e4, actor2);

//        Messages.EventImpl e5 = new Messages.EventImpl(" - pre restart - ");
//        actor1.tell(e5, actor2);
//
//        waitSeconds(1);
//        
//        Messages.Restart restart = new Messages.Restart();
//        actor1.tell(restart, actor2);
//
//        Messages.EventImpl e6 = new Messages.EventImpl(" - post restart - ");
//        actor1.tell(e6, actor2);

        waitSeconds(1);

        Messages.CheckTermination checkTermination = new Messages.CheckTermination();
        actor1.tell(checkTermination, actor2);
        
        waitSeconds(3);
        
        actor1.tell(unhandeledEvent, actor2);
        actor1.tell(checkTermination, actor2);
        

        //shutdown the actor system
        system.shutdown();
        system.awaitTermination();
    }//main

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
    }//isActorTerminated()
    
    private static void waitSeconds(long seconds){
        
        System.out.println("# # # waiting "+seconds+ " seconds # # #");
        try {
            TimeUnit.SECONDS.sleep(seconds);

        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//waitSeconds
}
