/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author Jimmy
 */
public abstract class AbstractTextArea extends JTextArea implements Observer {
    private String status;
    private Observable subject;
    
    public AbstractTextArea(Observable subject){
        this.subject = subject;
        
            
    }
    
    protected abstract void updateStatus();
    
    public String getStatus(){
        return status;
    }
    
    @Override
    public void update(Observable o, Object arg){
        System.out.println(arg);
        this.status = (String) arg ;
        updateStatus();
    }
}
