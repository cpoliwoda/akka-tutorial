/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial09.reuse_actor_path;

import akka.actor.ActorPath;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import scala.Option;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class WatchedActor extends UntypedActor {

    @Override
    public void onReceive(Object o) throws Exception {

        String simpleClassName = getClass().getSimpleName();
        String actorName = getSelf().path().name();
        ActorPath actorPath = getContext().system().child(actorName);
        ActorRef actor = getContext().actorFor(actorPath);

        System.out.println(simpleClassName + "." + actorName + " got mail from " + getSender() + ":");

        if (o instanceof Messages.EventImpl) {
            Messages.EventImpl msg = (Messages.EventImpl) o;

            System.out.println(msg);

        } else {
            System.out.println("unhandled message :-( ");
            
            unhandled(o);
        }
    }
    
}
