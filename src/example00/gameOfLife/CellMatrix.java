/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package example00.gameOfLife;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.dispatch.Await;
import akka.dispatch.Future;
import akka.pattern.Patterns;
import akka.util.Duration;
import akka.util.Timeout;
import example00.gameOfLife.Events.Position;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import tutorial00.helloworld.part01_ask.Main;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class CellMatrix implements Iterable<CellRow> {

    ArrayList<CellRow> cellRows = null;
    int[] dimension = new int[2];

    CellMatrix(ArrayList<CellRow> cellRows) {
        this.cellRows = cellRows;
        dimension[0] = cellRows.size();
        dimension[1] = cellRows.get(0).size();

    }//CellMatrix(ArrayList<CellRow>)

    public CellMatrix(int rows, int columns) {

        cellRows = makeCellRows(rows, columns); 
        
        dimension[0] = cellRows.size();
        dimension[1] = cellRows.get(0).size();
        
        for (CellRow cellRow : cellRows) {
            for (ActorRef cell : cellRow.getCells()) {
                cell.tell(new Events.Start());
            }
        }

        interconnect(cellRows);
    }
    
    
    
    public CellRow getRow(int i) { return cellRows.get(i);}
    
//    public CellRow apply(int i) { return cellRows.get(i);}

//    public CellMatrix apply(int rows, int columns) {
//
//        CellMatrix cells = new CellMatrix(makeCellRows(rows, columns));
//
//        for (CellRow cellRow : cells.cellRows) {
//            for (Cell cell : cellRow.getCells()) {
//                cell.getSelf().tell(new Events.Start());
//            }
//        }
//
//        interconnect(cells);
//
//        return cells;
//    }//CellMatrix apply(int rows, int columns)
    
    
    public ActorRef getCell(int row, int column){
        return getRow(row).getCell(column);
    }

    @Override
    public Iterator<CellRow> iterator() {
        Iterator<CellRow> it = new Iterator<CellRow>() {
            @Override
            public boolean hasNext() {
                return cellRows.iterator().hasNext();
            }

            @Override
            public CellRow next() {
                return cellRows.iterator().next();
            }

            @Override
            public void remove() {
                cellRows.iterator().remove();
            }
        };

        return it;
    }

    public void run() {
//        def run = {	
//foreach( cell => cell !! Init )  // TELL or ASK ???
//foreach( cell => cell ! Run )   }
        for (CellRow cellRow : cellRows) {
            for (ActorRef cell : cellRow) {
//                cell.tell(new Events.Init()); // TELL?? 
                
                //ASK =>
                Duration duration = Duration.create(5, TimeUnit.SECONDS);
                final Timeout timeout = new Timeout(duration);

                // this is one way to "ask" an actor
                Future<Object> future = Patterns.ask(cell, new Events.Init(), timeout);

                Object result = null;

                try {
                    // store the result of the "ask"
                    result = Await.result(future, duration);

                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }
                //ASK <=
            }
        }
        for (CellRow cellRow : cellRows) {
            for (ActorRef cell : cellRow) {
                cell.tell(new Events.Run());
            }
        }
    }//run()

    public void pause() {//def pause = foreach( cell => cell ! Pause )

        for (CellRow cellRow : cellRows) {
            for (ActorRef cell : cellRow) {
                cell.tell(new Events.Pause());
            }
        }
    }//pause

    public void shutdown() {//def shutdown = foreach( cell => cell.stop )

        for (CellRow cellRow : cellRows) {
            for (ActorRef cell : cellRow) {
                cell.tell(new Events.Stop());
            }
        }
    }//shutdown

    private ArrayList<CellRow> makeCellRows(int rows, int columns) {

        ArrayList<CellRow> result = new ArrayList<>();

        for (int rowNum = 0; rowNum < rows; rowNum++) {
            result.add(new CellRow(rowNum, columns));

        }
        return result;
    }//makeCellRows

    private void interconnect(ArrayList<CellRow> cellRows) {
        for (CellRow cellRow : cellRows) {
            for (ActorRef cell : cellRow.getCells()) {
                connect(cell, cellRows);
            }
        }
    }//interconnect

    private void connect(ActorRef cell, ArrayList<CellRow> cellRows) {
        int rowDimension = dimension[0];
        int columnDimension = dimension[1];

        Duration duration = Duration.create(5, TimeUnit.SECONDS);
        final Timeout timeout = new Timeout(duration);

        // this is one way to "ask" an actor
        Future<Object> future = Patterns.ask(cell, new Events.GetPosition(), timeout);

        Object result = null;

        try {
            // store the result of the "ask"
            result = Await.result(future, duration);

        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        Events.Position position = null;

        if (result instanceof Events.Position) {
            position = (Position) result;
        
            int row = position.getX();
            int column = position.getY();

        ArrayList<ActorRef> neighbours = new ArrayList<>();

        neighbours.add(getCell(row, (column + columnDimension - 1) % columnDimension));// left neighbour
        neighbours.add(getCell(row, (column + 1) % columnDimension));// right neighbour

        neighbours.add(getCell((row + rowDimension - 1) % rowDimension, (column + columnDimension - 1) % columnDimension));// above left neighbour
        neighbours.add(getCell((row + rowDimension - 1) % rowDimension, column));// above neighbour
        neighbours.add(getCell((row + rowDimension - 1) % rowDimension, (column + 1) % columnDimension));// above right neighbour

        neighbours.add(getCell((row + 1) % rowDimension, (column + columnDimension - 1) % columnDimension));// below left neighbour
        neighbours.add(getCell((row + 1) % rowDimension, column));// below neighbour
        neighbours.add(getCell((row + 1) % rowDimension, (column + 1) % columnDimension));// below right neighbour

        cell.tell(new Events.ResetNeighbors(neighbours));
        }
    }//connect
}

 /*
 * ORIGINAL CODE FROM:
 * https://github.com/mariogleichmann/AkkaSamples/tree/master/src/main/scala/com/mgi/akka/gameoflife

package com.mgi.akka.gameoflife

import akka.actor._
import akka.actor.Actor._
import Events._


class CellMatrix( cellRows : List[CellRow] ) extends Traversable[ActorRef] {

def row( i :Int ) = cellRows( i )
def apply( i: Int ) = row( i )

def dimension = ( cellRows.size, cellRows(0).size )

def run = {	
foreach( cell => cell !! Init )
foreach( cell => cell ! Run )
}

def pause = foreach( cell => cell ! Pause )

def shutdown = foreach( cell => cell.stop )

override def foreach[U]( f: ActorRef => U ) {	
cellRows.foreach( row => row.foreach( cell => f( cell ) ) )
}	
}

object CellMatrix{

def apply( rows :Int, columns :Int ) : CellMatrix = {	

val cells = new CellMatrix( makeCellRows( rows, columns ) )

cells.foreach( _.start )

interconnect( cells )

cells
}

def makeCellRows( rows :Int, columns :Int ) : List[CellRow] = {	
( for( rowNum <- 0 to rows - 1 ) yield CellRow( rowNum, columns ) ) toList
}	

def interconnect( cellMatrix :CellMatrix ){	
cellMatrix.foreach( cell => connect( cell, cellMatrix ) )
}

def connect( cell :ActorRef, cellMatrix :CellMatrix ){

val( rowDimension, columnDimension ) = cellMatrix.dimension

val Position( (row, column) ) = ( ( cell !! GetPosition ).get ).asInstanceOf[Position]

var neighbours :List[ActorRef] = Nil

neighbours = cellMatrix( row )( (column + columnDimension - 1) % columnDimension ) :: neighbours // left neighbour
neighbours = cellMatrix( row )( (column + 1) % columnDimension ) :: neighbours // right neighbour

neighbours = cellMatrix( (row + rowDimension - 1) % rowDimension )( (column + columnDimension - 1) % columnDimension ) :: neighbours // above left neighbour
neighbours = cellMatrix( (row + rowDimension - 1) % rowDimension )( column ) :: neighbours // above neighbour
neighbours = cellMatrix( (row + rowDimension - 1) % rowDimension )( (column +1 )% columnDimension ) :: neighbours // above right neighbour

neighbours = cellMatrix( (row + 1) % rowDimension )( (column + columnDimension - 1)% columnDimension ) :: neighbours // below left neighbour
neighbours = cellMatrix( (row + 1) % rowDimension )( column ) :: neighbours // below neighbour
neighbours = cellMatrix( (row + 1) % rowDimension )( (column + 1) % columnDimension ) :: neighbours // below right neighbour

cell ! ResetNeighbors( neighbours )
}
}


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