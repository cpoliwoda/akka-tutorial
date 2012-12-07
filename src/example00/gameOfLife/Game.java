package example00.gameOfLife;

import akka.actor.ActorSystem;
import akka.kernel.Bootable;
import com.sun.java.swing.plaf.gtk.GTKConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class Game implements Bootable {

    static int ROWS = 40;
    static int COLUMNS = 40;
    static int[] dimensions = {ROWS, COLUMNS};
    static CellMatrix cellMatrix; // = new CellMatrix(ROWS, COLUMNS);
    static MatrixDisplay matrixDisplay;// = new MatrixDisplay(cellMatrix, dimensions);
    static ActorSystem system;

    public static void main(String[] args) {

        system = ActorSystem.create();

        cellMatrix = new CellMatrix(ROWS, COLUMNS);

        matrixDisplay = new MatrixDisplay(cellMatrix, dimensions);

        JFrame frame = new JFrame("Game of Life");
        frame.setResizable(true);

        frame.add(matrixDisplay.gridPanel);

        JButton run = new JButton();
        run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cellMatrix.run();
            }
        });

        JButton pause = new JButton();
        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cellMatrix.pause();
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }//main

    @Override
    public void startup() {
    }

    @Override
    public void shutdown() {
        cellMatrix.shutdown();
//    matrixDisplay.shutdown();
    }
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