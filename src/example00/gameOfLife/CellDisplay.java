/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package example00.gameOfLife;

import akka.actor.UntypedActor;
import example00.gameOfLife.Events.Alive;
import example00.gameOfLife.Events.Dead;
import example00.gameOfLife.Events.GetPanel;
import java.awt.Color;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class CellDisplay extends UntypedActor {

    private Cell cell = null;
    private FlowPanel panel = null;

    public CellDisplay(Cell cell) {
        this.cell = cell;
        panel = new FlowPanel(cell);
    }
    
    

    @Override
    public void onReceive(Object o) throws Exception {

        if (o instanceof Dead) {
            panel.setBackground( Color.WHITE);
        } else if (o instanceof Alive) {
             panel.setBackground ( Color.BLUE);
        } else if (o instanceof GetPanel) {
            getSender().tell(getPanel());
        }
    }

    /**
     * @return the cell
     */
    public Cell getCell() {
        return cell;
    }

    /**
     * @return the panel
     */
    public FlowPanel getPanel() {
        return panel;
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