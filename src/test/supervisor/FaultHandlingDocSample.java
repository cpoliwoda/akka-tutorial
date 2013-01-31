/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.supervisor;

//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
import akka.actor.*;
//import akka.dispatch.Mapper;
//import akka.japi.Function;
//import scala.concurrent.duration.Duration;
//import akka.util.Timeout;
//import akka.event.Logging;
//import akka.event.LoggingAdapter;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

//import static akka.japi.Util.classTag;
//
//import static akka.actor.SupervisorStrategy.resume;
//import static akka.actor.SupervisorStrategy.restart;
//import static akka.actor.SupervisorStrategy.stop;
//import static akka.actor.SupervisorStrategy.escalate;
//import akka.actor.SupervisorStrategy.Directive;
//import static akka.pattern.Patterns.ask;
//import static akka.pattern.Patterns.pipe;
//    import static docs.actor.japi.FaultHandlingDocSample.WorkerApi.*;
//    import static docs.actor.japi.FaultHandlingDocSample.CounterServiceApi.*;
//    import static docs.actor.japi.FaultHandlingDocSample.CounterApi.*;
//    import static docs.actor.japi.FaultHandlingDocSample.StorageApi.*;
/**
 * BASED ON / ORIGINAL:
 * http://doc.akka.io/docs/akka/2.1.0/java/fault-tolerance-sample.html
 */
public class FaultHandlingDocSample {

    /**
     * Runs the sample
     */
    public static void main(String[] args) {
        
        Config config = ConfigFactory.parseString("akka.loglevel = DEBUG \n"
                + "akka.actor.debug.lifecycle = on");

        ActorSystem system = ActorSystem.create("FaultToleranceSample", config);

        ActorRef worker = system.actorOf(new Props(Worker.class), "worker");
        ActorRef progressListener = system.actorOf(new Props(Listener.class), "listener");

        // start the work and listen on progress
        // note that the listener is used as sender of the tell,
        // i.e. it will receive replies from the worker
        worker.tell(WorkerApi.Start, progressListener);
    }//main
}//FaultHandlingDocSample
