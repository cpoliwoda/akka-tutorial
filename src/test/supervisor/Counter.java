/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.supervisor;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import test.supervisor.CounterApi.UseStorage;
import test.supervisor.CounterServiceApi.CurrentCount;
import test.supervisor.CounterServiceApi.Increment;
import test.supervisor.StorageApi.Entry;
import test.supervisor.StorageApi.Store;

/**
 * The in memory count variable that will send current value to the Storage,
 * if there is any storage available at the moment.
 */
public class Counter extends UntypedActor {

    final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    final String key;
    long count;
    ActorRef storage;

    public Counter(String key, long initialValue) {
        this.key = key;
        this.count = initialValue;
    }

    @Override
    public void onReceive(Object msg) {
        log.debug("received message {}", msg);
        if (msg instanceof UseStorage) {
            storage = ((UseStorage) msg).storage;
            storeCount();
        } else if (msg instanceof Increment) {
            count += ((Increment) msg).n;
            storeCount();
        } else if (msg.equals(CounterServiceApi.GetCurrentCount)) {
            getSender().tell(new CurrentCount(key, count), getSelf());
        } else {
            unhandled(msg);
        }
    }

    void storeCount() {
        // Delegate dangerous work, to protect our valuable state.
        // We can continue without storage.
        if (storage != null) {
            storage.tell(new Store(new Entry(key, count)), getSelf());
        }
    }
}//Counter