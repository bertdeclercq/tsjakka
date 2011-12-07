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

    private String toAppend = new String("");

    public StatusTextArea(Observable subject) {
        super(subject);
    }

    @Override
    protected void updateStatus() {
        toAppend = String.format("\n%s", getStatus());
        this.append(toAppend);
        this.setCaretPosition(this.getCaretPosition()+toAppend.length());
    }
    
}
