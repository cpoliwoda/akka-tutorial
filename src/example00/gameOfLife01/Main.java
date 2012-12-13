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

    public static void main(String[] args) {

        Game app = new Game();
        System.out.println("Game created.");

        generateGUI(app);
//        new Main(Game.TITLE, app);

//        app.shutdown();
    }

    private static void generateGUI(final Game app) {

        frame = new JFrame(Game.TITLE);

        Box outter = Box.createVerticalBox();

        Box innerMenu = Box.createHorizontalBox();
        Box innerCells = Box.createHorizontalBox();

        JButton start = new JButton("start");
        JButton stop = new JButton("stop");

        start.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                app.getCell().getActor().tell(new Messages.Start());
            }
        });
        
        stop.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                app.getCell().getActor().tell(new Messages.Stop());
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


        frame.setSize(200, 100);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private static void addCellsToBox(Box box, ArrayList<Cell> cells){
        
        for (Cell cell : cells) {
            box.add(cell.getCheckBox());
        }
    }
}
