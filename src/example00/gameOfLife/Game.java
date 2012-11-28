package example00.gameOfLife;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class Game {
    
}

/* 
 * ORIGINAL CODE FROM:
 * https://github.com/mariogleichmann/AkkaSamples/tree/master/src/main/scala/com/mgi/akka/gameoflife
 
  package com.mgi.akka.gameoflife

import scala.swing._

object Game extends SimpleSwingApplication{

val ROWS = 40
val COLUMNS = 40

    var cellMatrix :CellMatrix = null	
var matrixDisplay :MatrixDisplay = null


override def startup(args: Array[String]) {
  
      cellMatrix = CellMatrix( ROWS, COLUMNS )	
 
matrixDisplay = MatrixDisplay( cellMatrix )

super.startup( args )
    }


override def top = new MainFrame {

title = "Game of Life"
resizable = true

contents = new BoxPanel( Orientation.Vertical ){

contents += matrixDisplay.panel

contents += Button( "Run" ){ cellMatrix.run }
contents += Button( "Pause" ){ cellMatrix.pause }
}
}

    override def shutdown() {	
cellMatrix.shutdown
matrixDisplay.shutdown
    }	

}
  
 */