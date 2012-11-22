/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial00.helloworld;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
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
        actor.tell(" - Hi - ");
        
        //shutdown the actor system
        system.shutdown();
    }
    
}
