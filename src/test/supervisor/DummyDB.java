/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.supervisor;

import java.util.HashMap;
import java.util.Map;
import test.supervisor.StorageApi.StorageException;

public class DummyDB {

    public static final DummyDB instance = new DummyDB();
    private final Map<String, Long> db = new HashMap<String, Long>();

    private DummyDB() {
    }

    public synchronized void save(String key, Long value) throws StorageException {

        //simulate an error
        if (11 <= value && value <= 14) {
            throw new StorageException("Simulated store failure " + value);
        }

        //normal saving
        db.put(key, value);
    }

    public synchronized Long load(String key) throws StorageException {
        return db.get(key);
    }
}//DummyDB
