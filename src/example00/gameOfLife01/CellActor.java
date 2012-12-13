/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package example00.gameOfLife01;

import akka.actor.UntypedActor;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class CellActor extends UntypedActor {

    // the cell this actor belongs to
    Cell cell;
    
    public CellActor(Cell cell) {
        this.cell = cell;
    }
    
    @Override
    public void onReceive(Object o) throws Exception {
        
        if (o instanceof Messages.Message) {
            
            if (o instanceof Messages.isSelected) {
                Messages.isSelected msg = (Messages.isSelected) o;
                System.out.println("Is checkbox selected: " + msg.isSelected());
                
                getSelf().tell(new Messages.getNeighbors(cell));
                
            } else if (o instanceof Messages.setSelected) {
                Messages.setSelected msg = (Messages.setSelected) o;
                cell.getCheckBox().setSelected(msg.isSelected());
                
            } else if (o instanceof Messages.Start) {
                Messages.Start msg = (Messages.Start) o;
                System.out.println(msg);
                
            } else if (o instanceof Messages.Stop) {
                Messages.Stop msg = (Messages.Stop) o;
                System.out.println(msg);
                
            } else if (o instanceof Messages.getNeighbors) {
                Messages.getNeighbors msg = (Messages.getNeighbors) o;
                
                System.out.println("Neighbors of " + cell + " are:");
                for (Cell c : msg.getNeighbors()) {
                    System.out.println(c);
                }
            } else if (o instanceof Messages.setNeighbors) {
                Messages.setNeighbors msg = (Messages.setNeighbors) o;
                cell.setNeighbors(msg.getNeighbors());
            }
            
        } else {
            System.out.println(" Unknown Message !!!");
        }
    }
}
