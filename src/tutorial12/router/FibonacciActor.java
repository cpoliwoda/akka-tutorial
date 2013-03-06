/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial12.router;

import akka.actor.UntypedActor;

/**
 * BASED ON: AKKA 2.1.0 DOCUMENTATION PDF chapter 4.9.4 Router (Java) page 111.
 * 
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class FibonacciActor extends UntypedActor {

    @Override
    public void onReceive(Object msg) {
        if (msg instanceof Messages.FibonacciNumber) {
            Messages.FibonacciNumber fibonacciNumber = (Messages.FibonacciNumber) msg;
            
            getSender().tell(fibonacci(fibonacciNumber.getNbr()), getSelf());
            
        } else {
            unhandled(msg);
        }
    }

    private int fibonacci(int n) {
        return fib(n, 1, 0);
    }

    private int fib(int n, int b, int a) {
        if (n == 0) {
            return a;
        }
                // recursion
        return fib(n - 1, a + b, b);
    }

}
