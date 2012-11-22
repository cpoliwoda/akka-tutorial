/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmp_stuff.tutorial05.remoteOnOneMachine.server;

import akka.actor.UntypedActor;
import tmp_stuff.tutorial05.remoteOnOneMachine.Messages;

/**
 * An simple actor that prints the messages it gots on the output stream.
 * 
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class ServerActor extends UntypedActor {

    private static int counter = 0;
    private static int counterMax = 5;

    @Override
    public void onReceive(Object o) throws Exception {
        System.out.println(getClass().getSimpleName() + " got mail:");

        //print received string message on output stream
        if (o instanceof String) {
            String msg = (String) o;
            System.out.println("  1>> " + msg);
        }//String
        //
        //shutdown the system in which the actor is running
        else if (o instanceof Messages.Shutdown) {
            System.out.println("  2>> system is shutting down now !!!");
            getContext().system().shutdown();
        }//Messages.Shutdown
        //
        else if (o instanceof Messages.Info) {
            Messages.Info msg = (Messages.Info) o;

            if (msg.getActor() == null) {
                System.err.println("  3>> actor is NULL in Messages.Info");
            } else {
                counter++;
                if (counter < counterMax) {
                    msg.getActor().tell(new Messages.Info("  4>> counter = "+counter, getSelf()));
                } else {
                    //this should cause the client actor to shutdown the client system
                    msg.getActor().tell(new Messages.Shutdown());
                    // and now close the server system
                    getSelf().tell(new Messages.Shutdown());
                }
            }
    }//Messages.Info
    //

        else {
            System.out.println(getClass().getSimpleName() + " got an unhandled Message");
//            unhandled(o);
    }
}
}
