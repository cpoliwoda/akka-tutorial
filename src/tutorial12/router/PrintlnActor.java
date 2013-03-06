/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial12.router;

import akka.actor.UntypedActor;

/**
 * BASED ON: AKKA 2.1.0 DOCUMENTATION PDF chapter 4.9.4 Router (Java) page 111.
 * 
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class PrintlnActor extends UntypedActor {

    @Override
    public void onReceive(Object msg) {
        System.out.println(String.format("Received message ’%s’ in actor %s",
                msg, getSelf().path().name()));
    }
}
