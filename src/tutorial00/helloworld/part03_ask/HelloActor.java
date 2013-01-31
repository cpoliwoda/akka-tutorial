/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial00.helloworld.part03_ask;

import akka.actor.UntypedActor;

/**
 * An simple actor that prints the messages it gots on the output stream.
 * 
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class HelloActor extends UntypedActor {

    @Override
    public void onReceive(Object o) throws Exception {
        System.out.println(getClass().getSimpleName() + "." + getSelf()
                + " got mail from " + getSender() + ":");

        if (o instanceof String) {
            String msg = (String) o;
            System.out.println("  String >> " + msg);
//            getSender().tell(msg, getSelf());
            catchFailureInFuture(msg);

        } else if (o instanceof Result) {
            Result msg = (Result) o;
            System.out.println("  Result >> " + msg);
//            getSender().tell(msg, getSelf());
            catchFailureInFuture(msg.toString());

        } else {
            System.out.println("  unhandled Message:\n" + o);
//            getSender().tell(o, getSelf());
            catchFailureInFuture(o.toString());
        }

    }

    private void catchFailureInFuture(String result) {
        try {
//    String result = operation();
            getSender().tell(result, getSelf());
            
        } catch (Exception e) {
            getSender().tell(new akka.actor.Status.Failure(e), getSelf());
            System.out.println("Failure catched :-) and Failure told sender");
            throw e;
        }


    }
}
