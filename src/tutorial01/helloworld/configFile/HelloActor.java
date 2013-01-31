/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial01.helloworld.configFile;

import akka.actor.UntypedActor;

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
    }
}
