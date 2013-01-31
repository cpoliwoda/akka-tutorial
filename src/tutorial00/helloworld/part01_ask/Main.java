/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial00.helloworld.part01_ask;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
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

        //create an actor with user choosen name
        ActorRef actor = system.actorOf(new Props(HelloActor.class), "actorA");

        //
        //ask the actor for a message/result
        //
        
        FiniteDuration duration = Duration.create(5, TimeUnit.SECONDS);
        final Timeout timeout = new Timeout(duration);

        // this is one way to "ask" an actor
        Future<Object> future = Patterns.ask(actor, "request", timeout);

        Object result = null;

        try {
            // store the result of the "ask"
            result = Await.result(future, duration);

        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        // use the result
        if (result instanceof String) {

            System.out.println("Response is instanceof String : " + ((String) result));

        } else {
            System.out.println("Response is of unknown Class !!!\n" + result);
        }

        //shutdown the actor system
        system.shutdown();
    }
}
/* http://alvinalexander.com/scala/scala-akka-actors-ask-examples-future-await-timeout-result
 
  // (1) this is one way to "ask" another actor
  implicit val timeout = Timeout(5 seconds)
  val future = myActor ? AskNameMessage
  val result = Await.result(future, timeout.duration).asInstanceOf[String]
  println(result)

  // (2) this is a slightly different way to ask another actor
  val future2: Future[String] = ask(myActor, AskNameMessage).mapTo[String]
  val result2 = Await.result(future2, 1 second)
  println(result2)
 
 
 */