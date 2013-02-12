/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial07.restartActor;

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
    
}
