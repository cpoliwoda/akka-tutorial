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
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.japi.Function;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import scala.Option;
import scala.concurrent.duration.Duration;

/* TEXT FROM: http://doc.akka.io/docs/akka/2.1.0/general/supervision.html
 * What Lifecycle Monitoring Means
 * 
 * Note: Lifecycle Monitoring in Akka is usually referred to as DeathWatch
 * 
 * In contrast to the special relationship between parent and child described 
 * above, each actor may monitor any other actor. Since actors emerge from 
 * creation fully alive and restarts are not visible outside of the affected 
 * supervisors, the only state change available for monitoring is the 
 * transition from alive to dead. Monitoring is thus used to tie one actor to 
 * another so that it may react to the other actor’s termination, in contrast 
 * to supervision which reacts to failure.
 * 
 * Lifecycle monitoring is implemented using a Terminated message to be received 
 * by the monitoring actor, where the default behavior is to throw a special 
 * DeathPactException if not otherwise handled. In order to start listening for 
 * Terminated messages is to use ActorContext.watch(targetActorRef) and then 
 * ActorContext.unwatch(targetActorRef) to stop listening for that. One important 
 * property is that the message will be delivered irrespective of the order in 
 * which the monitoring request and target’s termination occur, i.e. you still 
 * get the message even if at the time of registration the target is already dead.
 * 
 * Monitoring is particularly useful if a supervisor cannot simply restart its 
 * children and has to terminate them, e.g. in case of errors during actor 
 * initialization. In that case it should monitor those children and re-create 
 * them or schedule itself to retry this at a later time.
 * 
 * Another common use case is that an actor needs to fail in the absence of an 
 * external resource, which may also be one of its own children. If a third party 
 * terminates a child by way of the system.stop(child) method or sending a 
 * PoisonPill, the supervisor might well be affected.
 */

/* TEXT FROM: http://doc.akka.io/docs/akka/2.1.0/general/addressing.html#actorof-vs-actorfor
 * Reusing Actor Paths
 * 
 * When an actor is terminated, its path will point to the dead letter mailbox, 
 * DeathWatch will publish its final transition and in general it is not expected 
 * to come back to life again (since the actor life cycle does not allow this). 
 * While it is possible to create an actor at a later time with an identical 
 * path—simply due to it being impossible to enforce the opposite without keeping 
 * the set of all actors ever created available—this is not good practice: remote 
 * actor references which “died” suddenly start to work again, but without any 
 * guarantee of ordering between this transition and any other event, hence the 
 * new inhabitant of the path may receive messages which were destined for the 
 * previous tenant.
 * 
 * It may be the right thing to do in very specific circumstances, but make sure 
 * to confine the handling of this precisely to the actor’s supervisor, because 
 * that is the only actor which can reliably detect proper deregistration of the 
 * name, before which creation of the new child will fail.
 * 
 * It may also be required during testing, when the test subject depends on being 
 * instantiated at a specific path. In that case it is best to mock its supervisor 
 * so that it will forward the Terminated message to the appropriate point in the 
 * test procedure, enabling the latter to await proper deregistration of the name.
 * 
 */
