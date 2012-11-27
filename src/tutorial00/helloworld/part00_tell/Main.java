/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial00.helloworld.part00_tell;

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
    /* TEXT/HINT FROM: http://doc.akka.io/docs/akka/2.0.3/java/untyped-actors.html
     Tell: Fire-forget
     This is the preferred way of sending messages. No blocking waiting for a message. 
     This gives the best concurrency and scalability characteristics.

     actor.tell("Hello");

     Or with the sender reference passed along with the message and available to the 
     receiving Actor in its getSender: ActorRef member field. The target actor can 
     use this to reply to the original sender, by using getSender().tell(replyMsg).

     actor.tell("Hello", getSelf());

     If invoked without the sender parameter the sender will be deadLetters actor 
     reference in the target actor.     
     */
}
