/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package example00.gameOfLife;

import akka.actor.ActorRef;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class CellRow implements Iterable<Cell> {

    private ArrayList<Cell> cells = null;

    public CellRow(int row , int columns ) {
        cells = new ArrayList<Cell>();
        
        for (int col = 0; col < columns; col++) {
            cells.add(new Cell(row , col));
        }
    }
    
    @Override
    public Iterator<Cell> iterator() {
        Iterator<Cell> it = new Iterator<Cell>() {
            @Override
            public boolean hasNext() {
                return getCells().iterator().hasNext();
            }

            @Override
            public Cell next() {
                return getCells().iterator().next();
            }

            @Override
            public void remove() {
                getCells().iterator().remove();
            }
        };

        return it;
    }

    public Cell getCell(int i) {
        return getCells().get(i);
    }
    
    public int size(){
        return getCells().size();
    }

    /**
     * @return the cells
     */
    public ArrayList<Cell> getCells() {
        return cells;
    }
}

/*
 
 class CellRow( cells : List[ActorRef] ) extends Traversable[ActorRef]{

def cell( i :Int ) : ActorRef = cells( i )
def apply( i :Int ) = cell( i )

def start = foreach( _.start )

override def foreach[U]( f: ActorRef => U ) {	
cells.foreach( cell => f( cell ) )
}
}

object CellRow{

def apply( row :Int, columns :Int ) : CellRow = {	
new CellRow( ( for ( column <- 0 to columns - 1 ) yield actorOf( new Cell( row, column ) ) ) toList )	
}
}
 
 */