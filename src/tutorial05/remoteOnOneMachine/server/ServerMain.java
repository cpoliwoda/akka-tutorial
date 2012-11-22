/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial05.remoteOnOneMachine.server;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import tutorial05.remoteOnOneMachine.ConfigFiles;
import tutorial05.remoteOnOneMachine.Constants;

/**
 * This class is used to 
 * - starting a server system 
 * - creating an actor in that system
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
        
        //create new actors
        ActorRef serverActor = system.actorOf(new Props(ServerActor.class), "serverActor");
     
    }
    
}
