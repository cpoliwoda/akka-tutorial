/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial08.createChildActor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.TypedActor;
import akka.actor.UntypedActor;

/**
 * BASED ON: http://doc.akka.io/docs/akka/2.1.0/java/untyped-actors.html
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class Main {

    public static void main(String[] args) {
        //create an actor system
        ActorSystem system = ActorSystem.create();

//        //create a new actor
//        ActorRef grandFather = system.actorOf(new Props(HelloActor.class), "grandFather");
//     
//        ActorRef father = grandFather.createChild("father");
//        ActorRef child = father.createChild( "child");
//
//        //send the actor a message
//        grandFather.tell(" grandFather.tell(father) ", father);
//        grandFather.tell(" grandFather.tell(child) ", child);
//
//        
//        father.tell(" father.tell(grandFather) ", grandFather);
//        father.tell(" father.tell(child) ", child);
//        
//        child.tell(" child.tell(grandFather) ", grandFather);
//        child.tell(" child.tell(father) ", father);
        
        ActorRef father = system.actorOf(new Props(WatchActor.class), "father");
        
        father.tell("hi");
        father.tell("kill");
        
        //shutdown the actor system
        system.shutdown();
        system.awaitTermination();
    }
    
}