/**
 * 
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class SupervisorActor extends UntypedActor {

    private ActorRef worker = null;
    //
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
        String senderName = getSender().path().name();

        System.out.println("  >> " + "receiver = " + simpleClassName + "." + actorName
                + " , sender = " + senderName
                + " , message = " + o);

        if (o instanceof Messages.EventImpl) {

            if (o instanceof Messages.Start) {

                // TODO start the show :-)
                System.out.println("  >> " + "starting the show ;-)");
                System.out.println("  >> " + "init the worker");
                worker = this.getContext().actorOf(new Props(WorkerActor.class), "worker");
                System.out.println("  >> " + "watch the worker");
                getContext().watch(worker);

                wait(1, TimeUnit.SECONDS);

                System.out.println("  >> " + "CheckTermination");
                System.out.println("  >> " + "worker.isTerminated() = " + worker.isTerminated());
                getSelf().tell(new Messages.CheckTermination(), getSelf());

                wait(1, TimeUnit.SECONDS);

                System.out.println("  >> " + "stop (intern/child actor) worker");
                getContext().stop(worker);

                wait(1, TimeUnit.SECONDS);

                System.out.println("  >> " + "CheckTermination");
                System.out.println("  >> " + "worker.isTerminated() = " + worker.isTerminated());
                getSelf().tell(new Messages.CheckTermination(), getSelf());

                wait(1, TimeUnit.SECONDS);

                System.out.println("  >> " + "ReactivateWorker");
                getSelf().tell(new Messages.ReactivateChild(worker), getSelf());

                wait(1, TimeUnit.SECONDS);

                System.out.println("  >> " + "CheckTermination");
                System.out.println("  >> " + "worker.isTerminated() = " + worker.isTerminated());
                getSelf().tell(new Messages.CheckTermination(), getSelf());


                wait(5, TimeUnit.SECONDS);
                System.out.println("  >> " + "the show is over ;-)");


            } else if (o instanceof Messages.CheckTermination) {
                System.out.println("  >> " + "Messages.CheckTermination: "
                        + "worker.isTerminated() = " + worker.isTerminated());

            } else if (o instanceof Messages.ReactivateChild) {
                System.out.println("  >> " + "Messages.ReactivateChild: ");

                reactivateChild(worker, null, true);

            }



        }//if(o instance of Messages.EventImpl)
        else if (o instanceof Terminated) {
            System.out.println("  >> " + "got: Terminated, because of watching "
                    + getSender().path().name());

            //DO NOT WORK
            ActorPath actorPath = getSender().path();
            System.out.println("  >> " + "ReactivateChild( " + actorPath + " )");
            getSelf().tell(new Messages.ReactivateChild(getSender()), getSelf());

//            //
//            // just looks up for an existing Actor(Ref) in the system
//            // if the actor is terminated the new Ref returns the same
//            // status (terminaed).
//            //
//            ActorPath actorPath = getSender().path();
//            System.out.println("  >> " + "worker = getContext().actorFor( "
//                    + actorPath + " )");
//
//            worker = getContext().actorFor(actorPath);
//            getSelf().tell(new Messages.CheckTermination(), getSelf());

//            //
//            // these is the way of reusing the actor path
//            // NOT hte way it should be done here in these package !!!
//            //
//            String terminatedActorName = getSender().path().name();
//            System.out.println("  >> " + "worker = getContext().actorOf( "
//                    + terminatedActorName + " )");
//
//            worker = getContext().actorOf(new Props(WorkerActor.class), terminatedActorName);
//            getSelf().tell(new Messages.CheckTermination(), getSelf());

        } else {
            System.out.println("  >> " + "unhandled message :-|");

            unhandled(o);
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

    private static void wait(long time, TimeUnit timeUnit) {

        System.out.println("# # # waiting " + time + " " + timeUnit + " # # #");
        try {
            timeUnit.sleep(time);

        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//wait

    /**
     * Tries first to resume a child actor if is terminated and if it is still 
     * terminated after resume, the child actor is restarted.
     * 
     * @param child that should be reactivated
     * @param cause
     * @param suspendFirst says if before a restart the child should be first suspended
     */
    private void reactivateChild(ActorRef child, Throwable cause, boolean suspendFirst) {

        boolean isTerminated = child.isTerminated();
        System.out.println("  >> " + "before resume child: isTerminated = " + isTerminated);

        if (child.isTerminated()) {
            supervisorStrategy().resumeChild(child, cause);
        }

        wait(2, TimeUnit.SECONDS);

        isTerminated = child.isTerminated();
        System.out.println("  >> " + "before restart child: isTerminated = " + isTerminated);

        if (child.isTerminated()) {
            supervisorStrategy().restartChild(child, cause, suspendFirst);
        }

        wait(2, TimeUnit.SECONDS);

        isTerminated = child.isTerminated();
        System.out.println("  >> " + "after restart child: isTerminated = " + isTerminated);
    }//reactivateChild()
}
