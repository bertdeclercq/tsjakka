/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Jimmy
 */
public class StatusMessage implements Observer {

    private static String status;
    private DomeinController dc;

    public StatusMessage(DomeinController dc) {
        this.dc = dc;
        dc.addObserver(this);
    }

    public static void setStatus(String status) {
        StatusMessage.status = status;
    }

    public static String getStatus() {
        return status;
    }

    @Override
    public void update(Observable o, Object arg) {
       // tga werken, ma nie oe dat moet
//                System.out.println("updateMethod in statusMessage freakt!");
        
    }
}
