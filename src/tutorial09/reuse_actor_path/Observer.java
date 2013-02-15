/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial09.reuse_actor_path;

import akka.actor.ActorPath;
import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.japi.Function;
import scala.Option;
import scala.concurrent.duration.Duration;

/**
 * 
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class Observer extends UntypedActor {

    ActorRef watched_actor = null;

    @Override
    public void onReceive(Object o) throws Exception {

        String simpleClassName = getClass().getSimpleName();
        String actorName = getSelf().path().name();
        ActorPath actorPath = getContext().system().child(actorName);
        ActorRef actor = getContext().actorFor(actorPath);

        System.out.println("  >> " + "receiver = " + simpleClassName + "." + actorName
                + " , sender = " + getSender().path().name()
                + " , message = " + o);

        if (o instanceof Messages.EventImpl) {

            if (o instanceof Messages.Observe) {
                Messages.Observe msg = (Messages.Observe) o;

                watched_actor = msg.getActor();

            } else if (o instanceof Terminated) {
                System.out.println("  >> " + "watched actor ( " + watched_actor + " ) is termined");

            } else if (o instanceof Messages.IsTerminated) {
                System.out.println("  >> " + "watched actor = " + watched_actor.path().name()
                        + " is terminated = " + watched_actor.isTerminated());
            }

        }//if(o instance of Messages.EventImpl)
        else {
            System.out.println("  >> " + "unhandled message :-|");

            unhandled(o);
        }

    }//onReceive()
}
