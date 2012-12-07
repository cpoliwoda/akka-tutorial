/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package example00.gameOfLife;

import akka.actor.ActorRef;
import java.util.ArrayList;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class Events {

    public static class Event {
        // no instance should be created for this class
        private Event() {
        }    
    }
    
    /* START additional added because of useage in Cell*/
    public static class Start extends Event {
    }
    public static class Stop extends Event {
    }
    /* END additional added because of useage in Cell*/
    
    // runs the game (after init phase)
    public static class Run extends Event {
    }

    // pauses the game (after game has started to run)
    public static class Pause extends Event {
    }

    // initializes / resets a cell to be dead
    public static class ResetDead extends Event {
    }

    // initializes / resets a cell to be alive
    public static class ResetAlive extends Event {
    }

    // initializes / resets a cell with its neighbor cells (the cell depends on)
    public static class ResetNeighbors extends Event {

        private ArrayList<ActorRef> neighborCells;

        public ResetNeighbors(ArrayList<ActorRef> neighborCells) {
            this.neighborCells = neighborCells;
        }

        /**
         * @return the neighborCells
         */
        public ArrayList<ActorRef> getNeighborCells() {
            return neighborCells;
        }
    }

    // adds a listener to a cell (for getting informed by state changes of a cell)
    public static class AddListener extends Event {

        private ActorRef listener;

        public AddListener(ActorRef listener) {
            this.listener = listener;
        }

        /**
         * @return the listener
         */
        public ActorRef getListener() {
            return listener;
        }
    }

    // initializes a cell - forces a cell to initially calculate its state due to the initial state of its neighbors
    public static class Init extends Event {
    }

    // asks a cell for its current neighbors which are alive
    public static class NeighborsAlive extends Event {

        private int count;

        public NeighborsAlive(int count) {
            this.count = count;
        }

        /**
         * @return the count
         */
        public int getCount() {
            return count;
        }
    }

    // asks a cell for its current state
    public static class GetState extends Event {
    }

    // asks a cell for its position within a cell matrix
    public static class GetPosition extends Event {
    }

    // Answer to a request for a cells position
    public static class Position extends Event {

        private int x;
        private int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Position(Position position) {
            this.x = position.getX();
            this.y = position.getY();
        }

        /**
         * @return the x
         */
        public int getX() {
            return x;
        }

        /**
         * @return the y
         */
        public int getY() {
            return y;
        }

        @Override
        public String toString() {
            return "[ " + x + " ][ " + y + " ]";
        }
    }

    /** common base class for cell state
     */ 
    public static class CellState extends Event {

        private boolean alive;
        private Cell cell;

        public CellState(boolean alive, Cell cell) {
            this.alive = alive;
            this.cell = cell;
        }

        public CellState(CellState state) {
            this(state.isAlive(), state.getCell());
        }
        

        /**
         * @return the alive
         */
        public boolean isAlive() {
            return alive;
        }

        /**
         * @return the cell
         */
        public Cell getCell() {
            return cell;
        }
    }

    // informs a cell of the death of one of its neighbors
    public static class Dead extends CellState {

        public Dead(Cell cell) {
            super(false,cell);
        }
    }

    // informs a cell of the (re)birth of one of its neighbors
    public static class Alive extends CellState {

        public Alive(Cell cell) {
            super(true,cell);
        }
    }

    // asks a CellDisplay about its (Swing) panel 
    // (should go into a separate Event object for collecting the 'interface' of Actor 'CellDisplay'
    public static class GetPanel extends Event {
    }
}

 /*
 * ORIGINAL CODE FROM:
 * https://github.com/mariogleichmann/AkkaSamples/tree/master/src/main/scala/com/mgi/akka/gameoflife
 
  package com.mgi.akka.gameoflife

import akka.actor._

object Events {

  sealed case class Event

  // runs the game (after init phase)
  case object Run extends Event

  // pauses the game (after game has started to run)
  case object Pause extends Event

  // initializes / resets a cell to be dead
  case object ResetDead extends Event
  
  // initializes / resets a cell to be alive
  case object ResetAlive extends Event
  
  // initializes / resets a cell with its neighbor cells (the cell depends on)
  case class ResetNeighbors( neighborCells :List[ActorRef] ) extends Event
  
  // adds a listener to a cell (for getting informed by state changes of a cell)
  case class AddListener( listener :ActorRef )
  
  // initializes a cell - forces a cell to initially calculate its state due to the initial state of its neighbors
  case object Init extends Event
  
  // asks a cell for its current neighbors which are alive
  case class NeighborsAlive( count :Int ) extends Event
  
  // asks a cell for its current state
  case object GetState extends Event
  
  // asks a cell for its position within a cell matrix
  case object GetPosition extends Event
  
  // Answer to a request for a cells position
  case class Position( position :(Int,Int) )
  
  
  // common base class for cell state
  abstract case class CellState extends Event{
def isAlive : Boolean
  }
  
  // informs a cell of the death of one of its neighbors
  case class Dead( cell :Cell ) extends CellState{
override def isAlive = false
  }
  
  // informs a cell of the (re)birth of one of its neighbors
  case class Alive( cell :Cell ) extends CellState{
override def isAlive = true
  }
  
  
  // asks a CellDisplay about its (Swing) panel (should go into a separate Event object for collecting the 'interface' of Actor 'CellDisplay'
  case object GetPanel extends Event
  
}
  
 */