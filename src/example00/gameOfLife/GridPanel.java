/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package example00.gameOfLife;

import akka.dispatch.Await;
import akka.dispatch.Future;
import akka.pattern.Patterns;
import akka.util.Duration;
import akka.util.Timeout;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class GridPanel extends JPanel{
    
    

    public GridPanel(CellMatrix cellMatrix, int rows, int columns) {
    
    Duration duration = Duration.create(1, TimeUnit.SECONDS);
        Timeout timeout = new Timeout(duration);
        
        for ( CellRow cellRow : cellMatrix) {
            for (Cell cell : cellRow) {
                
                Future future = Patterns.ask(cell.getSelf(), new Events.GetPanel(), timeout);

            Object response = null;

            try {
                response = Await.result(future, duration);

            } catch (Exception ex) {
                Logger.getLogger(Cell.class.getName()).log(Level.SEVERE, null, ex);
            }
              FlowPanel flowPanel =null;
            if (response instanceof FlowPanel) {
                flowPanel = (FlowPanel) response;

                
            }
            
            }//fore cellRow
        }//fore cellMatrix
    }
    
}
/*
 lazy val panel = new GridPanel( rows, columns ) {
cellDisplays.foreach( cellDisplay => contents += ( (cellDisplay !! GetPanel).get ).asInstanceOf[FlowPanel] )
}
 
 def shutdown = cellDisplays.foreach( display => display.stop() )
}
 */