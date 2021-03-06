/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial00.helloworld.part01_ask;

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
            
            //response on a message by doubling the message
            getSender().tell("\n"+msg +"\n"+ msg, getSelf());

        } else {
            System.out.println("  unhandled Message:\n" + o);
            getSender().tell(o, getSelf());
        }

    }
}
