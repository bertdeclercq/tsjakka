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
 class StatusTextArea extends AbstractTextArea {

    

    public StatusTextArea(Observable subject) {
        super(subject);
    }

    @Override
    protected void updateStatus() {
        System.out.println("calling updateStatus");
        this.append(String.format("%s\n", getStatus()));
        System.out.println("printing status: " + getStatus());
    }
    
}
