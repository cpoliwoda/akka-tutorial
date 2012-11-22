/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial05.remoteOnOneMachine.client;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import tutorial05.remoteOnOneMachine.ConfigFiles;
import tutorial05.remoteOnOneMachine.Constants;

/**
 * This class is used to 
 * - starting a client system 
 * - get an actor reference of a second remote system
 * - sending this remote actor a message
 * - and close the client system.
 * Note: ServerMain need to be started before ClientMain is executed.
 * 
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class ClientMain {

    public static void main(String[] args) {

        //load config
        Config config = ConfigFactory.parseString(ConfigFiles.getClientConfig());

        //create an actor system with user choosen system name and configuration
        ActorSystem system = ActorSystem.create(Constants.clientSystemName, config);

        //create/get new actors
        //REMOTE
        ////Path need to be of the folling structure:
        //               akka://<actorsystemname>@<hostname>:<port>/<actor path>
        //Notice:
        //<actor path> consists of "user/" + actorName
//        String pathToRemoteServerActor = "akka://serverSystem@127.0.0.1:2552/user/serverActor";
        String pathToRemoteServerActor = "akka://" + Constants.serverSystemName
                + "@" + Constants.serverIP + ":" + Constants.serverPort
                + "/user/serverActor";

        //Notice: the differencen actorFOR() here and actorOF() that was used before
        //see docu: http://doc.akka.io/docs/akka/2.0.3/general/addressing.html#actorof-vs-actorfor
        //
        //actorOf: only ever creates a new actor, and it creates it as a direct 
        //         child of the context on which this method is invoked 
        //         (which may be any actor or actor system).
        //actorFor: only ever looks up an existing actor, i.e. does not create one.

        ActorRef remoteActor = system.actorFor(pathToRemoteServerActor);

        //send the remote actor a message
        System.out.println("sending message to an actor in an other actor system ...");
        remoteActor.tell("remote msg from ClientMain");
        
        //shutdown the client actor system
        System.out.println("shutting down ClientMain !!!");
        system.shutdown();
    }
}
