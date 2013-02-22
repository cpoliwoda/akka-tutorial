/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial10.kill_actor;

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
        ActorRef loggingActor = system.actorOf(new Props(LoggingActor.class), "loggingActor");

        
        //start the show  ;-)
        loggingActor.tell(new Messages.Start(), loggingActor);


        //wait before closing the akka system
        long time = 1;
        TimeUnit timeUnit = TimeUnit.MINUTES;

        try {
            System.out.println("###>> waiting " + time + " " + timeUnit + " before shutting down");
            timeUnit.sleep(time);

        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("###>> shutting akka system down");

        //shutdown the actor system
        system.shutdown();
        system.awaitTermination();
    }//main
}
