/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial02.helloworld.configFile;

import akka.actor.UntypedActor;

/**
 * An simple actor that prints the messages it gots on the output stream.
 * 
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class ServerActor extends UntypedActor {

    @Override
    public void onReceive(Object o) throws Exception {
        System.out.println(getClass().getSimpleName() + " got mail:");

        //print received string message on output stream
        if (o instanceof String) {
            String msg = (String) o;
            System.out.println("  1>> " + msg);
        }
        
        // print the sender of the "Info" message and
        // send the actor, that is part of the received message, a new message
        if(o instanceof Messages.Info){
            Messages.Info msg = (Messages.Info) o;
            
            System.out.println("  2>> sender = "+ getSender());
            msg.getActor().tell("forwarded mail from "+getSelf()
                    +",\n original send from "+ getSender()
                    +",\n with original message: "+ msg.getMessage());
        }
    }
}
