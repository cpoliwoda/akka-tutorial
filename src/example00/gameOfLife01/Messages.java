/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package example00.gameOfLife01;

import java.util.ArrayList;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class Messages {
    
    //basic class, no instance should be created
    public interface Message{
    }
    
    public static class Update implements Message{
    }
    
    public static class UpdateDone implements Message{
    }
    
    protected static class Neighbors implements Message{
        private ArrayList<Cell> neighbors;

        public Neighbors(ArrayList<Cell> neighbors) {
            this.neighbors = neighbors;
        }

        /**
         * @return the neighbors
         */
        public ArrayList<Cell> getNeighbors() {
            return neighbors;
        }

        @Override
        public String toString() {
            StringBuffer result =new StringBuffer();
            for (Cell cell : neighbors) {
                result.append(cell+"\n");
            }
            return result.toString();
        }
    }
    
    public static class setNeighbors extends Neighbors {
        public setNeighbors(ArrayList<Cell> neighbors) {
            super(neighbors);
        }
    }
    
    public static class getNeighbors extends Neighbors {
        public getNeighbors(Cell cell) {
            super(cell.getNeighbors());
        }
    }
    
    protected static class Selected implements Message{
        //stores if Seleted should be true or false
        private boolean selected;

        public Selected(boolean selected) {
            this.selected = selected;
        }
        
        @Override
        public String toString() {
            return String.valueOf(selected);
        }

        /**
         * @return the selected
         */
        public boolean isSelected() {
            return selected;
        }
    }
    public static class setSelected extends Selected{
        public setSelected(boolean selected) {
            super(selected);
        }
    }
    
    public static class isSelected extends Selected{
        public isSelected(boolean selected) {
            super(selected);
        }
    }
    
    public static class Start implements Message{
        @Override
        public String toString() {
            return "Start";
        }
    }
    
    public static class Stop implements Message{ 
        @Override
        public String toString() {
            return "Stop";
        }
    }
}
