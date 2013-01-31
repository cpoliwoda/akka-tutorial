/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial07.restartActor;

import akka.actor.ActorPath;
import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.SupervisorStrategy;
import akka.actor.UntypedActor;
import akka.japi.Function;
import scala.Option;
import scala.concurrent.duration.Duration;

/**
 * An simple actor that prints the messages it gots on the output stream.
 * 
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class HelloActor extends UntypedActor {

    private static SupervisorStrategy strategy = new OneForOneStrategy(3,
            Duration.create("5 seconds"), new Function<Throwable, SupervisorStrategy.Directive>() {
        @Override
        public SupervisorStrategy.Directive apply(Throwable t) {

            if (t instanceof Messages.Stop) {
                return SupervisorStrategy.stop();

            } else if (t instanceof Messages.Restart) {
                return SupervisorStrategy.restart();

            } else {
                return SupervisorStrategy.escalate();
            }

        }//apply
    });

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

        System.out.println(simpleClassName + "." + actorName + " got mail:");

        if (o instanceof Messages.EventImpl) {
            Messages.EventImpl msg = (Messages.EventImpl) o;
            System.out.println("  >> " + msg);

            if (msg instanceof Messages.Stop) {
                getContext().stop(getSelf());

            } else if (msg instanceof Messages.Restart) {
//                getContext().system().actorOf(new Props(HelloActor.class), "actor1");//[ERROR] [01/11/2013 13:52:40.001] [default-akka.actor.default-dispatcher-5] [akka://default/user/actor2] actor name actor1 is not unique!

//                System.out.println("getContext().system().child( " + actorName
//                        + " ).name() = " + actorName);

                System.out.println(actorName + ".isTerminated() = "
                        + actor.isTerminated());

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
    //onReceive
//    public void resume(){
//        getContext().guardian().resume();
//    }
//    
//    public void restart(Throwable throwable ){
//        getContext().guardian().restart(throwable );
//    }
//    
//    public void suspend(){
//        getContext().guardian().suspend();
//    }
//    
//    private SupervisorStrategy strategy = new OneForOneStrategy(
//            10, Duration.parse("1 minute"), new Function() {
//        @Override
//        public Object apply(Object t) {
//            if (t instanceof ArithmeticException) {
//                return new Resume();
//            } else if (t instanceof NullPointerException) {
//                return new Restart();
//            } else {
//                return new Escalate();
//            }
//        }
//    });
//
//    @Override
//    public SupervisorStrategy supervisorStrategy() {
//        return strategy;
//    }
}
