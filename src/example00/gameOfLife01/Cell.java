/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package example00.gameOfLife01;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JCheckBox;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class Cell {

    private JCheckBox checkBox = null;
    private ActorRef actor = null;
    private int row = 0;
    private int column = 0;
    private ArrayList<Cell> neighbors = null;

    public Cell(ActorSystem system, int row, int column) {
        checkBox = new JCheckBox();
        this.row = row;
        this.column = column;

//        actor = system.actorOf(new Props(CellActor.class));

        actor = system.actorOf(
                new Props(new CellActorFactory(this)),
                "cell:" + row + "," + column);

        checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkBox.isSelected()) {
                    actor.tell(new Messages.isSelected(true));
                } else {
                    actor.tell(new Messages.isSelected(false));
                }
            }
        });
    }

    /**
     * @return the checkBox
     */
    public JCheckBox getCheckBox() {
        return checkBox;
    }

    /**
     * @return the actor
     */
    public ActorRef getActor() {
        return actor;
    }

    @Override
    public String toString() {
        return "Cell[" + row + "," + column + "]: " + checkBox.isSelected();
    }

    /**
     * @return the neighbors
     */
    public ArrayList<Cell> getNeighbors() {
        return neighbors;
    }

    /**
     * @param neighbors the neighbors to set
     */
    public void setNeighbors(ArrayList<Cell> neighbors) {
        this.neighbors = neighbors;
    }
}
