/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.supervisor;

import akka.actor.ActorRef;

public interface CounterApi {

    public static class UseStorage {

        public final ActorRef storage;

        public UseStorage(ActorRef storage) {
            this.storage = storage;
        }

        public String toString() {
            return String.format("%s(%s)", getClass().getSimpleName(), storage);
        }
    }
}//CounterApi
