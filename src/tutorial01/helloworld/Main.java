/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial01.helloworld;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class Main {
    
    public static void main(String[] args) {
        
        //load config
        Config config = ConfigFactory.parseString(ConfigFiles.getSimpleLocalActorConfig());
        
        //create an actor system with user choosen system name and configuration
        ActorSystem system = ActorSystem.create("helloSystem", config);
        
        //create a new actor
        ActorRef actor = system.actorOf(new Props(HelloActor.class));
        
        //send the actor a message
        actor.tell(" - Hi - ");
        
        //shutdown the actor system
        system.shutdown();
    }
    
}
