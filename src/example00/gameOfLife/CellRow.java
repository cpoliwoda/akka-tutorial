/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package example00.gameOfLife;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActorFactory;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class CellRow implements Iterable<ActorRef> {

    private ArrayList<ActorRef> cells = null;

    public CellRow(final int row , int columns ) {
        cells = new ArrayList<ActorRef>();
        
        for (int col = 0; col < columns; col++) {
//            //TRY01
//            cells.add(new Cell(row , col));
//            //TRY02
//            cells.add(Game.system.actorOf(new Props(new UntypedActorFactory() {
//
//                @Override
//                public Actor create() throws Exception {
//                    return new Cell(row , col);
//                }
//            }),"cell "+row+","+col));
            //TRY03
            cells.add(Game.system.actorOf(new Props(new CellFactory(row, col)),"C:"+row+","+col));
            
        }
    }
    
    @Override
    public Iterator<ActorRef> iterator() {
        Iterator<ActorRef> it = new Iterator<ActorRef>() {
            @Override
            public boolean hasNext() {
                return getCells().iterator().hasNext();
            }

            @Override
            public ActorRef next() {
                return getCells().iterator().next();
            }

            @Override
            public void remove() {
                getCells().iterator().remove();
            }
        };

        return it;
    }

    public ActorRef getCell(int i) {
        return getCells().get(i);
    }
    
    public int size(){
        return getCells().size();
    }

    /**
     * @return the cells
     */
    public ArrayList<ActorRef> getCells() {
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