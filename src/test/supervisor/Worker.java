/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.supervisor;

import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.UntypedActor;
import akka.dispatch.Mapper;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Function;
import akka.japi.Util;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.duration.Duration;
import test.supervisor.CounterServiceApi.CurrentCount;
import test.supervisor.CounterServiceApi.Increment;
import test.supervisor.CounterServiceApi.ServiceUnavailable;
import test.supervisor.WorkerApi.Progress;

/**
 * Worker performs some work when it receives the Start message. It will
 * continuously notify the sender of the Start message of current Progress.
 * The Worker supervise the CounterService.
 */
public class Worker extends UntypedActor {

    final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    final Timeout askTimeout = new Timeout(Duration.create(5, "seconds"));
    //
    // The sender of the initial Start message will continuously be notified
    // about progress
    ActorRef progressListener;
    //
    final ActorRef counterService = getContext().actorOf(
            new Props(CounterService.class), "counter");
    //
    final int totalCount = 51;
    //
    // Stop the CounterService child if it throws ServiceUnavailable
    private static SupervisorStrategy strategy = new OneForOneStrategy(-1,
            Duration.Inf(),
            new Function<Throwable, SupervisorStrategy.Directive>() {
                @Override
                public SupervisorStrategy.Directive apply(Throwable t) {
                    if (t instanceof ServiceUnavailable) {
                        return SupervisorStrategy.stop();
                    } else {
                        return SupervisorStrategy.escalate();
                    }
                }
            }//new Funktion<,>()
            );//new OneForOneStrategy()

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    @Override
    public void onReceive(Object msg) {
        log.debug("received message {}", msg);

        if (msg.equals(WorkerApi.Start) && progressListener == null) {

            progressListener = getSender();

            //translation into german
            //scheduler =^= Planer / Steuerprogramm / Arbeitsplaner 
            //dispatcher =^= Disponent / Fahrdienstleiter / Rechenzeitverteiler / Vertriebsdisponent
            getContext().system().scheduler().schedule(
                    Duration.Zero(),
                    Duration.create(1, "second"),
                    getSelf(),
                    WorkerApi.Do,
                    getContext().dispatcher());

        } else if (msg.equals(WorkerApi.Do)) {

            counterService.tell(new Increment(1), getSelf());
            counterService.tell(new Increment(1), getSelf());
            counterService.tell(new Increment(1), getSelf());

            // Send current progress to the initial sender
            Patterns.pipe(
                    Patterns.ask(counterService, CounterServiceApi.GetCurrentCount, askTimeout)//Patterns.ask
                    .mapTo(Util.classTag(CurrentCount.class))
                    .map(new Mapper<CurrentCount, Progress>() {
                @Override
                public Progress apply(CurrentCount c) {
                    return new Progress(100.0 * c.count / totalCount);
                }//apply
            }//new Mapper<>()
                    , getContext().dispatcher())//map
                    , getContext().dispatcher())//Patterns.pipe
                    .to(progressListener);//to() is executed after pipe() => pipe().to() 

        } else {
            unhandled(msg);
        }
    }//onReceive()
}//Worker