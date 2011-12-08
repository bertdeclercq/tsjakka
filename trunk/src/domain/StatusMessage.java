/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.Observable;
import java.util.Observer;

/**
 *
 * This class is responsible for holding the status
 * that will be sent to the statusarea in the gui screen
 */
public class StatusMessage extends Observable {

    private String status;
    
    /**
     * 
     * @param stat 
     * initialize a newly created statusmessage object with stat
     * set as status.
     */
    public StatusMessage(String stat){
        status = stat;
        statusChanged();
    }

    
    /**
     * 
     * @return status The status that is currently in the statusmessage class
     */
    public String getStatus() {
        return status;
    }
    
    
    
   
/**
     * 
     * @param newStatus The status that has to be set so the
     * observer update method will be triggered
     */
    public void addStatus(String newStatus) {
        status = newStatus;
        statusChanged();
    }
    /**
     * 
     * @param observer 
     * Override of the standard addObserver method
     * 
     */
    @Override
    public void addObserver(Observer observer)
    {
        super.addObserver(observer);
        observer.update(this, getStatus());
    }
    
    /**
     * This is the method which lets the observers know that the
     * subject has canged and that it has to update
     */
    public void statusChanged(){
        setChanged();
        notifyObservers(status);
    }
    
}
