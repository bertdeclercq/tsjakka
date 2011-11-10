/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.util.Observable;
import java.util.Observer;
import javax.swing.AbstractListModel;

/**
 *
 * @author Gebruiker
 */
public class UserListModel extends AbstractListModel implements Observer {
    
    private DomeinController dc;

    public UserListModel(DomeinController dc) {
        this.dc = dc;
        dc.addObserver(this);
    }

    @Override
    public int getSize() {
        return dc.getUserMapSize();
    }

    @Override
    public Object getElementAt(int index) {
       return dc.getUserNameUser(index);
        
    }

    @Override
    public void update(Observable o, Object arg) {
       this.fireContentsChanged(this, 0, dc.getUserMapSize());
    }
    
}
