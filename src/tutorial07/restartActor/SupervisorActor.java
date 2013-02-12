/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial07.restartActor;

import akka.actor.ActorPath;
import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.UntypedActor;
import akka.japi.Function;
import scala.Option;
import scala.concurrent.duration.Duration;

/**
 * 
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class SupervisorActor extends UntypedActor {

    private final ActorRef worker = this.getContext().actorOf(new Props(WorkerActor.class), "worker");

    public SupervisorActor() {

        this.getContext().watch(worker); // <-- the only call needed for registration
    }
    private static SupervisorStrategy strategy = new OneForOneStrategy(3,
            Duration.create("5 seconds"), new Function<Throwable, SupervisorStrategy.Directive>() {
        @Override
        public SupervisorStrategy.Directive apply(Throwable t) {

            if (t instanceof Messages.Stop) {
//                SupervisorActor.this.getContext().stop(worker);

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

    /**
     * @return the strategy
     */
    public static SupervisorStrategy getStrategy() {
        return strategy;
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    @Override
    public void onReceive(Object o) throws Exception {

        String simpleClassName = getClass().getSimpleName();
        String actorName = getSelf().path().name();
        ActorPath actorPath = getContext().system().child(actorName);
        ActorRef actor = getContext().actorFor(actorPath);

        System.out.println(simpleClassName + "." + actorName + " got mail from " + getSender() + ":");

        if (o instanceof Messages.EventImpl) {
            Messages.EventImpl msg = (Messages.EventImpl) o;
            System.out.println("  >> " + msg);

            if (msg instanceof Messages.Start) {
                worker.tell(msg, getSelf());

            } else if (msg instanceof Messages.Stop) {

                System.out.println("want to stop actor: " + actorName);

                System.out.println("actor.isTerminated() BEFORE  "
                        + "getContext().stop( worker ): " + getSelf().isTerminated());

                getContext().stop(worker);

                System.out.println("actor.isTerminated() AFTER "
                        + "getContext().stop( worker ): " + getSelf().isTerminated());


            } else if (msg instanceof Messages.Resume) {

                System.out.println("want to resume actor: " + actorName);

                System.out.println("actor.isTerminated() BEFORE "
                        + "getStrategy().resumeChild( ): " + getSelf().isTerminated());

                getStrategy().resumeChild(worker, msg);

                System.out.println("actor.isTerminated() AFTER "
                        + "getStrategy().resumeChild( ): " + getSelf().isTerminated());


            } else if (msg instanceof Messages.Restart) {
                
                System.out.println("want to restart actor: " + actorName);

                System.out.println("actor.isTerminated() BEFORE "
                        + "getStrategy().restartChild( ): " + getSelf().isTerminated());

                getStrategy().restartChild(worker, msg, true);

                System.out.println("actor.isTerminated() AFTER "
                        + "getStrategy().restartChild( ): " + getSelf().isTerminated());

            }


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
