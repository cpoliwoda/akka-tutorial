/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial10.kill_actor;

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
    public static class EventImpl implements Event {

        protected String message = "--no-message--";

        public EventImpl() {
        }

        public EventImpl(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return message;
        }
    }

    public static class Start extends EventImpl {

        public Start() {
            super("START");
        }
    }
    
    public static class MyKill extends EventImpl {

        public MyKill() {
            super("MyKill");
        }
    }
}
