/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.supervisor;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import test.supervisor.StorageApi.Entry;
import test.supervisor.StorageApi.Get;
import test.supervisor.StorageApi.Store;

/**
 * Saves key/value pairs to persistent storage when receiving Store message.
 * Replies with current value when receiving Get message. Will throw
 * StorageException if the underlying data store is out of order.
 */
public class Storage extends UntypedActor {

    final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    final DummyDB db = DummyDB.instance;

    @Override
    public void onReceive(Object msg) {
        log.debug("received message {}", msg);
        
        if (msg instanceof Store) {
            Store store = (Store) msg;
            db.save(store.entry.key, store.entry.value);
            
        } else if (msg instanceof Get) {
            Get get = (Get) msg;
            Long value = db.load(get.key);
            getSender().tell(new Entry(get.key, value == null
                    ? Long.valueOf(0L) : value), getSelf());
        } else {
            unhandled(msg);
        }
    }
}//Storage
