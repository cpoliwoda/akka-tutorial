/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package example00.gameOfLife;

import akka.actor.Actor;
import akka.actor.UntypedActorFactory;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class CellFactory implements UntypedActorFactory {
    
    int row;
    int col;

    public CellFactory(int row, int col) {
        this.row = row;
        this.col = col;
    }
    

    @Override
    public Actor create() throws Exception {
        return new Cell(row, col);
    }
}
