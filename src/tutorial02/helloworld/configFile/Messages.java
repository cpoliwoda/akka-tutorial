/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial02.helloworld.configFile;

import akka.actor.ActorRef;
import java.io.Serializable;

/**
 * This is a util class were different types of messages can be found.
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class Messages implements Serializable{
    
    /**
     * A simple message containing of the text message and and actor reference.
     */
    public static class Info implements Serializable{
        
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
        
        
    }
    
}
