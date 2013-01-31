/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial02.helloworld.configFile;

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
        
        //create new actors
        ActorRef actor1 = system.actorOf(new Props(ServerActor.class), "actor1");
        ActorRef actor2 = system.actorOf(new Props(ServerActor.class), "actor2");
        
        //send the first actor a message
        //which will be forwarded to the second one
        actor1.tell(new Messages.Info("be happy", actor2));
        
        //shutdown the actor system
        system.shutdown();
    }
    
}
