/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package example00.gameOfLife01;

import akka.actor.ActorRef;
import akka.actor.AllForOneStrategy;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.UntypedActor;
import akka.japi.Function;
import java.util.concurrent.TimeUnit;
import scala.concurrent.duration.Duration;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class GameActor extends UntypedActor {

    ActorRef startButtonActor = null;
    ActorRef stopButtonActor = null;

    public GameActor() {
        Props prop = new Props(ButtonActor.class);
        startButtonActor = getContext().actorOf(prop, "startButtonActor");
        stopButtonActor = getContext().actorOf(prop, "stopButtonActor");
        
        this.getContext().watch(startButtonActor);
        this.getContext().watch(stopButtonActor);
    }

    @Override
    public void onReceive(Object o) throws Exception {

        System.out.println(getSelf() + " got a message of " + getSender());

        if (o instanceof Messages.Start) {
            startButtonActor.tell(o, stopButtonActor);

        } else if (o instanceof Messages.Stop) {
            startButtonActor.tell(o, stopButtonActor);
            
        } else {
            System.out.println(" !! unhandled message");
            unhandled(o);
        }

    }//onReceive()
    
    private static SupervisorStrategy strategy = new AllForOneStrategy(3,
            Duration.create("5 seconds"), new Function<Throwable, SupervisorStrategy.Directive>() {
        @Override
        public SupervisorStrategy.Directive apply(Throwable t) {

            if (t instanceof Messages.Stop) {
                return SupervisorStrategy.stop();

            } else if (t instanceof Messages.Resume) {
                return SupervisorStrategy.resume();

            } else if (t instanceof Messages.Restart) {
                return SupervisorStrategy.restart();

            } else {
                return SupervisorStrategy.escalate();
            }

        }//apply
    });//OneForOneStrategy()


//    @Override
//    public SupervisorStrategy supervisorStrategy() {
//        return strategy;
//    }
    
}
