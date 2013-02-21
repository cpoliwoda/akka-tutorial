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
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.japi.Function;
import java.util.concurrent.TimeUnit;
import scala.concurrent.duration.Duration;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class GameActor extends UntypedActor {

    private ActorRef startButtonActor = null;
    private ActorRef stopButtonActor = null;
//    Messages
    private Messages.Update update = new Messages.Update();
    private Messages.UpdateDone updateDone = new Messages.UpdateDone();
//    
    private Game game = null;

    public GameActor() {
        Props prop = new Props(ButtonActor.class);
        startButtonActor = getContext().actorOf(prop, "startButtonActor");
        stopButtonActor = getContext().actorOf(prop, "stopButtonActor");

        this.getContext().watch(startButtonActor);
        this.getContext().watch(stopButtonActor);
        //need to watch also all cellActors
        //this is done if the variable "game" is set on a message receive
    }

    @Override
    public void onReceive(Object o) throws Exception {

        System.out.println("receiver = " + getSelf().path().name() + "\n"
                + "sender = " + getSender().path().name() + "\n"
                + "message = " + o + "\n");

//        if (o instanceof Messages.UpdateDone) {
//
//            //adding a pause to slow down the update process
//            TimeUnit.MILLISECONDS.sleep(100);
//
//            getSender().tell(update, getSelf());
//        } else 
        if (o instanceof Messages.Start) {

            //
            // if an actor is terminated/stopped reactivated him 
            //
            reactivedActor(startButtonActor, null, true);

            reactivedActor(stopButtonActor, null, true);

            for (Cell cell : game.getCells()) {

                reactivedActor(cell.getActor(), null, true);
            }

            //
            // send now the message
            //
            startButtonActor.tell(o, getSelf());
            stopButtonActor.tell(o, getSelf());

            for (Cell cell : game.getCells()) {
                cell.getActor().tell(update, startButtonActor);
            }

        } else if (o instanceof Messages.Stop) {

            // forward the stop-message to all actors

            startButtonActor.tell(o, getSelf());
            stopButtonActor.tell(o, getSelf());

            for (Cell cell : game.getCells()) {
                cell.getActor().tell(o, getSelf());
            }

            //  STOP ALL ACTORS

            getContext().stop(startButtonActor);
            getContext().stop(stopButtonActor);

            for (Cell cell : game.getCells()) {
                getContext().stop(cell.getActor());
            }

        } else if (o instanceof Game) {
            game = (Game) o;

            for (Cell cell : game.getCells()) {
                getContext().watch(cell.getActor());
            }

        } else if (o instanceof Terminated) {
            System.out.println(getSelf().path().name()
                    + " got Terminated message of " + getSender().path().name());

        } else {
            System.out.println("!! unhandled message " + getSelf().path().name());
            unhandled(o);
        }

    }//onReceive()
//    private static SupervisorStrategy strategy = new AllForOneStrategy(1,
    private static SupervisorStrategy strategy = new OneForOneStrategy(1,
            Duration.create("3 seconds"), new Function<Throwable, SupervisorStrategy.Directive>() {
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

// as long as these methode is commented out the above supervisor strategy is NOT used !!
    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    /**
     * Check if actor is terminated and try to resume him.
     * 
     * @param actor to resume
     * @param cause that should be used
     */
    private void resumeActor(ActorRef actor, Throwable cause) {
        System.out.println(actor.path().name()
                + " was terminated resuming now.");

//        supervisorStrategy().resumeChild(actor, cause);
        
        ActorRef parent = getContext().actorFor(actor.path().parent());
        
        parent.

        System.out.println(actor.path().name()
                + ".isTerminated = " + actor.isTerminated());
    }

    /**
     * Check if actor is terminated and try to restart him.
     * 
     * @param actor to restart
     * @param cause that should be used
     * @param suspendFirst says if actor should first suspend before restarting him
     */
    private void restartActor(ActorRef actor, Throwable cause, boolean suspendFirst) {
        System.out.println(actor.path().name()
                + " was terminated restarting.");

        supervisorStrategy().restartChild(actor, cause, suspendFirst);

        System.out.println(actor.path().name()
                + ".isTerminated = " + actor.isTerminated());
    }

    /**
     * Check if actor is terminated and first tries the actor to resume and
     * if still terminated tries to restart him.
     * 
     * @see resumeActor() and restartActor()
     * 
     * @param actor to restart
     * @param cause that should be used
     * @param suspendFirst says if actor should first suspend before restarting him
     */
    private void reactivedActor(ActorRef actor, Throwable cause, boolean suspendFirst) {
        if (actor.isTerminated()) {
            resumeActor(actor, cause);

            //if still terminated try to restart
            if (actor.isTerminated()) {
                restartActor(actor, cause, suspendFirst);
            }
        }
    }
}
