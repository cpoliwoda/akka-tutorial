/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package example00.gameOfLife01;

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
public class Main {//extends JFrame {

    private static JFrame frame;
    
    private static int row =3;
    private static int column =3;

    public static void main(String[] args) {

        Game app = new Game(row,column);// create (row * column) cells
        System.out.println("Game created.");

        generateGUI(app);
//        new Main(Game.TITLE, app);

//        app.shutdown();
    }

    private static void generateGUI(final Game app) {

        frame = new JFrame(Game.TITLE);

        Box outter = Box.createVerticalBox();

        Box innerMenu = Box.createHorizontalBox();
        Box innerCells = Box.createVerticalBox();

        JButton start = new JButton("start");
        JButton stop = new JButton("stop");

        start.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
//                app.getCell().getActor().tell(new Messages.Start());
                System.out.println("START-BUTTON");
            }
        });
        
        stop.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
//                app.getCell().getActor().tell(new Messages.Stop());
                System.out.println("STOP-BUTTON");
            }
        });
        
        innerMenu.add(start);
        innerMenu.add(stop);

//        innerCells.add(new JCheckBox());
//        innerCells.add(app.getCell().getCheckBox());
        addCellsToBox(innerCells, app.getCells());
        
        outter.add(innerMenu);
        outter.add(innerCells);

        frame.add(outter);


//        frame.setSize(200, 100);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private static void addCellsToBox(Box box, ArrayList<Cell> cells){
        
//        for (Cell cell : cells) {
//            box.add(cell.getCheckBox());
//        }
        
        for (int i = 0; i < row; i++) {
            Box rowBox =  Box.createHorizontalBox();
            
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
    private static int getListIndex(int rowPos, int colPos){
        return rowPos * row +colPos;
    } 
}
