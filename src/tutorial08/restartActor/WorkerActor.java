/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial08.restartActor;

import akka.actor.ActorPath;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import scala.Option;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class WorkerActor extends UntypedActor {

    @Override
    public void onReceive(Object o) throws Exception {

        String simpleClassName = getClass().getSimpleName();
        String actorName = getSelf().path().name();
        String senderName = getSender().path().name();
        ActorPath actorPath = getContext().system().child(actorName);
        ActorRef actor = getContext().actorFor(actorPath);

        System.out.println( "receiver = " + simpleClassName + "." + actorName
                + " , sender = " + senderName
                + " , message = " + o);
        
        
        if (o instanceof Messages.EventImpl) {
            
            //nothing to do here yet

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
