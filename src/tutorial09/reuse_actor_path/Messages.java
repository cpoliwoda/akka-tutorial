/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial09.reuse_actor_path;

import akka.actor.ActorRef;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
class Messages {
    
    /**
     * base interface for all messages in this package (tutorial07.restart)
     */
    public static interface Event {        
    }
    
    /**
     * base class for all messages in this package (tutorial07.restart)
     */
    public static class EventImpl extends Throwable implements Event{
        protected String message = "--no-message--";

        public EventImpl() {
        }

        public EventImpl(String message) {
            super(message);
            this.message = message;
        }

        @Override
        public String toString() {
            return message;
        }
    }
    
    public static class Observe extends EventImpl{

        private ActorRef actor = null;

        public Observe(ActorRef actor) {
            super("Observe: "
                    +actor.getClass().getSimpleName()
                    +"."+actor.path().name());
            this.actor = actor;
        } 

        /**
         * @return the actor
         */
        public ActorRef getActor() {
            return actor;
        }
    }
    
    public static class IsTerminated extends EventImpl{

        public IsTerminated() {
            super("IsTerminated");
        }
    }
}
