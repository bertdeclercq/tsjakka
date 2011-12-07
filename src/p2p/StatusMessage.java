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
public class StatusMessage extends Observable {

    private String status;
    
    public StatusMessage(){
        status = "Alles is in orde";
    }

    public String getStatus() {
        return status;
    }

    public void addStatus(String newStatus) {
        System.out.println("Add status in StatusMessage");
        System.out.println(newStatus + " olé");
        this.status = newStatus;
        setChanged();
        notifyObservers(status);
    }
    @Override
    public void addObserver(Observer observer)
    {
        super.addObserver(observer);
        observer.update(this, getStatus());
    }
}
