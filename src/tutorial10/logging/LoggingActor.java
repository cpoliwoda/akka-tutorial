/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial10.logging;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.Option;

/**
 * BASED ON: AKKA 2.1.0 DOCUMENTATION PDF chapter 4.3 Logging (Java) page 70.
 * 
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class LoggingActor extends UntypedActor {

        LoggingAdapter log = Logging.getLogger(getContext().system(), this);

        @Override
        public void preStart() {
            log.debug("Starting");
        }

        @Override
        public void preRestart(Throwable reason, Option<Object> message) {
            log.error(reason, "Restarting due to [{}] when processing [{}]",
                    reason.getMessage(), message.isDefined() ? message.get() : "");
        }

        @Override
        public void onReceive(Object message) {
            if (message instanceof Messages.Start) {
                log.info("Received test");
            } else {
                log.warning("Received unknown message: {}", message);
            }
        }
    }
