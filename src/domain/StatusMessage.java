package domain;

import java.util.Observable;
import java.util.Observer;

/**
 * This class is responsible for holding the status that will be sent to the statusarea in the gui screen.
 */
public class StatusMessage extends Observable {

    private String status;
    
    /**
     * Initialize a newly created statusmessage object with stat set as status.
     * 
     * @param stat the status
     */
    public StatusMessage(String stat){
        status = stat;
        statusChanged();
    }

    
    /**
     * Returns the status that is currently in the statusmessage class.
     * 
     * @return the status
     */
    public String getStatus() {
        return status;
    }
    
    /**
     * The status that has to be set so the observer update method will be triggered.
     * 
     * @param newStatus a new status
     */
    public void addStatus(String newStatus) {
        status = newStatus;
        statusChanged();
    }
    /**
     * Override of the standard addObserver method.
     * 
     * @param observer the observer
     */
    @Override
    public void addObserver(Observer observer)
    {
        super.addObserver(observer);
        observer.update(this, getStatus());
    }
    
    /**
     * This is the method which lets the observers know that the subject has canged and that it has to update.
     */
    public void statusChanged(){
        setChanged();
        notifyObservers(status);
    }    
}
