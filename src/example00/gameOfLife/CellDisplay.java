/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package example00.gameOfLife;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.dispatch.Await;
import akka.dispatch.Future;
import akka.pattern.Patterns;
import akka.util.Duration;
import akka.util.Timeout;
import example00.gameOfLife.Events.*;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class CellDisplay extends UntypedActor implements MouseListener {

    ActorRef cell;
    Border border = LineBorder.createBlackLineBorder();
    Color background = Color.WHITE;
//            Label contents = new Label("");

    public CellDisplay(ActorRef cell) {
        this.cell = cell;
    }

    @Override
    public void onReceive(Object o) throws Exception {

        if (o instanceof Dead) {
            background = Color.WHITE;
        } else if (o instanceof Alive) {
            background = Color.BLUE;
        } else if (o instanceof GetPanel) {
            getSender().tell(this);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        //java
        Duration duration = Duration.create(1, TimeUnit.SECONDS);
        Timeout timeout = new Timeout(duration);
        Future<Object> future = Patterns.ask(cell, new GetState(), timeout);

        Object response = null;

            try {
                response = Await.result(future, duration);

            } catch (Exception ex) {
                Logger.getLogger(Cell.class.getName()).log(Level.SEVERE, null, ex);
            }

        CellState oldState = null;

        if (response instanceof CellState) {
            oldState = (CellState) response;

            cell.tell(oldState.isAlive() ? new ResetDead() : new ResetAlive());
            background = oldState.isAlive() ? Color.WHITE : Color.BLUE;
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
}

/* 
 * ORIGINAL CODE FROM:
 * https://github.com/mariogleichmann/AkkaSamples/tree/master/src/main/scala/com/mgi/akka/gameoflife
 
 package com.mgi.akka.gameoflife

import scala.swing._
import scala.swing.event._
import javax.swing.border.LineBorder
import java.awt.Color
import akka.actor._
import Events._

class CellDisplay( cell :ActorRef ) extends Actor{

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

  def receive = {
 
  case Dead( _ ) =>
  panel.background = Color.WHITE
 
  case Alive( _ ) =>
  panel.background = Color.BLUE
 
  case GetPanel => {
  self reply panel
  }
  }
  
  
}
 
 */