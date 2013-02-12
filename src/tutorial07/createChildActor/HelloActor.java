/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial07.createChildActor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.actor.UntypedActor;

/**
 * An simple actor that prints the messages it gots on the output stream.
 * 
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class HelloActor extends UntypedActor {

    public HelloActor() {
        System.out.println(getClass().getSimpleName() + "()");
        System.out.println(getSelf().path().name());
    }

    @Override
    public void onReceive(Object o) throws Exception {
        System.out.println(getClass().getSimpleName() + " got mail:");

        if (o instanceof String) {

            String msg = (String) o;
            System.out.println("  >> " + msg);

        } else if (o instanceof Terminated) {

            System.out.println(getSelf().path().name() + " got termination MSG");

            getSender().tell("finished", getSelf());

        }
    }

    /**
     * Creates an ActorRef as child of the actor on which this method is called.
     * 
     * @param actorName
     * @return a new created ActorRef as child of the actor on which this method
     * is called. 
     */
    public ActorRef createChild(String actorName) {

        System.out.println("New actor( " + actorName + " ) will be created.");

        return getContext().actorOf(new Props(HelloActor.class), actorName);
    }
}
