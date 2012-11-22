/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial05.remoteOnOneMachine.client;

import akka.actor.UntypedActor;
import tutorial05.remoteOnOneMachine.Messages;

/**
 * An simple actor that prints the messages it gots on the output stream.
 * 
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class ClientActor extends UntypedActor {

    @Override
    public void onReceive(Object o) throws Exception {
        System.out.println(getClass().getSimpleName() + " got mail:");

        //print received string message on output stream
        if (o instanceof String) {
            String msg = (String) o;
            System.out.println("  1>> " + msg);
            
//            getSelf().tell(new Messages.Shutdown());
        }//String
        //
        //shutdown the system in which the actor is running
        else if (o instanceof Messages.Shutdown) {
            System.out.println("  2>> system is shutting down now !!!");
            getContext().system().shutdown();
        }//Messages.Shutdown
        //
        else {
            System.out.println(getClass().getSimpleName()+" got an unhandled Message");
        }
         
    }
}
