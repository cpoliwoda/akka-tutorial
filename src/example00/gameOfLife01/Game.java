/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package example00.gameOfLife01;

import akka.actor.ActorSystem;
import akka.kernel.Bootable;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class Game implements Bootable {

    final static String TITLE = "GameOfLife";
    private ActorSystem system;
    private ArrayList<Cell> cells = new ArrayList<>();
    private int row = 0;
    private int column = 0;

    public Game(int row, int column) {

        //AKKA
        system = ActorSystem.create(TITLE + "System01");


        //GAME
        this.row = row;
        this.column = column;

        //create cells
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                cells.add(new Cell(system, i, j));
            }
        }

        //calculate the neighbors and set them for each cell
        for (Cell c1 : cells) {
            c1.setNeighbors(getNeighbors(c1));
        }
        
        //set randomly cells alive / checkbox selected
        Random random = new Random(System.currentTimeMillis());
        
        for (Cell cell : cells) {
            cell.getActor().tell(new Messages.setSelected(random.nextBoolean()));
        }

    }

    /**
     * 
     * @param cell which neighbors should be found
     * @return the neighbors of the cell
     */
    private ArrayList<Cell> getNeighbors(Cell cell) {
        ArrayList<Cell> result = new ArrayList<>();

        //position in the linear storage
        int index = cells.indexOf(cell);

        //the row in the 2d storage
        int cellRow = index / row;

//        System.out.println(cell);

        if (index > -1) {//cell is in cells

            //
            ////side neighbors
            //
            //right neighbor
            if ((index + 1 < cells.size())
                    && inSameRow(cellRow, index + 1)) {

                result.add(cells.get(index + 1));
//                System.out.println("right");
            }
            //left neighbor
            if ((index - 1 >= 0)
                    && inSameRow(cellRow, index - 1)) {

                result.add(cells.get(index - 1));
//                System.out.println("left");
            }
            //
            ////upper neighbors
            //
            //upper right neighbor
            if ((index + 1 - row < cells.size())
                    && (index + 1 - row >= 0)
                    && inSameRow(cellRow - 1, index + 1 - row)) {

                result.add(cells.get(index + 1 - row));
//                System.out.println("upper right");
            }
            //upper neighbor
            if ((index - row < cells.size())
                    && (index - row >= 0)
                    && inSameRow(cellRow - 1, index - row)) {

                result.add(cells.get(index - row));
//                System.out.println("upper");
            }
            //upper left neighbor
            if ((index - 1 - row < cells.size())
                    && (index - 1 - row >= 0)
                    && inSameRow(cellRow - 1, index - 1 - row)) {

                result.add(cells.get(index - 1 - row));
//                System.out.println("upper left");
            }
            //
            ////lower neighbors
            //
            //lower right neighbor
            if ((index + 1 + row < cells.size())
                    && (index + 1 + row >= 0)
                    && inSameRow(cellRow + 1, index + 1 + row)) {

                result.add(cells.get(index + 1 + row));
//                System.out.println("lower right");
            }
            //lower neighbor
            if ((index + row < cells.size())
                    && (index + row >= 0)
                    && inSameRow(cellRow + 1, index + row)) {

                result.add(cells.get(index + row));
//                System.out.println("lower");
            }
            // lower left neighbor
            if ((index - 1 + row < cells.size())
                    && (index - 1 + row >= 0)
                    && inSameRow(cellRow + 1, index - 1 + row)) {

                result.add(cells.get(index - 1 + row));
//                System.out.println("lower left");
            }

        }

        return result;
    }

    /**
     * To be sure check that there are no neighbors of a cell in the 2d storage
     * which are only visible in linear storage. 
     * e.g. for a FALSE neighbor: jumping form the end of a row to the first 
     * entry of the next row is no legal neighbor in the 2d storage
     * 
     * @param rowOfCell the row where the neighbor is found in the 2d storage
     * @param indexOfNeighbor the index of the linear storage of the possible neighbor
     * 
     * @return true if a legal neighbor in the 2d storage was found
     */
    private boolean inSameRow(int rowOfCell, int indexOfNeighbor) {
        boolean result = false;

        int neighborRow = indexOfNeighbor / row;

//        System.out.println("rowOfCell = " + rowOfCell
//                + " indexOfNeighbor = " + indexOfNeighbor
//                + " neighborRow = " + neighborRow);

        if (rowOfCell == neighborRow) {
            result = true;
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
        System.exit(0);
    }

    /**
     * @return the system
     */
    public ActorSystem getSystem() {
        return system;
    }

    /**
     * @return the cells
     */
    public ArrayList<Cell> getCells() {
        return cells;
    }
}
