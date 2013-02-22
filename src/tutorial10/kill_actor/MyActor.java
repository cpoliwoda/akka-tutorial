/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial10.kill_actor;

import akka.actor.ActorRef;
import akka.actor.Kill;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import scala.Option;

/**
 * BASED ON: AKKA 2.1.0 DOCUMENTATION PDF chapter 4.3 Logging (Java) page 70.
 * 
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class MyActor extends UntypedActor {

    ActorRef child1 = null;
    ActorRef child2 = null;

    @Override
    public void onReceive(Object o) {
        if (o instanceof Messages.Start) {

            child1 = getContext().actorOf(new Props(MyActor.class), "child1");
            child2 = getContext().actorOf(new Props(MyActor.class), "child2");
            getContext().watch(child1);
            getContext().watch(child2);

            for (ActorRef c : getContext().getChildren()) {
                System.out.println("  >> " + getSelf().path().name()
                        + " children: " + c.path().name()
                        + " isTerminated = "+c.isTerminated());
            }


            getSelf().tell(new Messages.MyKill(), getSelf());

        } else if (o instanceof Messages.MyKill) {
            child1.tell(Kill.getInstance(), null);

        } else if (o instanceof Terminated) {
            System.out.println("  >> " + getSelf().path().name()
                    + " got Terminated msg from " + getSender().path().name());
            
            for (ActorRef c : getContext().getChildren()) {
                System.out.println("  >> " + getSelf().path().name()
                        + " children: " + c.path().name()
                        + " isTerminated = "+c.isTerminated());
            }
            
        } else {
            unhandled(o);
        }
    }

    @Override
    public void preStart() {
        super.preStart();

        System.out.println(getSelf().path().name() + ".preStart() called.");
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) {
        super.preRestart(reason, message);

        System.out.println(getSelf().path().name() + ".preRestart() called.");
    }

    @Override
    public void postRestart(Throwable reason) {
        super.postRestart(reason);

        System.out.println(getSelf().path().name() + ".postRestart() called.");
    }

    @Override
    public void postStop() {
        super.postStop();

        System.out.println(getSelf().path().name() + ".postStop() called.");
    }
}
