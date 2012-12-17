/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial07.restartActor;

import akka.actor.OneForOneStrategy;
import akka.actor.SupervisorStrategy;
import akka.actor.UntypedActor;
import akka.dispatch.Resume;
import akka.japi.Function;
import akka.util.Duration;

/**
 * An simple actor that prints the messages it gots on the output stream.
 * 
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class HelloActor extends UntypedActor {

    @Override
    public void onReceive(Object o) throws Exception {
        System.out.println(getClass().getSimpleName() + " got mail:");

        if (o instanceof String) {
            String msg = (String) o;
            System.out.println("  >> " + msg);
        }
    }//onReceive
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
