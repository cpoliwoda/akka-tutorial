/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package example00.gameOfLife;

import akka.actor.ActorRef;
import akka.dispatch.Await;
import akka.dispatch.Future;
import akka.pattern.Patterns;
import akka.util.Duration;
import akka.util.Timeout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class FlowPanel extends JPanel implements MouseListener {

    ActorRef cell = null;
//    Border border = null;
//    Color background = null;
//            Label contents = null;

    public FlowPanel(ActorRef cell) {
        super(new FlowLayout());

        this.cell = cell;
        setBorder( LineBorder.createBlackLineBorder());
        setBackground(Color.WHITE);
        setName("");//     contents = new Label("");
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        //java
        Duration duration = Duration.create(1, TimeUnit.SECONDS);
        Timeout timeout = new Timeout(duration);
        Future<Object> future = Patterns.ask(cell, new Events.GetState(), timeout);

        Object response = null;

        try {
            response = Await.result(future, duration);

        } catch (Exception ex) {
            Logger.getLogger(Cell.class.getName()).log(Level.SEVERE, null, ex);
        }

        Events.CellState oldState = null;

        if (response instanceof Events.CellState) {
            oldState = (Events.CellState) response;

            cell.tell(oldState.isAlive() ? new Events.ResetDead() : new Events.ResetAlive());
            setBackground(oldState.isAlive() ? Color.WHITE : Color.BLUE);
        }


//            //scala
//                val oldState :CellState = ( ( cell !! GetState ).get ).asInstanceOf[CellState]
//                cell ! ( if( oldState.isAlive ) ResetDead else ResetAlive )
//                background = if( oldState.isAlive ) Color.WHITE else Color.BLUE
    }

    @Override
    public void mousePressed(MouseEvent e) {
//            throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//            throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
//            throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseExited(MouseEvent e) {
//            throw new UnsupportedOperationException("Not supported yet.");
    }
}//FlowPanel


/*
 
   val panel = new FlowPanel {

border = LineBorder.createBlackLineBorder
            background = Color.WHITE
            contents += new Label("")

listenTo( mouse.clicks )

reactions += {

case e: MouseClicked => {

                val oldState :CellState = ( ( cell !! GetState ).get ).asInstanceOf[CellState]
               
                cell ! ( if( oldState.isAlive ) ResetDead else ResetAlive )
                
                background = if( oldState.isAlive ) Color.WHITE else Color.BLUE
}
}
}
 
 
 
 
 */