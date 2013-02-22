/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial08.restartActor;

import akka.actor.ActorRef;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
class Messages {
    
    /**
     * base interface for all messages in this package
     */
    public static interface Event {        
    }
    
    /**
     * base class for all messages in this package
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
    
    public static class Start extends EventImpl{

        public Start() {
            super("START");
        } 
    }
    
    public static class Stop extends EventImpl{
        
        public Stop() {
            super("STOP");
        }
    }
    
    public static class Resume extends EventImpl{
        public Resume() {
            super("RESUME");
        }
    }
    
    public static class Restart extends EventImpl{
        public Restart() {
            super("RESTART");
        }
    }
    
    public static class CheckTermination extends EventImpl{
        public CheckTermination() {
            super("CHECKTERMINATION");
        }
    }
    
    public static class ReactivateChild extends EventImpl{
        
        private ActorRef child = null;
        
        public ReactivateChild(ActorRef child) {
            super("REACTIVATEchild");
            this.child=child;
        }

        /**
         * @return the child
         */
        public ActorRef getChild() {
            return child;
        }
    }
    
}
