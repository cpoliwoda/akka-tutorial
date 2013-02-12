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
 * BASED ON: http://doc.akka.io/docs/akka/2.1.0/java/untyped-actors.html
 * 
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class WatchActor extends UntypedActor {

//    final ActorRef child = this.getContext().actorOf(Props.empty(), "child");
    final ActorRef child = this.getContext().actorOf(new Props(HelloActor.class), "child");

    public WatchActor() {

        System.out.println(getClass().getSimpleName() + "()");
        System.out.println(getSelf().path().name());

        this.getContext().watch(child); // <-- the only call needed for registration
    }
    ActorRef lastSender = getContext().system().deadLetters();

    @Override
    public void onReceive(Object message) {

        if (message instanceof String) {

            System.out.println(message);

            if (message.equals("kill")) {

                getContext().stop(child);
                lastSender = getSender();
            }

        } else if (message instanceof Terminated) {

            System.out.println(getSelf().path().name() + " got termination MSG");

            final Terminated t = (Terminated) message;
            if (t.getActor() == child) {
                lastSender.tell("finished", getSelf());
            }

        } else {
            unhandled(message);
        }
    }//onReceive
}
