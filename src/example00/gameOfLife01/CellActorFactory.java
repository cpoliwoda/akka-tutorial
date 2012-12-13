/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package example00.gameOfLife01;

import akka.actor.Actor;
import akka.actor.UntypedActorFactory;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class CellActorFactory implements UntypedActorFactory {
    
    private Cell cell;

    public CellActorFactory(Cell cell) {
        this.cell= cell;
    }
    

    @Override
    public Actor create() throws Exception {
        return new CellActor(cell);
    }
}