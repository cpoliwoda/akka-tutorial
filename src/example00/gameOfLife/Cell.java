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
import example00.gameOfLife.Events.AddListener;
import example00.gameOfLife.Events.Alive;
import example00.gameOfLife.Events.CellState;
import example00.gameOfLife.Events.Dead;
import example00.gameOfLife.Events.GetPosition;
import example00.gameOfLife.Events.GetState;
import example00.gameOfLife.Events.Init;
import example00.gameOfLife.Events.NeighborsAlive;
import example00.gameOfLife.Events.Pause;
import example00.gameOfLife.Events.Position;
import example00.gameOfLife.Events.ResetAlive;
import example00.gameOfLife.Events.ResetDead;
import example00.gameOfLife.Events.ResetNeighbors;
import example00.gameOfLife.Events.Run;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class Cell extends UntypedActor {

    Position position;
    CellState state = new Dead(this);
    ArrayList<ActorRef> neighborCells = null;
    int neighborCellsAlive = 0;
    ArrayList<ActorRef> listeners = null;
    boolean initializing = true;
    boolean running = false;
    boolean receive = false;
    boolean newState = false;

    public Cell(Position position) {
        this.position = position;
    }

    public Cell(int row, int col) {
        this(new Position(row, col));
    }

    @Override
    public void onReceive(Object o) throws Exception {
        receive = initializing;

        if (initializing) {
            initializing = false;

            if (o instanceof ResetDead) {
                state = new Dead(this);

            } else if (o instanceof ResetAlive) {
                state = new Alive(this);

            } else if (o instanceof ResetNeighbors) {
                ResetNeighbors msg = (ResetNeighbors) o;
                neighborCells = msg.getNeighborCells();

            } else if (o instanceof AddListener) {
                AddListener msg = (AddListener) o;
                listeners.add(msg.getListener());

            } else if (o instanceof GetPosition) {
                getSender().tell(new Position(position));

            } else if (o instanceof Init) {
                init();
                getSender().tell(new NeighborsAlive(neighborCellsAlive));

            } else if (o instanceof GetState) {
                getSender().tell(new CellState(state));
//                getSender().tell(new CellState(state));

            } else if (o instanceof Run) {
                if (newState) {
                    fireNewState();
                }
//                become(running);
                running = true;

            } else {
                System.out.println("sorry ... not in mode initializing");
            }
        }//initializing

        else if (running) {

            if (o instanceof Dead) {
                System.out.println("neighbor gone");
                if (neighborCellsAlive > 0) {
                    neighborCellsAlive -= 1;
                }
                if (newState) {
                    fireNewState();
                }

            } else if (o instanceof Alive) {
                System.out.println("neighbor born");
                neighborCellsAlive += 1;
                if (newState) {
                    fireNewState();
                }

            } else if (o instanceof Pause) {
//                become(initializing);
                initializing = true;
            }
        }//running    
        else {
            System.out.println("Unknown Message: " + o.getClass().getSimpleName());
        }

    }//onReceive()

    void init() {
        //java

        Duration duration = Duration.create(1, TimeUnit.SECONDS);
        Timeout timeout = new Timeout(duration);

        for (ActorRef cell : neighborCells) {

            Future future = Patterns.ask(cell, new GetState(), timeout);

            Object response = null;

            try {
                response = Await.result(future, duration);

            } catch (Exception ex) {
                Logger.getLogger(Cell.class.getName()).log(Level.SEVERE, null, ex);
            }

            CellState neighborState = null;

            if (response instanceof CellState) {
                neighborState = (CellState) response;

                neighborCellsAlive = neighborState.isAlive() ? neighborCellsAlive + 1 : neighborCellsAlive;
            }
        }
//    //scala original code
//    neighborCells.foreach{ cell =>
//
//        val neighborState :CellState = ( ( cell !! GetState ).get ).asInstanceOf[CellState]
//
//        neighborCellsAlive = if( neighborState.isAlive ) neighborCellsAlive + 1 else neighborCellsAlive
//    }
    }//init()

    boolean newState() {

        CellState oldState = state;

        if (neighborCellsAlive >= 3 && neighborCellsAlive <= 5) {
            state = new Alive(this);
        } else {
            state = new Dead(this);
        }
        System.out.println("state of cell " + position + " : " + oldState + " -> " + state);

        return (oldState.isAlive() == state.isAlive());
    }//newState

    void fireNewState() {
        for (ActorRef cell : listeners) {
            cell.tell(state);
        }

        for (ActorRef cell : neighborCells) {
            cell.tell(state);
        }
    }//fireNewState

    @Override
    public String toString() {
        return "Cell " + position;
    }
}



 /*
 * ORIGINAL CODE FROM:
 * https://github.com/mariogleichmann/AkkaSamples/tree/master/src/main/scala/com/mgi/akka/gameoflife
  
package com.mgi.akka.gameoflife

import akka.actor._
import Events._

class Cell( val position :(Int,Int) ) extends Actor {

  var state :CellState = Dead( this )
  
  var neighborCells :List[ActorRef] = Nil
  
  var neighborCellsAlive = 0;
  
  var listeners :List[ActorRef] = Nil

  def initializing :Receive = {

case ResetDead =>
state = Dead( this )

case ResetAlive =>
state = Alive( this )

case ResetNeighbors( cells ) =>
neighborCells = cells

case AddListener( listener ) =>
listeners = listener :: listeners

case GetPosition =>
self reply Position( position )

case Init => {
init
self reply NeighborsAlive( neighborCellsAlive )
}	

case GetState => {
self reply state
}

case Run => {
if( newState ) fireNewState
become( running )
}	

case _ => println( "sorry ... not in mode initializing" )
  }
  
  def running :Receive = {

case Dead( cell )	=> {
println( "neighbor gone" )
if( neighborCellsAlive > 0 ) neighborCellsAlive -= 1
if( newState ) fireNewState
}

case Alive( cell ) => {
println( "neighbor born" )
neighborCellsAlive += 1
if( newState ) fireNewState
}

case Pause =>
become( initializing )
  }
  

  def receive = initializing
  


  def init{

    neighborCells.foreach{ cell =>

      val neighborState :CellState = ( ( cell !! GetState ).get ).asInstanceOf[CellState]

neighborCellsAlive = if( neighborState.isAlive ) neighborCellsAlive + 1 else neighborCellsAlive
    }
  }
  
  def newState() : Boolean = {

val oldState = state

state = if( neighborCellsAlive >= 3 && neighborCellsAlive <= 5 ) Alive( this ) else Dead( this )

println( "state of cell " + position + " : " + oldState + " -> " + state )

!( oldState.isAlive == state.isAlive )
  }
  
  
  def fireNewState {

listeners.foreach( _ ! state )
neighborCells.foreach( _ ! state )
  }
  
  
  override def toString = "Cell" + position
  
}
 
 */