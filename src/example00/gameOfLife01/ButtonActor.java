/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package example00.gameOfLife01;

import akka.actor.UntypedActor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class ButtonActor extends UntypedActor {

    @Override
    public void onReceive(Object o) throws Exception {

//        System.out.println("receiver = " + getSelf().path().name() + "\n"
//                + "sender = " + getSender().path().name() + "\n"
//                + "message = " + o + "\n");

        if (o instanceof Messages.UpdateDone) {
            //wait before react -> slow down the update process
            TimeUnit.MILLISECONDS.sleep(100);

            getSender().tell(new Messages.Update(), getSelf());

        } else if (o instanceof Messages.Start) {
            System.out.println("Button got START msg.");

        } else if (o instanceof Messages.Stop) {
            System.out.println("Button got STOP msg.");
        
        }else{
            System.out.println("!! unhandled message "+getSelf().path().name());
            unhandled(o);
        }


    }//onReceive
//    public void resume(){
//        getContext().guardian().resume();
//    }
//    
//    public void restart(Throwable throwable ){
//        getContext().guardian().restart(throwable );
//    }
//    
//    public void suspend(){
//        getContext().guardian().suspend();
//    }
}
