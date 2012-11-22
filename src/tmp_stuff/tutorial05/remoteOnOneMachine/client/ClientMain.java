/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmp_stuff.tutorial05.remoteOnOneMachine.client;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import tmp_stuff.tutorial05.remoteOnOneMachine.ConfigFiles;
import tmp_stuff.tutorial05.remoteOnOneMachine.Constants;
import tmp_stuff.tutorial05.remoteOnOneMachine.Messages;

/**
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
        //LOCAL
        ActorRef clientActor = system.actorOf(new Props(ClientActor.class), "clientActor");

        //REMOTE
        ////Path need to be of the folling structure:
        //               akka://<actorsystemname>@<hostname>:<port>/<actor path>
        //Notice:
        //<actor path> consists of "user/" + actorName
        String pathToRemoteServerActor = "akka://serverSystem@127.0.0.1:2552/user/serverActor";
//        String pathToRemoteServerActor = "akka://" + Constants.serverSystemName
//                + "@" + Constants.serverIP + ":" + Constants.serverPort
//                + "/user/serverActor";

        //Notice: the differencen actorFOR() here and actorOF() that was used before
        //see docu: http://doc.akka.io/docs/akka/2.0.3/general/addressing.html#actorof-vs-actorfor
        //
        //actorOf: only ever creates a new actor, and it creates it as a direct 
        //         child of the context on which this method is invoked 
        //         (which may be any actor or actor system).
        //actorFor: only ever looks up an existing actor, i.e. does not create one.

        ActorRef remoteActor = system.actorFor(pathToRemoteServerActor);

        //send the remote actor a message
        remoteActor.tell(new Messages.Start());

    }
}
