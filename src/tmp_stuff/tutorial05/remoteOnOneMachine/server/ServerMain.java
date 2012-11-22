/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmp_stuff.tutorial05.remoteOnOneMachine.server;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import tmp_stuff.tutorial05.remoteOnOneMachine.ConfigFiles;
import tmp_stuff.tutorial05.remoteOnOneMachine.Constants;

/**
 * This class is used to 
 * - start a server system 
 * - create an actor in that system
 * - and wait.
 * 
 * Note: ServerMain need to be started before ClientMain is executed.
 * 
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class ServerMain {
    
    public static void main(String[] args) {
        
        //load config
        Config config = ConfigFactory.parseString(ConfigFiles.getServerConfig());
        
        //create an actor system with user choosen system name and configuration
        ActorSystem system = ActorSystem.create(Constants.serverSystemName, config);
        
        //creates a new actor in the server system with user specified actor name
        ActorRef serverActor = system.actorOf(new Props(ServerActor.class), "serverActor");
     
    }
    
}
