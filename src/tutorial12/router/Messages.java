/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial12.router;

import java.io.Serializable;

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

    public static class FibonacciNumber extends EventImpl implements Serializable  {

        private static final long serialVersionUID = 1L;
        private final int nbr;

        public FibonacciNumber(int nbr) {
            this.nbr = nbr;
        }

        public int getNbr() {
            return nbr;
        }
        
    }//FibonacciNumber class
}
