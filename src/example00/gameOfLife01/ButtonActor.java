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

        System.out.println(getSelf() + " got a message of " + getSender());

        TimeUnit.MILLISECONDS.sleep(100);

        if (o instanceof Messages.UpdateDone) {
            getSender().tell(new Messages.Update(), getSelf());
        
        }else if(o instanceof Messages.Stop){
            System.out.println("Button got STOP msg.");
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
