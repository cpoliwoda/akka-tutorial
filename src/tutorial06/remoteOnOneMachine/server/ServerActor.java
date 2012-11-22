/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial06.remoteOnOneMachine.server;

import akka.actor.UntypedActor;
import tutorial06.remoteOnOneMachine.Messages;

/**
 * An simple actor that prints the messages it gots on the output stream.
 * 
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class ServerActor extends UntypedActor {

    private int counter = 0;
    private int counterMax = 5;

    @Override
    public void onReceive(Object o) throws Exception {
        System.out.println(getClass().getSimpleName() + " got mail:");

        //print received string message on output stream
        if (o instanceof String) {
            String msg = (String) o;
            System.out.println("  S1>> " + msg);
        }//String
        //
        //shutdown the system in which the actor is running
        else if (o instanceof Messages.Shutdown) {
            System.out.println("  S2>> system is shutting down now !!!");
            getContext().system().shutdown();
        }//Messages.Shutdown
        //
        // print the sender of the "Info" message and
        // send the actor, that is part of the received message, a new message
        else if (o instanceof Messages.Info) {
            Messages.Info msg = (Messages.Info) o;

            System.out.println("  S3>> original send from " + getSender()
                    + ",\n with message: " + msg.getMessage()
                    + ",\n with actor: " + msg.getActor());

            if (msg.getActor() == null) {
                System.err.println("  S4>> actor is NULL in Messages.Info");
            } else {
                counter++;
                if (counter < counterMax) {
                    msg.getActor().tell(new Messages.Info("  S5>> counter = " + counter, getSelf()));
                } else {
                    System.out.println("  S6>> telling the other actor to close his system");
                    //this should cause the client actor to shutdown the client system
                    msg.getActor().tell(new Messages.Shutdown());
                    
                    System.out.println("  S7>> telling myself to close");
                    // and now close the server system
                    getSelf().tell(new Messages.Shutdown());
                }
            }
        }//Messages.Info
        //
        else {
            System.out.println(getClass().getSimpleName() + " got an unhandled Message");
        }

    }
}
