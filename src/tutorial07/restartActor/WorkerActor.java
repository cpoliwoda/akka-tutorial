/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial07.restartActor;

import akka.actor.ActorPath;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class WorkerActor extends UntypedActor {

    @Override
    public void onReceive(Object o) throws Exception {
    
        String simpleClassName = getClass().getSimpleName();
        String actorName = getSelf().path().name();
        ActorPath actorPath = getContext().system().child(actorName);
        ActorRef actor = getContext().actorFor(actorPath);

        System.out.println(simpleClassName + "." + actorName + " got mail from " + getSender() + ":");

    }
    
}
