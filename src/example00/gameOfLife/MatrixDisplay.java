/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package example00.gameOfLife;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.dispatch.Await;
import akka.dispatch.Future;
import akka.pattern.Patterns;
import akka.util.Duration;
import akka.util.Timeout;
import java.awt.GridLayout;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class MatrixDisplay {

    int rows = 0;
    int columns = 0;
    GridPanel gridPanel=null;

    public MatrixDisplay(CellMatrix cellMatrix, int[] dimension) {
        rows = dimension[0];
        columns = dimension[1];

         gridPanel = new GridPanel(cellMatrix, rows, columns);

    }

    void displaysFor(CellMatrix cellMatrix) {
        Cell display = null;
        for (CellRow cellRow : cellMatrix) {
            for (Cell cell : cellRow) {

                System.out.println(cell);

                cell.getSelf().tell(new Events.Start());
                displayFor(cell);
            }
        }
    }//displaysFor

    CellDisplay displayFor(Cell cell) {

        CellDisplay display = new CellDisplay(cell);
        ActorRef actor = display.getSelf();
        cell.getSelf().tell(new Events.AddListener(actor));
        
        return display;
    }
}//MatrixDisplay


/*
 * ORIGINAL CODE FROM:
 * https://github.com/mariogleichmann/AkkaSamples/tree/master/src/main/scala/com/mgi/akka/gameoflife
 
  package com.mgi.akka.gameoflife

import akka.actor._
import akka.actor.Actor._
import scala.swing._
import Events._

class MatrixDisplay( cellDisplays : Traversable[ActorRef], dimension :(Int,Int) ) {

val (rows, columns) = dimension

lazy val panel = new GridPanel( rows, columns ) {
cellDisplays.foreach( cellDisplay => contents += ( (cellDisplay !! GetPanel).get ).asInstanceOf[FlowPanel] )
}

def shutdown = cellDisplays.foreach( display => display.stop() )
}

object MatrixDisplay{

def apply( cells :CellMatrix ) = {	
new MatrixDisplay( displaysFor( cells ), cells.dimension )
}

def displaysFor( cellMatrix : CellMatrix ) : Traversable[ActorRef] = {	
cellMatrix.map( cell => displayFor( cell ) )
}

def displayFor( cell :ActorRef ) : ActorRef = {

val display = actorOf( new CellDisplay( cell ) ).start

cell ! AddListener( display )

display
}
}
  
 */