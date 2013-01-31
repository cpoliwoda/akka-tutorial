/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial00.helloworld.part02_ask;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.Futures;
import akka.dispatch.Mapper;
import akka.pattern.Patterns;
import akka.util.Timeout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;

/** 
 * BASED ON CODE FROM: http://doc.akka.io/docs/akka/2.0.3/java/untyped-actors.html
 * TEXT FORM ABOVE NAMED SITE.
 * 
 * This example demonstrates ask together with the pipe pattern on futures, 
 * because this is likely to be a common combination. Please note that all of 
 * the above is completely non-blocking and asynchronous: ask produces a Future, 
 * two of which are composed into a new future using the Futures.sequence and 
 * map methods and then pipe installs an onComplete-handler on the future to 
 * effect the submission of the aggregated Result to another actor.
 * 
 * Using ask will send a message to the receiving Actor as with tell, and the 
 * receiving actor must reply with getSender().tell(reply) in order to complete 
 * the returned Future with a value. The ask operation involves creating an 
 * internal actor for handling this reply, which needs to have a timeout after 
 * which it is destroyed in order not to leak resources; see more below.
 * 
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class Main {

    public static void main(String[] args) {
        //create an actor system with Name
        ActorSystem system = ActorSystem.create("AskSystem");

        //create new actors
        ActorRef actorA = system.actorOf(new Props(HelloActor.class), "actorA");
        ActorRef actorB = system.actorOf(new Props(HelloActor.class), "actorB");
        ActorRef actorC = system.actorOf(new Props(HelloActor.class), "actorC");

        //ask the actors for a message/result


//        //// START Code for akka 2.0.3 NEED TO be changed
//        
//        final Timeout t = new Timeout(FiniteDuration.create(5, TimeUnit.SECONDS));
//
//        final ArrayList<Future<Object>> futures = new ArrayList<Future<Object>>();
//        futures.add(Patterns.ask(actorA, new Result(0, "zero"), 1000)); // using 1000ms timeout
//        futures.add(Patterns.ask(actorB, "request", t)); // using timeout from above
//
//        final Future<Iterable<Object>> aggregate = Futures.sequence(futures, system.dispatcher());

//        
//        final Future<Result> transformed = aggregate.map(new Mapper<Iterable<Object>, Result>() {
//            @Override
//            public Result apply(Iterable<Object> coll) {
//                final Iterator<Object> it = coll.iterator();
//
//                final String s = (String) it.next();
//                final int x = (Integer) it.next();
//
//                return new Result(x, s);
//            }
//        });
//
//        pipe(transformed).to(actorC);
//        //// END Code for akka 2.0.3

//////        START akka 2.1.0

        final Timeout t = new Timeout(FiniteDuration.create(5, TimeUnit.SECONDS));

        final ArrayList<Future<Object>> futures = new ArrayList<Future<Object>>();
        futures.add(Patterns.ask(actorA, "request", 1000)); // using 1000ms timeout
        futures.add(Patterns.ask(actorB, "another request", t)); // using timeout from above

        final Future<Iterable<Object>> aggregate =
                Futures.sequence(futures, system.dispatcher());

        Mapper mapper = new Mapper<Iterable<Object>, Result>() {
            
            @Override
            public Result apply(Iterable<Object> coll) {
                final Iterator<Object> it = coll.iterator();
                final String s = (String) it.next();
                final int x = (Integer) it.next();
                return new Result(x, s);
            }
        };
        
        final Future<Result> transformed = aggregate.map(mapper, system.dispatcher());

        Patterns.pipe(transformed, system.dispatcher()).to(actorC);

//////        END akka 2.1.0

        //shutdown the actor system
        system.shutdown();
        system.awaitTermination();
    }
}
