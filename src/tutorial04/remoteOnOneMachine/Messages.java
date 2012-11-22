/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial04.remoteOnOneMachine;

import akka.actor.ActorRef;
import java.io.Serializable;

/**
 * This is a util class were different types of messages can be found.
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class Messages implements Serializable {

    //just to prevend initiation of the class Messages
    private Messages() {
    }
    
    /**
     * A simple message containing of the text message and and actor reference.
     */
    public static class Info implements Serializable {

        private String message;
        private ActorRef actor;

        public Info(String message, ActorRef actor) {
            this.message = message;
            this.actor = actor;
        }

        /**
         * @return the message
         */
        public String getMessage() {
            return message;
        }

        /**
         * @return the actor
         */
        public ActorRef getActor() {
            return actor;
        }
    }//Info

    /**
     * This message should be send if an actor should close the actor system
     * it is running in. 
     * 
     * NOTE: 
     * the reaction on this message depends completly on the implementation
     * of the actor that this message gets.
     */
    public static class Shutdown {
    }
}
