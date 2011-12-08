/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Jimmy
 */
public class StatusMessage extends Observable {

    private String status;
    
    
    public StatusMessage(String stat){
        status = stat;
        statusChanged();
    }

    public String getStatus() {
        return status;
    }
    
    
    
   

    public void addStatus(String newStatus) {
        status = newStatus;
        statusChanged();
    }
    @Override
    public void addObserver(Observer observer)
    {
        super.addObserver(observer);
        observer.update(this, getStatus());
    }
    
    public void statusChanged(){
        setChanged();
        notifyObservers(status);
    }
    
}
