/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package example00.gameOfLife01;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.kernel.Bootable;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class Game implements Bootable {

    final static String TITLE = "GameOfLife";
    private ActorSystem system;
    private Cell cell;
    private ArrayList<Cell> cells = new ArrayList<>();

    public Game() {

        //AKKA
        system = ActorSystem.create(TITLE + "System01");

        cell = new Cell(system, 0, 0);

        cells.add(cell);

        for (int i = 1; i < 5; i++) {
            cells.add(new Cell(system, 0, i));
        }

        for (Cell c1 : cells) {

            c1.setNeighbors(getNeighbors(c1));
        }

    }

    /**
     * 
     * @param cell which neighbors should be found
     * @return the neighbors of the cell
     */
    private ArrayList<Cell> getNeighbors(Cell cell) {
        ArrayList<Cell> result = new ArrayList<>();

        int index = cells.indexOf(cell);

        if (index > -1) {//cell is in cells

            if (index + 1 < cells.size()) {//there is a right neighbor
                result.add(cells.get(index + 1));
            }
            if (index - 1 >= 0) {//there is a left neighbor
                result.add(cells.get(index - 1));
            }
        }

        return result;
    }

    @Override
    public void startup() {
        System.out.println("Game is starting.");
    }

    @Override
    public void shutdown() {
        System.out.println("Game is shutting down.");
    }

    /**
     * @return the system
     */
    public ActorSystem getSystem() {
        return system;
    }

    /**
     * @return the actor
     */
    public Cell getCell() {
        return cell;
    }

    /**
     * @return the cells
     */
    public ArrayList<Cell> getCells() {
        return cells;
    }
}
