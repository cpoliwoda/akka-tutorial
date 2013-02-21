/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package example00.gameOfLife01;

import akka.actor.ActorRef;
import akka.actor.Props;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class Main {

//    GUI
    private static JFrame frame;
    private static JButton startButton = null;
    private static JButton stopButton = null;
//    Properties
    private static int row = 9;
    private static int column = 9;
//    Logic
    private static ActorRef gameActor = null;

    public static void main(String[] args) {

        Game app = new Game(row, column);
        System.out.println(">> Game created.");

        generateGUI(app);
        System.out.println(">> GUI created.");
        
        generateLogic(app);
        System.out.println(">> Logic created.");

//        app.shutdown();
    }

    private static void generateGUI(final Game app) {

        frame = new JFrame(Game.TITLE);

        Box outter = Box.createVerticalBox();

        Box innerMenu = Box.createHorizontalBox();
        Box innerCells = Box.createVerticalBox();

        startButton = new JButton("start");
        stopButton = new JButton("stop");

        innerMenu.add(startButton);
        innerMenu.add(stopButton);

        addCellsToBox(innerCells, app.getCells());

        outter.add(innerMenu);
        outter.add(innerCells);

        frame.add(outter);

        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }//generateGUI()
    
    private static void generateLogic(final Game app) {
        gameActor = app.getSystem().actorOf(new Props(GameActor.class), "gameActor");
        
        //init inner variable game in GameActor
        gameActor.tell(app, gameActor);
        
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("START-BUTTON");

                gameActor.tell(new Messages.Start(), gameActor);

            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("STOP-BUTTON");
                
                gameActor.tell(new Messages.Stop(), gameActor);
            }
        });
        
    }//generateLogic()

    private static void addCellsToBox(Box box, ArrayList<Cell> cells) {

        for (int i = 0; i < row; i++) {
            Box rowBox = Box.createHorizontalBox();

            for (int j = 0; j < column; j++) {

                rowBox.add(cells.get(getListIndex(i, j)).getCheckBox());

            }

            box.add(rowBox);
        }
    }//addCellsToBox

    /**
     * helper method to map from 2d in 1d
     * @param rowPos
     * @param colPos
     * @return the index in 1d
     */
    private static int getListIndex(int rowPos, int colPos) {
        return rowPos * row + colPos;
    }

    
}
