/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial08.restartActor;

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

        this.getContext().watch(worker); // needed for registration
        // and reactivating the child actor after stopping it
    }
    private static SupervisorStrategy strategy = new OneForOneStrategy(3,
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

        System.out.println("  >> " + simpleClassName + "." + actorName + " got mail from " + getSender() + ":");

        if (o instanceof Messages.EventImpl) {
            Messages.EventImpl msg = (Messages.EventImpl) o;
            System.out.println("  >> " + msg);

//            //forward the message to the worker
//            worker.tell(msg, getSelf());

            if (msg instanceof Messages.Start) {
                Messages.Start start = (Messages.Start) msg;

                worker.tell(start, getSelf());

            } else if (msg instanceof Messages.Stop) {
                Messages.Stop stop = (Messages.Stop) msg;

                //tell worker to stop with supervisor as sender
                worker.tell(stop, getSelf());

                System.out.println("  >> " + "want to stop actor: " + worker.path().name());

                System.out.println("  >> " + "BEFORE getContext().stop( worker ): "
                        + "worker.isTerminated()  " + worker.isTerminated());

                getContext().stop(worker);

                System.out.println("  >> " + "AFTER getContext().stop( worker ): "
                        + "worker.isTerminated() " + worker.isTerminated());


            } else if (msg instanceof Messages.Resume) {
                Messages.Resume resume = (Messages.Resume) msg;

                worker.tell(resume, getSelf());

                System.out.println("  >> " + "want to resume actor: " + worker.path().name());

                System.out.println("  >> " + "BEFORE getStrategy().resumeChild( ): "
                        + "worker.isTerminated() " + worker.isTerminated());

                getStrategy().resumeChild(worker, resume);

                System.out.println("  >> " + "AFTER getStrategy().resumeChild( ): "
                        + "worker.isTerminated() " + worker.isTerminated());


            } else if (msg instanceof Messages.Restart) {
                Messages.Restart restart = (Messages.Restart) msg;

                worker.tell(restart, getSelf());

                System.out.println("  >> " + "want to restart actor: " + worker.path().name());

                System.out.println("  >> " + "BEFORE getStrategy().restartChild( ): "
                        + "worker.isTerminated() " + worker.isTerminated());

                getStrategy().restartChild(worker, restart, true);

                System.out.println("  >> " + "AFTER getStrategy().restartChild( ): "
                        + "worker.isTerminated() " + worker.isTerminated());

            } else if (msg instanceof Messages.CheckTermination) {
                Messages.CheckTermination checkTermination = (Messages.CheckTermination) msg;


                System.out.println("  >> " + "BEFORE checkTermination: "
                        + "worker.isTerminated() " + worker.isTerminated());

                worker.tell(checkTermination, getSelf());

                System.out.println("  >> " + "AFTER checkTermination: "
                        + "worker.isTerminated() " + worker.isTerminated());

            }


        }//if(o instance of Messages.EventImpl)
        else {
            System.out.println("  >> " + "unhandled message :-|");

            System.out.println("  >> " + "BEFORE unhandled(): " + "worker.isTerminated() " + worker.isTerminated());

            worker.tell(o, getSelf());

            unhandled(o);

            System.out.println("  >> " + "AFTER unhandled(): " + "worker.isTerminated() " + worker.isTerminated());
        }

    }//onReceive()

    @Override
    public void preStart() {
        super.preStart();

        System.out.println("  >> " + getSelf().path().name() + ".preStart() called.");
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) {
        super.preRestart(reason, message);

        System.out.println("  >> " + getSelf().path().name() + ".preRestart() called.");
    }

    @Override
    public void postRestart(Throwable reason) {
        super.postRestart(reason);

        System.out.println("  >> " + getSelf().path().name() + ".postRestart() called.");
    }

    @Override
    public void postStop() {
        super.postStop();

        System.out.println("  >> " + getSelf().path().name() + ".postStop() called.");
    }
}
