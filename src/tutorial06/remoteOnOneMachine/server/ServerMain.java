/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial06.remoteOnOneMachine.server;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import tutorial06.remoteOnOneMachine.ConfigFiles;
import tutorial06.remoteOnOneMachine.Constants;
import tutorial06.remoteOnOneMachine.Messages;

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

        //create local in this system a new actor
        ActorRef serverActor = system.actorOf(new Props(ServerActor.class), "serverActor");

        //look remote for an actor
        //REMOTE
        ////Path need to be of the folling structure:
        //               akka://<actorsystemname>@<hostname>:<port>/<actor path>
        //Notice:
        //<actor path> consists of "user/" + actorName
        String pathToRemoteClientActor = "akka://" + Constants.clientSystemName
                + "@" + Constants.clientIP + ":" + Constants.clientPort
                + "/user/clientActor";

        //Notice the difference:
        //actorFOR() here and actorOF() that was used before
        //see docu: http://doc.akka.io/docs/akka/2.0.3/general/addressing.html#actorof-vs-actorfor
        //
        //actorOf: only ever creates a new actor, and it creates it as a direct 
        //         child of the context on which this method is invoked 
        //         (which may be any actor or actor system).
        //actorFor: only ever looks up an existing actor, i.e. does not create one.

        ActorRef remoteClientActor = system.actorFor(pathToRemoteClientActor);

        System.out.println("Server system is running ...");

        //waiting for second/remote system to be startet by user
        for (int i = 0; i < 10; i++) {
            System.out.println("Waited " + i + " seconds.");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(ServerMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (!remoteClientActor.isTerminated() && !system.isTerminated()) {
            //send the remote actor a message
            System.out.println("sending message to an actor in an other actor system ...");
            remoteClientActor.tell(new Messages.Info("msg from SERVER-MAIN", serverActor));
        }
    }
}
