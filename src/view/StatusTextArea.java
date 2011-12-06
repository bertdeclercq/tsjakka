/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.Observable;
import javax.swing.JTextArea;

/**
 *
 * @author Jimmy
 */
public class StatusTextArea extends AbstractTextArea {

    

    public StatusTextArea(Observable subject) {
        super(subject);

        updateStatus();
    }

    @Override
    protected void updateStatus() {
        this.append(getStatus() + "testing");
        System.out.println("printing status: " + getStatus());
    }
    
}
